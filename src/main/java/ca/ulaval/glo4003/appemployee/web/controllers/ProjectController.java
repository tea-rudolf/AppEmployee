package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

@Controller
@RequestMapping(value = "/projects")
@SessionAttributes({ "email" })
public class ProjectController {

	static final String EMAIL_ATTRIBUTE = "email";

	private ProjectService projectService;
	private UserService userService;

	@Autowired
	public ProjectController(ProjectService projectService, UserService userService) {
		this.projectService = projectService;
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getProjects(Model model, HttpSession session) {
		model.addAttribute("projects", projectService.retrieveAllProjects());
		return "projectList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateProjectForm(Model model, ProjectViewModel projectViewModel, HttpSession session) {
		model.addAttribute("project", projectViewModel);
		return "createProject";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createProject(Model model, ProjectViewModel projectViewModel, HttpSession session) throws Exception {
		projectService.createProject(projectViewModel);
		return "redirect:/projects";
	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.GET)
	public String editProject(@PathVariable String projectNumber, Model model, HttpSession session) {
		User currentUser = userService.retrieveUserByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute("tasks", projectService.retrieveTasksByProject(projectNumber));
		model.addAttribute("employees", projectService.retieveEmployeesByProject(projectNumber));
		model.addAttribute("project", projectService.retrieveProjectViewModelForExistingProject(projectNumber));
		model.addAttribute("role", currentUser.getRole());

		return "editProject";
	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.POST)
	public String saveEditedProject(@PathVariable String projectNumber, Model model, ProjectViewModel viewModel, HttpSession session) throws Exception {
		
		try {
			projectService.updateProject(projectNumber, viewModel);
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		
		return String.format("redirect:/projects/%s/edit", projectNumber);
	}
}
