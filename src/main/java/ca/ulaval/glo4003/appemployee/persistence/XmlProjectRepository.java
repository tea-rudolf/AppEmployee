package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectRepository;

@Repository
@Singleton
public class XmlProjectRepository implements ProjectRepository {
	
	XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	public List<Project> projects = new ArrayList<Project>();

	public XmlProjectRepository() {
		unmarshall();
	}
	
	public Project getByNumber(String number) {
		unmarshall();
		
		for(Project project : projects) {
			if (project.getNumber().compareTo(number) == 0) {
				return project;
			}
		}
		
		throw new ProjectNotFoundException(String.format("Cannot find project with number '%s'.", number));
	}
	
	public List<Project> getAll() {
		unmarshall();
		return projects;
	}
	
	public void persist(Project project) {
		try {
			getByNumber(project.getNumber());
			throw new ProjectExistsException(String.format("Project number '%s' already exists.", project.getNumber()));
		} catch (ProjectNotFoundException e) {};
		
		projects.add(project);	
		marshall();
	}
	
	public void update(Project project) {
		marshall();
	}
	
	private void marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setProjects(projects);
		xmlRepositoryMarshaller.Marshall();
	}
	
	private void unmarshall() {
		xmlRepositoryMarshaller.Unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		projects = xmlRootNode.getProjects();
	}
}
