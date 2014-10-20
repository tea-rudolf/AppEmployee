package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

public class ProjectConverterTest {

	private static final String FIRST_NAME = "firstName";
	private static final String FIRST_ID = "123456";
	private static final String SECOND_NAME = "secondName";
	private static final String SECOND_ID = "789103";
	private static final List<String> TASK_IDS = new ArrayList<String>();
	private static final List<String> USER_IDS = new ArrayList<String>();
	private static final List<String> EXPENSES_IDS = new ArrayList<String>();

	private ProjectConverter projectConverter;
	private ProjectViewModel projectViewModelMock;
	private Project projectMock;

	@Before
	public void init() {
		projectViewModelMock = mock(ProjectViewModel.class);
		projectMock = mock(Project.class);
		projectConverter = new ProjectConverter();
	}

	@Test
	public void convertProjectsListToViewModelsConvertsAllOfThem() {

		Project firstProject = createProject(FIRST_ID, FIRST_NAME);
		Project secondProject = createProject(SECOND_ID, SECOND_NAME);
		List<Project> projects = new ArrayList<Project>();
		projects.add(firstProject);
		projects.add(secondProject);

		ProjectViewModel[] viewModels = projectConverter.convert(projects).toArray(new ProjectViewModel[1]);

		assertEquals(FIRST_NAME, viewModels[0].getName());
		assertEquals(FIRST_ID, viewModels[0].getuId());

		assertEquals(SECOND_NAME, viewModels[1].getName());
		assertEquals(SECOND_ID, viewModels[1].getuId());
	}

	@Test
	public void convertProjectViewModelToProject() {
		when(projectViewModelMock.getName()).thenReturn(FIRST_NAME);
		when(projectViewModelMock.getTaskIds()).thenReturn(TASK_IDS);
		when(projectViewModelMock.getUserIds()).thenReturn(USER_IDS);
		when(projectViewModelMock.getExpenseIds()).thenReturn(EXPENSES_IDS);

		projectMock = projectConverter.convert(projectViewModelMock);

		assertEquals(projectViewModelMock.getName(), projectMock.getName());
		assertEquals(projectViewModelMock.getTaskIds(), projectMock.getTaskuIds());
		assertEquals(projectViewModelMock.getUserIds(), projectMock.getEmployeeuIds());
		assertEquals(projectViewModelMock.getExpenseIds(), projectMock.getExpenseuIds());
	}

	@Test
	public void convertProjectlToProjectViewMode() {
		when(projectMock.getuId()).thenReturn(FIRST_ID);
		when(projectMock.getName()).thenReturn(FIRST_NAME);
		when(projectMock.getTaskuIds()).thenReturn(TASK_IDS);
		when(projectMock.getEmployeeuIds()).thenReturn(USER_IDS);
		when(projectMock.getExpenseuIds()).thenReturn(EXPENSES_IDS);

		projectViewModelMock = projectConverter.convert(projectMock);

		assertEquals(projectMock.getuId(), projectViewModelMock.getuId());
		assertEquals(projectMock.getName(), projectViewModelMock.getName());
		assertEquals(projectMock.getTaskuIds(), projectViewModelMock.getTaskIds());
		assertEquals(projectMock.getEmployeeuIds(), projectViewModelMock.getUserIds());
		assertEquals(projectMock.getExpenseuIds(), projectViewModelMock.getExpenseIds());
	}

	private Project createProject(String number, String name) {
		Project project = mock(Project.class);
		given(project.getName()).willReturn(name);
		given(project.getuId()).willReturn(number);
		return project;
	}
}
