package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.department.Department;

@XmlRootElement(name = "departments")
public class DepartmentXMLAssembler {

	private List<Department> departments = new ArrayList<Department>();

	public List<Department> getDepartments() {
		return this.departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}
