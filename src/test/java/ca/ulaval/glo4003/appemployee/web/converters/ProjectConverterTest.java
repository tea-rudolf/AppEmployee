//package ca.ulaval.glo4003.appemployee.web.converters;
//
//import static org.junit.Assert.*;
//import static org.mockito.BDDMockito.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import ca.ulaval.glo4003.appemployee.domain.project.Project;
//import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
//
//public class ProjectConverterTest {
//	private ProjectConverter converter;
//
//	@Before
//	public void setUp() {
//		converter = new ProjectConverter();
//	}
//
//	@Test
//	public void convertProjectsListToViewModelsConvertsAllOfThem() {
//		String firstName = "firstName";
//		String firstNumber = "123456";
//		String secondName = "secondName";
//		String secondNumber = "789103";
//		Project firstProject = createProject(firstNumber, firstName);
//		Project secondProject = createProject(secondNumber, secondName);
//		List<Project> projects = new ArrayList<Project>();
//		projects.add(firstProject);
//		projects.add(secondProject);
//
//		ProjectViewModel[] viewModels = converter.convert(projects).toArray(new ProjectViewModel[1]);
//
//		assertEquals(firstName, viewModels[0].getName());
//		assertEquals(firstNumber, viewModels[0].getNumber());
//
//		assertEquals(secondName, viewModels[1].getName());
//		assertEquals(secondNumber, viewModels[1].getNumber());
//	}
//
//	@Test
//	public void convertProjectToProjectViewModelSetsNumberAndName() {
//		String number = "123456";
//		String name = "abcdefg";
//		Project project = createProject(number, name);
//
//		ProjectViewModel viewModel = converter.convert(project);
//
//		assertEquals(number, viewModel.getNumber());
//		assertEquals(name, viewModel.getName());
//	}
//
//	@Test
//	public void convertProjectViewModelToProjectSetsNumberAndName() {
//		ProjectViewModel viewModel = new ProjectViewModel();
//		viewModel.setName("task");
//		viewModel.setNumber("123456");
//
//		Project project = converter.convert(viewModel);
//
//		assertEquals(viewModel.getName(), project.getName());
//		assertEquals(viewModel.getNumber(), project.getNumber());
//	}
//
//	private Project createProject(String number, String name) {
//		Project project = mock(Project.class);
//		given(project.getName()).willReturn(name);
//		given(project.getNumber()).willReturn(number);
//		return project;
//	}
//}
