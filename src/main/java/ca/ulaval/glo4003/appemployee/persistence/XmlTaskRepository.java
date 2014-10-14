package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import org.springframework.stereotype.Repository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;

@Repository
@Singleton
public class XmlTaskRepository implements TaskRepository {

	private XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	private List<Task> tasks = new ArrayList<Task>();

	public XmlTaskRepository() {
		unmarshall();
	}

	public Task getByUid(Integer id) {
		unmarshall();

		for (Task task : tasks) {
			if (task.getuId() == id) {
				return task;
			}
		}

		throw new TaskNotFoundException(String.format("Cannot find Task with id '%s'.", id));
	}

	public List<Task> getAll() {
		unmarshall();
		return tasks;
	}

	public void add(Task Task) {

		tasks.add(Task);
		marshall();
	}

	public void update(Task Task) {
		marshall();
	}

	private void marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setTasks(tasks);
		xmlRepositoryMarshaller.marshall();
	}

	private void unmarshall() {
		xmlRepositoryMarshaller.unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		tasks = xmlRootNode.getTasks();
	}

}
