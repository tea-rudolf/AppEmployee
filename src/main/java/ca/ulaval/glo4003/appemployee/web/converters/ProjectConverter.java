package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

@Component
public class ProjectConverter {

	public Collection<ProjectViewModel> convert(List<Project> projects) {
		Collection<ProjectViewModel> viewModels = new ArrayList<ProjectViewModel>();

		for (Project project : projects) {
			ProjectViewModel viewModel = convert(project);
			viewModels.add(viewModel);
		}
		return viewModels;
	}

	public Project convert(ProjectViewModel projectViewModel) {
		Project project = new Project(projectViewModel.getNumber(), projectViewModel.getName());
		project.setStartDate(projectViewModel.getStartDate());
		project.setEndDate(projectViewModel.getEndDate());
		project.setTaskIds(projectViewModel.getTaskIds());
		project.setUserIds(projectViewModel.getUserIds());
		project.setExpenseIds(projectViewModel.getExpenseIds());

		return project;
	}

	public ProjectViewModel convert(Project project) {
		ProjectViewModel viewModel = new ProjectViewModel();
		viewModel.setNumber(project.getNumber());
		viewModel.setName(project.getName());
		viewModel.setStartDate(project.getStartDate());
		viewModel.setEndDate(project.getEndDate());
		viewModel.setTaskIds(project.getTaskIds());
		viewModel.setUserIds(project.getUserIds());
		viewModel.setExpenseIds(project.getExpenseIds());

		return viewModel;
	}
}
