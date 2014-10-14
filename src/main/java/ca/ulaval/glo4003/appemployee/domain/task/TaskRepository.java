package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.List;
import javax.inject.Singleton;
import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface TaskRepository {
	
	void add(Task Task);
	
	void update(Task Task);
	
	Task getByUid(Integer id);
	
	List<Task> getAll();
	
}

