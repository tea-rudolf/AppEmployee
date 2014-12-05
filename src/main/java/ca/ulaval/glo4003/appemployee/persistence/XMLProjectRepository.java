package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.repository.ProjectRepository;

@Repository
@Singleton
public class XMLProjectRepository implements ProjectRepository {

	private XMLGenericMarshaller<ProjectXMLAssembler> serializer;
	private Map<String, Project> projects = new HashMap<String, Project>();
	private static String PROJECTS_FILEPATH = "/projects.xml";

	public XMLProjectRepository() throws Exception {
		serializer = new XMLGenericMarshaller<ProjectXMLAssembler>(
				ProjectXMLAssembler.class);
		parseXML();
	}

	public XMLProjectRepository(
			XMLGenericMarshaller<ProjectXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public Project findById(String projectId) {
		return projects.get(projectId);
	}
	
	@Override
	public Project findByName(String name) {
		Collection<Project> projects = findAll();
		for(Project project : projects){
			if  (project.getName().equals(name)){
				return project;
			}	
		}
		return null;
	}

	@Override
	public Collection<Project> findAll() {
		return projects.values();
	}

	@Override
	public void store(Project project) throws Exception {
		projects.put(project.getUid(), project);
		saveXML();
	}

	private void saveXML() throws Exception {
		ProjectXMLAssembler projectXMLWrapper = new ProjectXMLAssembler();
		projectXMLWrapper
				.setProjects(new ArrayList<Project>(projects.values()));
		serializer.marshall(projectXMLWrapper, PROJECTS_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<Project> deserializedProjects = serializer.unmarshall(
				PROJECTS_FILEPATH).getProjects();
		for (Project project : deserializedProjects) {
			projects.put(project.getUid(), project);
		}
	}

}
