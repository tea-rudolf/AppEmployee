package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.HashMap;
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
	private HashMap<String, Department> departments = new HashMap<String, Department>();
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
		return departments.get(departmentName);
	}

	@Override
	public void store(Department department) throws Exception {
		if (departments.containsKey(department.getName())) {
			throw new DepartmentAlreadyExistsException("Department already exists in repository.");
		}

		departments.put(department.getName(), department);
		saveXML();
	}

	@Override
	public void update(Department department) throws Exception {
		if (!departments.containsKey(department.getName())) {
			throw new DepartmentNotFoundException("Department does not exist in repository.");
		}

		departments.put(department.getName(), department);
		saveXML();
	}

	private void saveXML() throws Exception {
		DepartmentXMLAssembler departmentAssembler = new DepartmentXMLAssembler();
		departmentAssembler.setDepartments(new ArrayList<Department>(departments.values()));
		serializer.marshall(departmentAssembler, DEPARTMENTS_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<Department> deserializedDepartments = serializer.unmarshall(DEPARTMENTS_FILEPATH).getDepartments();
		for (Department department : deserializedDepartments) {
			departments.put(department.getName(), department);
		}
	}

}
