package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.project.Project;

public class XMLProjectRepositoryTest {

	private static final String VALID_UID = "1234";
	private static final String VALID_NAME = "dummyProject";

	@Mock
	private XMLGenericMarshaller<ProjectXMLAssembler> marshallerMock;

	@Mock
	private Project projectMock;

	@InjectMocks
	private XMLProjectRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		repository = new XMLProjectRepository(marshallerMock);
		when(projectMock.getUid()).thenReturn(VALID_UID);
	}

	@Test
	public void findByIdFindsProjectById() throws Exception {
		Project dummyProject = new Project(VALID_UID, VALID_NAME);
		repository.store(dummyProject);

		assertEquals(dummyProject, repository.findById(VALID_UID));
	}

	@Test
	public void storeAddsProjectToProjectRepository() throws Exception {
		repository.store(projectMock);

		assertEquals(projectMock, repository.findById(VALID_UID));
	}

	@Test
	public void findAllProjectReturnsAllProjects() throws Exception {
		repository.store(projectMock);
		assertEquals(1, repository.findAll().size());
	}
}
