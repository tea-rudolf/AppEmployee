package ca.ulaval.glo4003.appemployee.domain.repository;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;

@Repository
@Singleton
public interface TimeEntryRepository {

	void store(TimeEntry timeEntry) throws Exception;

	TimeEntry findByUid(String id);

}
