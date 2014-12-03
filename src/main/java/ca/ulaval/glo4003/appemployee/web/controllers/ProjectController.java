package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/projects")
@SessionAttributes({ "email" })
public class ProjectController {

	static final String EMAIL_ATTRIBUTE = "email";

	private ProjectService projectService;
	private UserService userService;

	private TaskConverter taskConverter;
	private UserConverter userConverter;

	@Autowired
	public ProjectController(ProjectService projectService, UserService userService, TaskConverter taskConverter, UserConverter userConverter) {
		this.projectService = projectService;
		this.userService = userService;
		this.taskConverter = taskConverter;
		this.userConverter = userConverter;
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

			if (!viewModel.getUserEmail().equals("NONE") && !viewModel.getUserEmail().equals("")) {
				userService.retrieveUserByEmail(viewModel.getUserEmail());
			}

			projectService.updateProject(projectNumber, viewModel);

			return "redirect:/projects/";

		} catch (Exception e) {

			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return editProject(projectNumber, model, session);
		}

	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.GET)
	public String createTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel, HttpSession session) {

		model.addAttribute("task", taskViewModel);
		model.addAttribute("projectNumber", projectNumber);
		return String.format("createTask");
	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.POST)
	public String saveTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel, HttpSession session) throws Exception {

		try {
			projectService.addNewTaskToProject(taskViewModel, projectNumber);
		} catch (TaskAlreadyExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return createTask(projectNumber, model, taskViewModel, session);
		}

		return String.format("redirect:/projects/%s/edit", projectNumber);
	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.GET)
	public String editTask(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model, HttpSession session) {

		Task task = projectService.getTaskById(taskNumber);
		List<User> employees = userService.retrieveUsersByEmail(task.getAuthorizedUsers());
		Collection<UserViewModel> employeesViewModel = userConverter.convert(employees);
		User currentUser = userService.retrieveUserByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		TaskViewModel taskViewModel = taskConverter.convert(task);
		taskViewModel.setAvailableUsers(userService.evaluateAllUserEmails());
		model.addAttribute("project", taskViewModel);

		model.addAttribute("task", taskViewModel);
		model.addAttribute("projectNumber", projectNumber);
		model.addAttribute("employees", employeesViewModel);
		model.addAttribute("role", currentUser.getRole());

		return "editTask";
	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.POST)
	public String saveEditedTask(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model, TaskViewModel viewModel, HttpSession session)
			throws Exception {

		try {

			if (!viewModel.getUserEmail().equals("NONE") && !viewModel.getUserEmail().isEmpty()) {
				userService.retrieveUserByEmail(viewModel.getUserEmail());
			}

			projectService.updateTask(projectNumber, taskNumber, viewModel);

			return String.format("redirect:/projects/%s/edit", projectNumber);

		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return editTask(projectNumber, taskNumber, model, session);
		}
	}

}
