package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface ProjectRepository {
	
	Project getByNumber(String number);
	
	List<Project> getAll();
	
	void persist(Project project);
	
	void update(Project project);
	
}
