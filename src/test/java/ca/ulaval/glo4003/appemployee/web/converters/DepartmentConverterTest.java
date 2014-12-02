package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;

public class DepartmentConverterTest {
	
	private static final String DEPARTMENT_NAME = "departmentName";

	private List<String> employeeIds = new ArrayList<String>();
	private List<String> supervisorIds = new ArrayList<String>();
	
	@Mock
	private Department departmentMock;
	
	@Mock
	private DepartmentViewModel departmentViewModelMock;
	
	@InjectMocks
	private DepartmentConverter departmentConverter;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		departmentConverter = new DepartmentConverter();
	}
	
	@Test
	public void convertDepartmentToDepartmentViewModel() {
		when(departmentMock.getName()).thenReturn(DEPARTMENT_NAME);
		when(departmentMock.getEmployeeIds()).thenReturn(employeeIds);
		when(departmentMock.getSupervisorIds()).thenReturn(supervisorIds);
		
		departmentViewModelMock = departmentConverter.convert(departmentMock);
		
		assertEquals(departmentMock.getName(), departmentViewModelMock.getName());
		assertEquals(departmentMock.getEmployeeIds(), departmentViewModelMock.getEmployeeIds());
		assertEquals(departmentMock.getSupervisorIds(), departmentViewModelMock.getSupervisorIds());
	}

}
