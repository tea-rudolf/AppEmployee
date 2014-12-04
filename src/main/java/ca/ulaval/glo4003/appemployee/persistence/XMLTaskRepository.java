package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@Repository
@Singleton
public class XMLTaskRepository implements TaskRepository {

	private XMLGenericMarshaller<TaskXMLAssembler> serializer;
	private Map<String, Task> tasks = new HashMap<String, Task>();
	private static String TASKS_FILEPATH = "/tasks.xml";

	public XMLTaskRepository() throws Exception {
		serializer = new XMLGenericMarshaller<TaskXMLAssembler>(
				TaskXMLAssembler.class);
		parseXML();
	}

	public XMLTaskRepository(XMLGenericMarshaller<TaskXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void store(Task task) throws Exception {
		tasks.put(task.getUid(), task);
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
	
	@Override
	public List<Task> findByUids(List<String> taskIds) {
		List<Task> tasks = new ArrayList<Task>();

		for (String taskId : taskIds) {
			Task task = findByUid(taskId);
			tasks.add(task);
		}
		return tasks;
	}

	private void saveXML() throws Exception {
		TaskXMLAssembler taskAssembler = new TaskXMLAssembler();
		taskAssembler.setTasks(new ArrayList<Task>(tasks.values()));
		serializer.marshall(taskAssembler, TASKS_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<Task> deserializedTasks = serializer.unmarshall(TASKS_FILEPATH)
				.getTasks();
		for (Task task : deserializedTasks) {
			tasks.put(task.getUid(), task);
		}
	}

}
