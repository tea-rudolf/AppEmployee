package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;

@Repository
@Singleton
public class XMLDepartmentRepository implements DepartmentRepository {

	private XMLGenericMarshaller<DepartmentXMLAssembler> serializer;
	private List<Department> departments = new ArrayList<Department>();
	private static String DEPARTMENTS_FILEPATH = "/departments.xml";

	public XMLDepartmentRepository() throws Exception {
		serializer = new XMLGenericMarshaller<DepartmentXMLAssembler>(DepartmentXMLAssembler.class);
		parseXML();
	}

	public XMLDepartmentRepository(XMLGenericMarshaller<DepartmentXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public Department findByName(String departmentName) {
		Department department = null;

		for (Department aDepartment : departments) {
			if (aDepartment.getName().equals(departmentName)) {
				department = aDepartment;
			}
		}
		return department;
	}

	@Override
	public void persist(Department department) throws Exception {
		if (departments.contains(department)) {
			throw new DepartmentAlreadyExistsException("Department already exists in repository.");
		}

		departments.add(department);
		saveXML();
	}

	@Override
	public void update(Department department) throws Exception {
		int index = departments.indexOf(department);
		if (index == -1) {
			throw new DepartmentNotFoundException("Department does not exist in repository.");
		}

		departments.set(index, department);
		saveXML();

	}

	private void saveXML() throws Exception {
		DepartmentXMLAssembler departmentAssembler = new DepartmentXMLAssembler();
		departmentAssembler.setDepartments(departments);
		serializer.marshall(departmentAssembler, DEPARTMENTS_FILEPATH);
	}

	private void parseXML() throws Exception {
		departments = serializer.unmarshall(DEPARTMENTS_FILEPATH).getDepartments();
	}

}
