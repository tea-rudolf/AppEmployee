package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Component
public class PayPeriodConverter {

	public PayPeriodConverter() {

	}

	public PayPeriod convert(PayPeriodViewModel payPeriodViewModel) {
		LocalDate startDate = new LocalDate(payPeriodViewModel.getStartDate());
		LocalDate endDate = new LocalDate(payPeriodViewModel.getEndDate());
		List<String> timeEntryIds = payPeriodViewModel.getTimeEntryIds();
		PayPeriod payPeriod = new PayPeriod(startDate, endDate);
		payPeriod.setTimeEntryIds(timeEntryIds);

		return payPeriod;
	}

	public PayPeriodViewModel convert(PayPeriod payPeriod) {
		PayPeriodViewModel payPeriodViewModel = new PayPeriodViewModel();
		payPeriodViewModel.setStartDate(payPeriod.getStartDate().toString());
		payPeriodViewModel.setEndDate(payPeriod.getEndDate().toString());
		payPeriodViewModel.setTimeEntryIds(payPeriod.getTimeEntryIds());

		return payPeriodViewModel;
	}
}
