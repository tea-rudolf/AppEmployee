package ca.ulaval.glo4003.appemployee.domain.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.Task;

public interface TaskRepository {
	
	Task findByNumber(Integer number);
	
	List<Task> findAll();
	
	void persist(Task Task);
	
	void update(Task task);
	
}
