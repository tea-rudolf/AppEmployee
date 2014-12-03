package ca.ulaval.glo4003.appemployee.domain.repository;

import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.task.Task;

@Repository
@Singleton
public interface TaskRepository {

	void store(Task task) throws Exception;

	Task findByUid(String taskUid);

	List<Task> findAll();

}
