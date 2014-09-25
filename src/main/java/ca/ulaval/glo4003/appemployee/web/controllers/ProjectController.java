package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.Task;
import ca.ulaval.glo4003.appemployee.service.ProjectService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
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
	
	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable String projectNumber, Model model) {
		Project project = projectService.getProjectByNumber(projectNumber);
		model.addAttribute("project", projectConverter.convert(project));
		model.addAttribute("tasks", taskConverter.convert(project.getTasks()));
		return "editproject";
	}
	
	@RequestMapping(value = "/{projectNumber}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable String projectNumber, ProjectViewModel viewModel) {
		projectService.updateProject(projectNumber, viewModel);
		return "redirect:/projects/";
	}
	
	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.GET)
	public String task(@PathVariable String projectNumber, Model model) {
		model.addAttribute("task", new TaskViewModel());
		model.addAttribute("projectNumber", projectNumber);
		return String.format("createtask");
	}

	@RequestMapping(value = "/{projectNumber}/tasks/add", method = RequestMethod.POST)
	public String addTask(@PathVariable String projectNumber, TaskViewModel taskViewModel) {
		Task task = taskConverter.convert(taskViewModel);
		projectService.addTask(projectNumber, task);
		return String.format("redirect:/projects/%s/edit", projectNumber);
	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model) {
		Task task = projectService.getTaskByNumber(projectNumber, taskNumber);
		
		model.addAttribute("task", taskConverter.convert(task));
		model.addAttribute("projectNumber", projectNumber);
		
		return "edittask";
	}

	@RequestMapping(value = "/{projectNumber}/tasks/{taskNumber}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable String projectNumber, @PathVariable String taskNumber, TaskViewModel viewModel) {
		projectService.updateTask(projectNumber, taskNumber, viewModel);
		return String.format("redirect:/projects/%s/edit", projectNumber);
	}
}
