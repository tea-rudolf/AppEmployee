package ca.ulaval.glo4003.appemployee.domain.repository;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.department.Department;

@Repository
@Singleton
public interface DepartmentRepository {

	Department findByName(String departmentName);

	void store(Department department) throws Exception;

	void update(Department department) throws Exception;

}
