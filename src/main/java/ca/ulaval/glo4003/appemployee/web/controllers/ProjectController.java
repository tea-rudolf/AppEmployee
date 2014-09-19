package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;
import ca.ulaval.glo4003.appemployee.persistence.XmlProjectRepository;
import ca.ulaval.glo4003.appemployee.web.dto.ProjectDto;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
	
	private XmlProjectRepository projectRepository = new XmlProjectRepository();
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getProjects() {
		ModelAndView model = new ModelAndView("projects");
		model.addObject("projects", projectRepository.findAll());
		return model;
	}
	
	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public ModelAndView project() {
		return new ModelAndView("project", "command", new Project(3, "Project 3"));
	}
	
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public ModelAndView addProject(@ModelAttribute("SpringWeb")ProjectDto projectDto, ModelMap model) {
		Project project = new Project(projectDto.getNumber(), projectDto.getName());
		projectRepository.persist(project);
		
		return getProjects();
	}
	
	@RequestMapping(value = "/editProject", method = RequestMethod.POST)
	public ModelAndView editProject(@ModelAttribute("SpringWeb")ProjectDto projectDto, ModelMap model) {
		Project project = projectRepository.findByNumber(projectDto.getNumber());
		project.setName(projectDto.getName());
		projectRepository.update(project);
		
		return getProjects();
	}
}
