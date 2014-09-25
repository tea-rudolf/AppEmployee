package ca.ulaval.glo4003.appemployee.domain.dao;

import java.util.List;

import ca.ulaval.glo4003.appemployee.domain.Task;

public interface TaskRepository {
	
	Task findByNumber(String number);
	
	List<Task> findAll();
	
	void persist(Task Task);
	
	void update(Task task);
	
}
