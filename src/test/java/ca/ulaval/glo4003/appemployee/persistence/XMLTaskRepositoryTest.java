package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.task.Task;



public class XMLTaskRepositoryTest {
	
	
	private static final String VALID_UID = "1234";
	
	@Mock
	private XMLGenericMarshaller<TaskXMLAssembler> marshallerMock;
	
	@Mock
	private Task taskMock;

	@InjectMocks
	private XMLTaskRepository repository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		repository = new XMLTaskRepository(marshallerMock);
		when(taskMock.getuId()).thenReturn(VALID_UID);
	}
	
	@Test
	public void findByIdFindsTaskById() throws Exception {
		Task dummyTask = new Task(VALID_UID);
		repository.store(dummyTask);
		
		assertEquals(dummyTask, repository.findByUid(VALID_UID));
	}
	
	@Test
	public void storeAddsTaskToTaskRepository() throws Exception {
		repository.store(taskMock);
		
		assertEquals(taskMock, repository.findByUid(VALID_UID));
	}
	
	@Test
	public void findAllTasktReturnsAllTask() throws Exception {
		repository.store(taskMock);
		assertEquals(1, repository.findAll().size());
	}

}
