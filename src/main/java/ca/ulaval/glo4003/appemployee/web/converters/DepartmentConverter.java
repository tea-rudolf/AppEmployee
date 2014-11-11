package ca.ulaval.glo4003.appemployee.web.converters;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;

@Component
public class DepartmentConverter {

	public DepartmentViewModel convert(Department department) {
		DepartmentViewModel departmentViewModel = new DepartmentViewModel();
		departmentViewModel.setName(department.getName());
		departmentViewModel.setEmployeeIds(department.getEmployeeIds());
		departmentViewModel.setSupervisorIds(department.getSupervisorIds());

		return departmentViewModel;
	}

}
