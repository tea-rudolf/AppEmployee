package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.web.dto.ProjectDto;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
	static ArrayList<Project> projects = new ArrayList<Project>(); //TODO: Use XML repo
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getProjects() {
		if (projects.isEmpty()) {
			projects.add(new Project(1, "Project 1"));
			projects.add(new Project(2, "Project 2"));
		}
		
		ModelAndView model = new ModelAndView("projects");
		model.addObject("projects", projects);
		return model;
	}
	
	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public ModelAndView project() {
		return new ModelAndView("project", "command", new Project(3, "Project 3"));
	}
	
	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public ModelAndView addProject(@ModelAttribute("SpringWeb")ProjectDto projectDto, ModelMap model) {
		projects.add(new Project(projectDto.getNumber(), projectDto.getName()));
		
		return getProjects();
	}
}
