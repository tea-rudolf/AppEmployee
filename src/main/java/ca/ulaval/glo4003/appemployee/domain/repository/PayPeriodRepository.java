package ca.ulaval.glo4003.appemployee.domain.repository;

import javax.inject.Singleton;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;

@Repository
@Singleton
public interface PayPeriodRepository {

	void store(PayPeriod payPeriod) throws Exception;

	void update(PayPeriod payPeriod) throws Exception;

	PayPeriod findByDate(LocalDate date);

}
