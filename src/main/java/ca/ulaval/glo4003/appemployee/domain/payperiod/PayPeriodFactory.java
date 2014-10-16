package ca.ulaval.glo4003.appemployee.domain.payperiod;

import org.joda.time.LocalDate;

public class PayPeriodFactory {
	
	public PayPeriod createPayPeriod(String startDate, String endDate){
		return new PayPeriod(new LocalDate(startDate), new LocalDate(endDate));
	}

}
