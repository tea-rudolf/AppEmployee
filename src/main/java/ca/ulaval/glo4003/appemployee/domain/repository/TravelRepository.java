package ca.ulaval.glo4003.appemployee.domain.repository;

import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;

@Repository
@Singleton
public interface TravelRepository {
	
	void store(Travel travel) throws Exception;

	List<Travel> findAlltravelsByUser(String userId);

	Travel findByUid(String uId);
	
	Collection<Travel> findAll();

}
