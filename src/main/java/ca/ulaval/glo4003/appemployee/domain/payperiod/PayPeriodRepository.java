package ca.ulaval.glo4003.appemployee.domain.payperiod;

import javax.inject.Singleton;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface PayPeriodRepository {

	void persist(PayPeriod payPeriod) throws Exception;

	void update(PayPeriod payPeriod) throws Exception;

	PayPeriod findByDate(LocalDate date);

}
