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

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

@Controller
@RequestMapping(value = "/projects")
@SessionAttributes({ "email" })
public class ProjectController {

	static final String EMAIL_ATTRIBUTE = "email";

	private ProjectService projectService;
	private ProjectConverter projectConverter;
	private TaskConverter taskConverter;

	@Autowired
	public ProjectController(ProjectService projectService, ProjectConverter projectConverter, TaskConverter taskConverter) {
		this.projectService = projectService;
		this.projectConverter = projectConverter;
		this.taskConverter = taskConverter;
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
	public String projectCreation(Model model, ProjectViewModel projectViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		model.addAttribute("project", projectViewModel);
		return "createProject";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProject(Model model, ProjectViewModel projectViewModel, HttpSession session) throws Exception {

		try {
			Project newProject = projectConverter.convert(projectViewModel);
			projectService.addProject(newProject);
			return String.format("redirect:/projects/%s/edit", newProject.getuId());
		} catch (ProjectExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return projectCreation(model, projectViewModel, session);
		}

	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.GET)
	public String projectModification(@PathVariable String projectNumber, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		Project project = projectService.getProjectById(projectNumber);
		model.addAttribute("project", projectConverter.convert(project));
		List<Task> t = projectService.getAllTasksByProjectId(project.getuId());

		Collection<TaskViewModel> col = taskConverter.convert(t);
		model.addAttribute("tasks", col);

		return "editProject";
	}

	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.POST)
	public String editProject(@PathVariable String projectNumber, ProjectViewModel viewModel, HttpSession session) throws Exception {
		projectService.updateProject(projectNumber, viewModel);
		return "redirect:/projects/";
	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.GET)
	public String taskCreation(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		model.addAttribute("task", taskViewModel);
		model.addAttribute("projectNumber", projectNumber);
		return String.format("createTask");
	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.POST)
	public String addTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel, HttpSession session) throws Exception {

		try {
			projectService.addTask(taskConverter.convert(taskViewModel));
			projectService.addTaskToProject(projectNumber, taskConverter.convert(taskViewModel).getuId());
		} catch (TaskAlreadyExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return taskCreation(projectNumber, model, taskViewModel, session);
		}

		return String.format("redirect:/projects/%s/edit", projectNumber);

	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.GET)
	public String taskModification(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		Task task = projectService.getTaskById(taskNumber);
		model.addAttribute("task", taskConverter.convert(task));
		model.addAttribute("projectNumber", projectNumber);
		return "editTask";
	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.POST)
	public String editTask(@PathVariable String projectNumber, @PathVariable String taskNumber, TaskViewModel viewModel, HttpSession session) throws Exception {
		projectService.updateTask(projectNumber, taskNumber, viewModel);
		return String.format("redirect:/projects/%s/edit", projectNumber);
	}
}
