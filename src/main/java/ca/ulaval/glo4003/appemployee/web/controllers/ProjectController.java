package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.service.ProjectService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
	
	private ProjectService projectService;
	private ProjectConverter projectConverter;
	
	@Autowired
	public ProjectController(ProjectService projectService, ProjectConverter projectConverter) {
		this.projectService = projectService;
		this.projectConverter = projectConverter;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getProjects(Model model) {
		model.addAttribute("projects", projectConverter.convert(projectService.getAllProjects()));
		return "projectlist";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String project(Model model) {
		model.addAttribute("project", new ProjectViewModel());
		return "createproject";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProject(@ModelAttribute("SpringWeb")ProjectViewModel projectViewModel, ModelMap model) {
		projectService.addProject(projectConverter.convert(projectViewModel));
		return "redirect:/projects/";
	}
	
	@RequestMapping(value = "/{number}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable int number, Model model) {
		model.addAttribute("project", projectConverter.convert(projectService.getProjectByNumber(number)));
		return "editproject";
	}
	
	@RequestMapping(value = "/{number}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable int number, ProjectViewModel viewModel) {
		projectService.editProject(number, viewModel);
		return "redirect:/projects/";
	}
}
