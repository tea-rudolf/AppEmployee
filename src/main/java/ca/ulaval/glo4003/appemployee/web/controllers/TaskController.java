package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.glo4003.appemployee.domain.Task;
import ca.ulaval.glo4003.appemployee.service.ProjectService;
import ca.ulaval.glo4003.appemployee.service.TaskService;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

	@Controller
	@RequestMapping(value = "/tasks")
	public class TaskController {

		private TaskService taskService;
		private TaskConverter taskConverter;
		private ProjectService projectService;

		@Autowired
		public TaskController(TaskService taskService,
				TaskConverter taskConverter, ProjectService projectService) {
			this.taskService = taskService;
			this.taskConverter = taskConverter;
			this.projectService = projectService;
		}

		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String getTasks(Model model) {
			model.addAttribute("tasks",
					taskConverter.convert(taskService.getAllTasks()));
			return "tasklist";
		}

		@RequestMapping(value = "/add", method = RequestMethod.GET)
		public String task(Model model, @RequestParam Map<String,String> requestParameters) {
			TaskViewModel viewModel = new TaskViewModel();
			
			viewModel.setSource(requestParameters.get("source"));
			viewModel.setProjectNumber(requestParameters.get("projectNumber"));
			
			model.addAttribute("task", viewModel);
			return String.format("createtask");
		}

		@RequestMapping(value = "/add", method = RequestMethod.POST)
		public String addTask(TaskViewModel taskViewModel, @RequestParam Map<String,String> requestParameters) {
			Task task = taskConverter.convert(taskViewModel);
			taskService.addTask(task);
			
			if (requestParameters.get("source").compareTo("editproject") == 0) {
				projectService.addTask(requestParameters.get("projectNumber"), task);
				return String.format("redirect:/projects/%s/edit", requestParameters.get("projectNumber"));
			} else {
				return "redirect:/tasks/";
			}
		}

		@RequestMapping(value = "/{number}/edit", method = RequestMethod.GET)
		public String edit(@PathVariable String number, Model model, @RequestParam Map<String,String> requestParameters) {
			TaskViewModel taskViewModel = taskConverter.convert(taskService.getTaskByNumber(number));
			
			taskViewModel.setSource(requestParameters.get("source"));
			taskViewModel.setProjectNumber(requestParameters.get("projectNumber"));
			
			model.addAttribute("task", taskViewModel);
			
			return "edittask";
		}

		@RequestMapping(value = "/{number}/edit", method = RequestMethod.POST)
		public String edit(@PathVariable String number, TaskViewModel viewModel, @RequestParam Map<String,String> requestParameters) {
			taskService.editTask(number, viewModel);
			if (requestParameters.get("source").compareTo("editproject") == 0) {
				return String.format("redirect:/projects/%s/edit", requestParameters.get("projectNumber"));
			} else {
				return "redirect:/tasks/";
			}
		}
	}

