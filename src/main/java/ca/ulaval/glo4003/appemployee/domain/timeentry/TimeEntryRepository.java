package ca.ulaval.glo4003.appemployee.domain.timeentry;

import javax.inject.Singleton;
import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface TimeEntryRepository {
	
	void add(TimeEntry timeEntry);
	
	void update(TimeEntry timeEntry);

	TimeEntry getByUid(Integer id);
	
}
