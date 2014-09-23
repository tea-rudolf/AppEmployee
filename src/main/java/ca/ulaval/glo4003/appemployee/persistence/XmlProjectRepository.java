package ca.ulaval.glo4003.appemployee.persistence;

import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;

@Repository
@Singleton
public class XmlProjectRepository implements ProjectRepository {
	
	XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	public List<Project> projects;

	public Project findByNumber(Integer number) {
		Unmarshall();
		
		for(Project project : projects) {
			if (project.getNumber() == number) {
				return project;
			}
		}
		
		throw new RuntimeException(String.format("Cannot find project with number '%s'.", number));
	}
	
	public List<Project> findAll() {
		Unmarshall();
		return projects;
	}
	
	public void persist(Project project) {
		projects.add(project);	
		Marshall();
	}
	
	public void update(Project project) {
		Marshall();
	}
	
	private void Marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setProjects(projects);
		xmlRepositoryMarshaller.Marshall();
	}
	
	private void Unmarshall() {
		xmlRepositoryMarshaller.Unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		projects = xmlRootNode.getProjects();
	}
}
