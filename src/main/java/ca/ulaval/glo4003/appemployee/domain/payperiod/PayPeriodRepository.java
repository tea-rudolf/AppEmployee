package ca.ulaval.glo4003.appemployee.domain.payperiod;

import java.util.List;

import javax.inject.Singleton;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;


@Repository
@Singleton
public interface PayPeriodRepository {

	void add(PayPeriod payPeriod);
	
	void update(PayPeriod payPeriod);

	PayPeriod findPayPeriod(LocalDate date);

	List<Integer> getAllTimeEntryUids(PayPeriod payPeriod);
	
}
