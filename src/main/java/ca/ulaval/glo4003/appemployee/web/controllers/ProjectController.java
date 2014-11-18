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

import ca.ulaval.glo4003.appemployee.domain.exceptions.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
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
	static final String SIMPLE_REDIRECT = "redirect:/";
	static final String PROJECT_LIST_FORM = "projectList";
	static final String CREATE_PROJECT_FORM = "createProject";
	static final String EDIT_PROJECT_REDIRECT = "redirect:/projects/%s/edit";
	static final String EDIT_PROJECT_FORM = "editProject";
	static final String PROJECT_LIST_REDIRECT = "redirect:/projects/";
	static final String EDIT_TASK_REDIRECT = "editTask";

	private ProjectService projectService;
	private UserService userService;
	private ProjectConverter projectConverter;
	private TaskConverter taskConverter;
	private UserConverter userConverter;

	@Autowired
	public ProjectController(ProjectService projectService, UserService userService, ProjectConverter projectConverter, TaskConverter taskConverter,
			UserConverter userConverter) {
		this.projectService = projectService;
		this.userService = userService;
		this.projectConverter = projectConverter;
		this.taskConverter = taskConverter;
		this.userConverter = userConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getProjects(Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("projects", projectConverter.convert(projectService.getAllProjects()));
		return PROJECT_LIST_FORM;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createProject(Model model, ProjectViewModel projectViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("project", projectViewModel);
		return CREATE_PROJECT_FORM;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveProject(Model model, ProjectViewModel projectViewModel, HttpSession session) throws Exception {

		try {
			Project newProject = projectConverter.convert(projectViewModel);
			projectService.addProject(newProject);
			return String.format(EDIT_PROJECT_REDIRECT, newProject.getUid());
		} catch (ProjectExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return createProject(model, projectViewModel, session);
		}

	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.GET)
	public String editProject(@PathVariable String projectNumber, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		Project project = projectService.getProjectById(projectNumber);
		List<Task> tasks = projectService.getAllTasksByProjectId(project.getUid());
		List<User> employees = projectService.getAllEmployeesByProjectId(project.getUid());
		User currentUser = userService.retrieveByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		Collection<TaskViewModel> tasksViewModel = taskConverter.convert(tasks);
		Collection<UserViewModel> employeesViewModel = userConverter.convert(employees);
		ProjectViewModel projectViewModel = projectConverter.convert(project);

		projectViewModel.setAvailableUsers(userService.retrieveAllUserEmails());

		model.addAttribute("tasks", tasksViewModel);
		model.addAttribute("employees", employeesViewModel);
		model.addAttribute("project", projectViewModel);
		model.addAttribute("role", currentUser.getRole());

		return EDIT_PROJECT_FORM;

	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.POST)
	public String saveEditedProject(@PathVariable String projectNumber, Model model, ProjectViewModel viewModel, HttpSession session) throws Exception {

		try {

			if (!viewModel.getUserEmail().equals("NONE") && !viewModel.getUserEmail().equals("")) {
				userService.retrieveByEmail(viewModel.getUserEmail());
			}

			projectService.updateProject(projectNumber, viewModel);

			return PROJECT_LIST_REDIRECT;

		} catch (Exception e) {

			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return editProject(projectNumber, model, session);
		}

	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.GET)
	public String createTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

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

		return String.format(EDIT_PROJECT_REDIRECT, projectNumber);

	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.GET)
	public String editTask(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		Task task = projectService.getTaskById(taskNumber);
		List<User> employees = userService.retrieveUsersByEmail(task.getAuthorizedUsers());
		Collection<UserViewModel> employeesViewModel = userConverter.convert(employees);
		User currentUser = userService.retrieveByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		TaskViewModel taskViewModel = taskConverter.convert(task);
		taskViewModel.setAvailableUsers(userService.retrieveAllUserEmails());
		model.addAttribute("project", taskViewModel);

		model.addAttribute("task", taskViewModel);
		model.addAttribute("projectNumber", projectNumber);
		model.addAttribute("employees", employeesViewModel);
		model.addAttribute("role", currentUser.getRole());

		return EDIT_TASK_REDIRECT;

	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.POST)
	public String saveEditedTask(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model, TaskViewModel viewModel, HttpSession session)
			throws Exception {

		try {

			if (!viewModel.getUserEmail().equals("NONE") && !viewModel.getUserEmail().isEmpty()) {
				userService.retrieveByEmail(viewModel.getUserEmail());
			}

			projectService.updateTask(projectNumber, taskNumber, viewModel);

			return String.format(EDIT_PROJECT_REDIRECT, projectNumber);

		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return editTask(projectNumber, taskNumber, model, session);
		}

	}

}
