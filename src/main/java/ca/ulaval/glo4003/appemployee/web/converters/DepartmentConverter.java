package ca.ulaval.glo4003.appemployee.web.converters;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;

@Component
public class DepartmentConverter {

	public DepartmentViewModel convert(Department department) {
		return new DepartmentViewModel(department.getName(), department.getEmployeeIds(), department.getSupervisorIds());
	}

}
