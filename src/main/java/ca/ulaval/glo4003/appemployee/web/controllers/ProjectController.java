package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.domain.Project;

public class ProjectController {

	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public ModelAndView student() {
		return new ModelAndView("project", "command", new Project());
	}
	
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public String addProject(@ModelAttribute("SpringWeb")Project project, ModelMap model) {
		model.addAttribute("number", project.getNumber());
		model.addAttribute("name", project.getName());
		
		return "project";
	}
}
