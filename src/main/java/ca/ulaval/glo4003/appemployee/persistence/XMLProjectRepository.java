package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectRepository;

public class XMLProjectRepository implements ProjectRepository {

	private XMLSerializer<ProjectXMLAssembler> serializer;
	private Map<String, Project> projects = new HashMap<String, Project>();
	private static String PROJECTS_FILEPATH = "/resources/projects.xml";

	public XMLProjectRepository() throws Exception {
		serializer = new XMLSerializer<ProjectXMLAssembler>(ProjectXMLAssembler.class);
		parseXML();
	}

	public XMLProjectRepository(XMLSerializer<ProjectXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public Project findById(String projectId) {
		return projects.get(projectId);
	}

	@Override
	public Collection<Project> findAll() {
		return projects.values();
	}

	@Override
	public void store(Project project) throws Exception {
		projects.put(project.getuId(), project);
		saveXML();
	}

	private void saveXML() throws Exception {
		ProjectXMLAssembler projectXMLWrapper = new ProjectXMLAssembler();
		projectXMLWrapper.setProjects(new ArrayList<Project>(projects.values()));
		serializer.serialize(projectXMLWrapper, PROJECTS_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<Project> deserializedProjects = serializer.deserialize(PROJECTS_FILEPATH).getProjects();
		for (Project project : deserializedProjects) {
			projects.put(project.getuId(), project);
		}
	}

}
