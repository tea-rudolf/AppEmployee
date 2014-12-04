package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.TaskService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

@Controller
@RequestMapping(value = "projects/{projectNumber}/tasks")
@SessionAttributes({ "email" })
public class TaskController {
	
	static final String EMAIL_ATTRIBUTE = "email";
	
	private ProjectService projectService;
	private TaskService taskService;
	private UserService userService;
	
	@Autowired
	public TaskController(ProjectService projectService, TaskService taskService, UserService userService) {
		this.projectService = projectService;
		this.taskService = taskService;
		this.userService = userService;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel,
			HttpSession session) {
		model.addAttribute("task", taskViewModel);
		model.addAttribute("projectNumber", projectNumber);
		return String.format("createTask");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveTask(@PathVariable String projectNumber, Model model, TaskViewModel taskViewModel,
			HttpSession session) throws Exception {
		
		try {
			projectService.addNewTaskToProject(taskViewModel, projectNumber);
		} catch (TaskAlreadyExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return createTask(projectNumber, model, taskViewModel, session);
		}

		return String.format("redirect:/projects/%s/edit", projectNumber);
	}
	
	@RequestMapping(value = "/{taskNumber}/edit", method = RequestMethod.GET)
	public String editTask(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model,
			HttpSession session) {
		
		User currentUser = userService.retrieveUserByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		
		model.addAttribute("task", taskService.retrieveTaskViewModelForExistingTask(taskNumber));
		model.addAttribute("projectNumber", projectNumber);
		model.addAttribute("employees", taskService.retrieveEmployeesByTask(taskNumber));
		model.addAttribute("role", currentUser.getRole());
		return "editTask";
	}
	
	@RequestMapping(value = "/{taskNumber}/edit", method = RequestMethod.POST)
	public String saveEditedTask(@PathVariable String projectNumber, @PathVariable String taskNumber, Model model,
			TaskViewModel viewModel, HttpSession session) throws Exception {

		try {
			projectService.updateTask(projectNumber, taskNumber, viewModel);
			return String.format("redirect:/projects/%s/tasks/%s/edit", projectNumber, taskNumber);
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return editTask(projectNumber, taskNumber, model, session);
		}
	}


}
