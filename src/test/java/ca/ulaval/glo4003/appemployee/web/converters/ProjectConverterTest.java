package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

public class ProjectConverterTest {
	private ProjectConverter converter;
	
	@Before
	public void setUp() {
		converter = new ProjectConverter();
	}
	
	@Test
	public void convertProjectsListToViewModelsConvertsAllOfThem() {
		String firstName = "firstName";
		String firstNumber = "123456";
		String secondName = "secondName";
		String secondNumber = "789103";
		Project firstProject = createProject(firstNumber, firstName);
		Project secondProject = createProject(secondNumber, secondName);
		List<Project> projects = new ArrayList<Project>();
		projects.add(firstProject);
		projects.add(secondProject);

		
		ProjectViewModel[] viewModels = converter.convert(projects).toArray(new ProjectViewModel[1]);
			
		assertEquals(firstName, viewModels[0].name);
		assertEquals(firstNumber, viewModels[0].number);
		
		assertEquals(secondName, viewModels[1].name);
		assertEquals(secondNumber, viewModels[1].number);
	}
	
	@Test 
	public void convertProjectToProjectViewModelSetsNumberAndName() {
		String number = "123456";
		String name = "abcdefg";
		Project project = createProject(number, name);
		
		ProjectViewModel viewModel	= converter.convert(project);
		
		assertEquals(number, viewModel.number);
		assertEquals(name, viewModel.name);
	}
	
	@Test
	public void convertProjectViewModelToProjectSetsNumberAndName() {
		ProjectViewModel viewModel = new ProjectViewModel();
		viewModel.name = "task";
		viewModel.number = "123456";
		
		Project project = converter.convert(viewModel);
		
		assertEquals(viewModel.name, project.getName());
		assertEquals(viewModel.number, project.getNumber());
	}
	

	private Project createProject(String number, String name) {
		Project project = mock(Project.class);
		given(project.getName()).willReturn(name);
		given(project.getNumber()).willReturn(number);
		return project;
	}

}
