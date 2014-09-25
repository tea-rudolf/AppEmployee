package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskExistsException;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

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
		return "projectList";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String project(Model model, ProjectViewModel projectViewModel) {
		model.addAttribute("project", projectViewModel);
		return "createProject";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProject(Model model, ProjectViewModel projectViewModel) {
		try { 
			projectService.addProject(projectConverter.convert(projectViewModel));
		} catch (ProjectExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return project(model, projectViewModel);
		}
		return String.format("redirect:/projects/%s/edit", projectViewModel.getNumber());
	}
	
	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable String projectNumber, Model model) {
		Project project = projectService.getProjectByNumber(projectNumber);
		model.addAttribute("project", projectConverter.convert(project));
		model.addAttribute("tasks", taskConverter.convert(project.getTasks()));
		return "editProject";
	}
	
	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable String projectNumber, ProjectViewModel viewModel) {
		projectService.updateProject(projectNumber, viewModel);
		return "redirect:/projects/";
	}
	
	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.GET)
	public String task(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel) {
		model.addAttribute("task", taskViewModel);
		model.addAttribute("projectNumber", projectNumber);
		return String.format("createTask");
	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.POST)
	public String addTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel) {
		try {
			projectService.addTask(projectNumber, taskConverter.convert(taskViewModel));
		} catch (TaskExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return task(projectNumber, model, taskViewModel);
		}
		return String.format("redirect:/projects/%s/edit", projectNumber);
	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model) {
		Task task = projectService.getTaskByNumber(projectNumber, taskNumber);
		
		model.addAttribute("task", taskConverter.convert(task));
		model.addAttribute("projectNumber", projectNumber);
		
		return "editTask";
	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable String projectNumber, @PathVariable String taskNumber, TaskViewModel viewModel) {
		projectService.updateTask(projectNumber, taskNumber, viewModel);
		return String.format("redirect:/projects/%s/edit", projectNumber);
	}
}
