package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.Collection;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface ProjectRepository {

	Project findById(String projectId);

	Collection<Project> findAll();

	void store(Project project) throws Exception;

}
