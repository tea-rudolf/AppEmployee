package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
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
			return "redirect:/";
		}

		model.addAttribute("projects", projectConverter.convert(projectService.getAllProjects()));
		return "projectList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createProject(Model model, ProjectViewModel projectViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		model.addAttribute("project", projectViewModel);
		return "createProject";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveProject(Model model, ProjectViewModel projectViewModel, HttpSession session) throws Exception {

		try {
			Project newProject = projectConverter.convert(projectViewModel);
			projectService.addProject(newProject);
			return String.format("redirect:/projects/%s/edit", newProject.getuId());
		} catch (ProjectExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return createProject(model, projectViewModel, session);
		}

	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.GET)
	public String editProject(@PathVariable String projectNumber, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		Project project = projectService.getProjectById(projectNumber);
		List<Task> tasks = projectService.getAllTasksByProjectId(project.getuId());

		List<User> employees = projectService.getAllEmployeesByProjectId(project.getuId());
		User currentUser = userService.retrieveByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		Collection<TaskViewModel> tasksViewModel = taskConverter.convert(tasks);
		Collection<UserViewModel> employeesViewModel = userConverter.convert(employees);

		model.addAttribute("tasks", tasksViewModel);
		model.addAttribute("employees", employeesViewModel);
		model.addAttribute("project", projectConverter.convert(project));
		model.addAttribute("role", currentUser.getRole());
		
		return "editProject";
	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.POST)
	public String saveEditedProject(@PathVariable String projectNumber, ProjectViewModel viewModel, HttpSession session) throws Exception {

		if (!viewModel.getUserEmail().equals("")) {
			try {
				userService.retrieveByEmail(viewModel.getUserEmail());
			} catch (UserNotFoundException userNotFound) {
				return "redirect:/projects/userNotFoundError";
			}
		}

		projectService.updateProject(projectNumber, viewModel);
		return "redirect:/projects/";
	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.GET)
	public String createTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		model.addAttribute("task", taskViewModel);
		model.addAttribute("projectNumber", projectNumber);
		return String.format("createTask");
	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.POST)
	public String saveTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel, HttpSession session) throws Exception {

		try {
			Task task = taskConverter.convert(taskViewModel);
			projectService.addTask(task);
			projectService.addTaskToProject(projectNumber, task.getuId());
		} catch (TaskAlreadyExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return createTask(projectNumber, model, taskViewModel, session);
		}

		return String.format("redirect:/projects/%s/edit", projectNumber);

	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.GET)
	public String editTask(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		Task task = projectService.getTaskById(taskNumber);
		List<User> employees = userService.retrieveUsersByEmail(task.getAuthorizedUsers());
		Collection<UserViewModel> employeesViewModel = userConverter.convert(employees);
		User currentUser = userService.retrieveByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		model.addAttribute("task", taskConverter.convert(task));
		model.addAttribute("projectNumber", projectNumber);
		model.addAttribute("employees", employeesViewModel);
		model.addAttribute("role", currentUser.getRole());

		return "editTask";

	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.POST)
	public String saveEditedTask(@PathVariable String projectNumber, @PathVariable String taskNumber, TaskViewModel viewModel, HttpSession session) throws Exception {

		if (!viewModel.getUserEmail().equals("")) {
			try {
				userService.retrieveByEmail(viewModel.getUserEmail());
			} catch (UserNotFoundException userNotFound) {
				return "redirect:/projects/userNotFoundError";
			}
		}

		projectService.updateTask(projectNumber, taskNumber, viewModel);

		return String.format("redirect:/projects/%s/edit", projectNumber);
	}

	@RequestMapping(value = "/userNotFoundError", method = RequestMethod.GET)
	public String getErrorNoTaskSelected(ModelMap model, HttpSession session) {
		return "userNotFoundError";
	}

}
