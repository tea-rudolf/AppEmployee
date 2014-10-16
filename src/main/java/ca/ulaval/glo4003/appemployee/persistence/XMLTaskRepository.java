package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;

public class XMLTaskRepository implements TaskRepository {

	private XMLSerializer<TaskXMLAssembler> serializer;
	private Map<String, Task> tasks = new HashMap<String, Task>();
	private static String TASKS_FILEPATH = "/resources/tasks.xml";

	public XMLTaskRepository() throws Exception {
		serializer = new XMLSerializer<TaskXMLAssembler>(TaskXMLAssembler.class);
		parseXML();
	}

	public XMLTaskRepository(XMLSerializer<TaskXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void store(Task task) throws Exception {
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

	private void saveXML() throws Exception {
		TaskXMLAssembler taskAssembler = new TaskXMLAssembler();
		taskAssembler.setTasks(new ArrayList<Task>(tasks.values()));
		serializer.serialize(taskAssembler, TASKS_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<Task> deserializedTasks = serializer.deserialize(TASKS_FILEPATH).getTasks();
		for (Task task : deserializedTasks) {
			tasks.put(task.getuId(), task);
		}
	}
}
