package ca.ulaval.glo4003.appemployee.persistence;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.SerializationException;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;

@Repository
@Singleton
public class XMLTaskRepository implements TaskRepository {

	private XMLGenericMarshaller<TaskXMLAssembler> serializer;
	private Map<String, Task> tasks = new HashMap<String, Task>();
	private static String TASKS_FILEPATH = "/tasks.xml";

	public XMLTaskRepository() throws JAXBException {
		serializer = new XMLGenericMarshaller<TaskXMLAssembler>(TaskXMLAssembler.class);
		parseXML();
	}

	public XMLTaskRepository(XMLGenericMarshaller<TaskXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void store(Task task) throws FileNotFoundException, JAXBException, URISyntaxException{
		tasks.put(task.getuId(), task);
		saveXML();
	}

	@Override
	public Task findByUid(String taskUId) {
		return tasks.get(taskUId);
	}

	@Override
	public List<Task> findAll() {
		return new ArrayList<Task>(tasks.values());
	}

	private void saveXML() throws FileNotFoundException, JAXBException, URISyntaxException  {
		TaskXMLAssembler taskAssembler = new TaskXMLAssembler();
		taskAssembler.setTasks(new ArrayList<Task>(tasks.values()));
		serializer.marshall(taskAssembler, TASKS_FILEPATH);
	}

	private void parseXML() throws SerializationException, JAXBException {
		List<Task> deserializedTasks = serializer.unmarshall(TASKS_FILEPATH).getTasks();
		for (Task task : deserializedTasks) {
			tasks.put(task.getuId(), task);
		}
	}
}
