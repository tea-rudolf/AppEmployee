package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface TaskRepository {

	void store(Task task) throws Exception;

	Task findByUid(String taskUId);

	List<Task> findAll();

}
