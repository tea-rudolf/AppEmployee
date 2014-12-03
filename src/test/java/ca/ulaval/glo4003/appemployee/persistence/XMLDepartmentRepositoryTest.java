package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.department.Department;

public class XMLDepartmentRepositoryTest {

	private static final String DUMMY_NAME = "dummyName";

	@Mock
	private XMLGenericMarshaller<DepartmentXMLAssembler> marshallerMock;

	@Mock
	private Department departmentMock;

	@InjectMocks
	private XMLDepartmentRepository repository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repository = new XMLDepartmentRepository(marshallerMock);
		when(departmentMock.getName()).thenReturn(DUMMY_NAME);
	}

	@Test
	public void findByNameFindsDepartmentWhenDepartmentExists()
			throws Exception {
		Department dummyDepartment = new Department(DUMMY_NAME);
		repository.store(dummyDepartment);
		assertEquals(dummyDepartment, repository.findByName(DUMMY_NAME));
	}

	@Test
	public void storeAddsDepartment() throws Exception {
		repository.store(departmentMock);
		assertEquals(departmentMock, repository.findByName(DUMMY_NAME));
	}

	@Test
	public void findAllDepartmentReturnsAllDepartments() throws Exception {
		repository.store(departmentMock);
		assertEquals(1, repository.findAll().size());
	}

}
