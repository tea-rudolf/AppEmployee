package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

@Component
public class ProjectConverter {

	public Collection<ProjectViewModel> convert(Collection<Project> projects) {
		Collection<ProjectViewModel> viewModels = new ArrayList<ProjectViewModel>();

		for (Project project : projects) {
			ProjectViewModel viewModel = convert(project);
			viewModels.add(viewModel);
		}
		return viewModels;
	}

	public Project convert(ProjectViewModel projectViewModel) {
		Project project = new Project();
		project.setName(projectViewModel.getName());
		project.setTaskuIds(projectViewModel.getTaskIds());
		project.setEmployeeuIds(projectViewModel.getUserIds());
		project.setExpenseuIds(projectViewModel.getExpenseIds());

		return project;
	}

	public ProjectViewModel convert(Project project) {

		ProjectViewModel viewModel = new ProjectViewModel();
		viewModel.setuId(project.getuId());
		viewModel.setName(project.getName());
		viewModel.setTaskIds(project.getTaskuIds());
		viewModel.setUserIds(project.getEmployeeuIds());
		viewModel.setExpenseIds(project.getExpenseuIds());

		return viewModel;
	}
}
