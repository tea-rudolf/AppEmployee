package ca.ulaval.glo4003.appemployee.domain.dao;

import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.Project;

@Repository
@Singleton
public interface ProjectRepository {
	
	Project findByNumber(Integer number);
	
	List<Project> findAll();
	
	void persist(Project project);
	
	void update(Project project);
	
}
