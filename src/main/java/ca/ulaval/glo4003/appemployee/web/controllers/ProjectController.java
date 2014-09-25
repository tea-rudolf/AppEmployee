package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.service.ProjectService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
	
	private ProjectService projectService;
	private ProjectConverter projectConverter;
	private TaskConverter taskConverter;
	
	@Autowired
	public ProjectController(ProjectService projectService, ProjectConverter projectConverter, TaskConverter taskConverter) {
		this.projectService = projectService;
		this.projectConverter = projectConverter;
		this.taskConverter = taskConverter;
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
	public String addProject(ProjectViewModel projectViewModel) {
		projectService.addProject(projectConverter.convert(projectViewModel));
		return String.format("redirect:/projects/%s/edit", projectViewModel.getNumber());
	}
	
	@RequestMapping(value = "/{number}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable String number, Model model) {
		Project project = projectService.getProjectByNumber(number);
		model.addAttribute("project", projectConverter.convert(project));
		model.addAttribute("tasks", taskConverter.convert(project.getTasks()));
		return "editproject";
	}
	
	@RequestMapping(value = "/{number}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable String number, ProjectViewModel viewModel) {
		projectService.editProject(number, viewModel);
		return "redirect:/projects/";
	}
}
