package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.Project;

public class XmlProjectRepository {
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
