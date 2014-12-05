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

	public ProjectViewModel convert(Project project) {
		return new ProjectViewModel(project.getUid(), project.getName(), project.getTaskUids(), project.getEmployeeUids(), project.getExpenseUids());
	}
}
