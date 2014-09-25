package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.Task;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;
import ca.ulaval.glo4003.appemployee.persistence.XmlProjectRepository;
import ca.ulaval.glo4003.appemployee.persistence.XmlTaskRepository;
import ca.ulaval.glo4003.appemployee.service.ProjectService;
import ca.ulaval.glo4003.appemployee.service.TaskService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;



	@Controller
	@RequestMapping(value = "/tasks")
	public class TaskController {

		private TaskService taskService;
		private TaskConverter taskConverter;

		@Autowired
		public TaskController(TaskService taskService,
				TaskConverter taskConverter) {
			this.taskService = taskService;
			this.taskConverter = taskConverter;
		}

		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String getTasks(Model model) {
			model.addAttribute("tasks",
					taskConverter.convert(taskService.getAllTasks()));
			return "tasklist";
		}

		@RequestMapping(value = "/add", method = RequestMethod.GET)
		public String task(Model model) {
			model.addAttribute("task", new TaskViewModel());
			return "createtask";
		}

		@RequestMapping(value = "/add", method = RequestMethod.POST)
		public String addTask(
				@ModelAttribute("SpringWeb") TaskViewModel taskViewModel,
				ModelMap model) {
			taskService.addTask(taskConverter.convert(taskViewModel));
			return "redirect:/tasks/";
		}

		@RequestMapping(value = "/{number}/edit", method = RequestMethod.GET)
		public String edit(@PathVariable int number, Model model) {
			model.addAttribute("task",
					taskConverter.convert(taskService.getTaskByNumber(number)));
			return "editproject";
		}

		@RequestMapping(value = "/{number}/edit", method = RequestMethod.POST)
		public String edit(@PathVariable int number, TaskViewModel viewModel) {
			taskService.editTask(number, viewModel);
			return "redirect:/projects/";
		}
	}

