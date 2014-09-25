package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.Task;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.dao.TaskRepository;


@Repository
@Singleton
public class XmlTaskRepository implements TaskRepository {
	
	XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	public List<Task> tasks;

	public Task findByNumber(Integer number) {
		Unmarshall();
		
		for(Task task : tasks) {
			if (task.getNumber() == number) {
				return task;
			}
		}
		
		throw new RuntimeException(String.format("Cannot find task with number '%s'.", number));
	}
	
	public List<Task> findAll() {
		Unmarshall();
		return tasks;
	}
	
	public void persist(Task task) {
		tasks.add(task);	
		Marshall();
	}
	
	public void update(Task task) {
		Marshall();
	}
	
	private void Marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setTasks(tasks);
		xmlRepositoryMarshaller.Marshall();
	}
	
	private void Unmarshall() {
		xmlRepositoryMarshaller.Unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		tasks = xmlRootNode.getTasks();
	}
}


