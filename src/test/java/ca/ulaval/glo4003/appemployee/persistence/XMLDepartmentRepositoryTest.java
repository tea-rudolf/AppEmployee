package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;

public class XMLDepartmentRepositoryTest {

	private static final String DUMMY_NAME = "dummyName";
	private static final String DUMMY_NAME2 = "dummyName2";

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
	public void findByNameFindsDepartmentWhenDepartmentExists() throws Exception {
		Department dummyDepartment = new Department(DUMMY_NAME);
		repository.store(dummyDepartment);
		assertEquals(dummyDepartment, repository.findByName(DUMMY_NAME));
	}

	@Test
	public void storeAddsDepartment() throws Exception {
		repository.store(departmentMock);
		assertEquals(departmentMock, repository.findByName(DUMMY_NAME));
	}

	@Test(expected = DepartmentAlreadyExistsException.class)
	public void storeThrowsExceptionWhenDepartmentAlreadyExists() throws Exception {
		repository.store(departmentMock);
		repository.store(departmentMock);
	}

	@Test
	public void updateUpdatesDepartmentWhenDepartmentFoundInRepo() throws Exception {
		when(departmentMock.getName()).thenReturn(DUMMY_NAME2);
		repository.store(departmentMock);

		repository.update(departmentMock);

		assertEquals(departmentMock, repository.findByName(DUMMY_NAME2));
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void updateThrowsExceptionWhenDepartmentDoesNotExist() throws Exception {
		repository.update(departmentMock);
	}
}
