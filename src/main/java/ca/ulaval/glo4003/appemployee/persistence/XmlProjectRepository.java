package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;

@Repository
@Singleton
public class XmlProjectRepository implements ProjectRepository {
	static ArrayList<Project> projects = new ArrayList<Project>();
	
	public Project findByNumber(Integer number) {
		for(Project project : projects) {
			if (project.getNumber() == number) {
				return project;
			}
		}
		
		throw new RuntimeException(String.format("Cannot find project with number '%s'.", number));
	}
	
	public List<Project> findAll() {
		return projects;
	}
	
	public void persist(Project project) {
		projects.add(project);	
	}
	
	public void update(Project project) {
		
	}
}
