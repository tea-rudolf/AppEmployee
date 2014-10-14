package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;

@Repository
@Singleton
public class XmlPayPeriodRepository implements PayPeriodRepository {

	private XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	private List<PayPeriod> payPeriods = new ArrayList<PayPeriod>();

	public XmlPayPeriodRepository() {
		unmarshall();
	}

	public void add(PayPeriod payPeriod) {
		payPeriods.add(payPeriod);
		marshall();
	}
	
	public void update(PayPeriod payPeriod) {
		marshall();
	}
	
	public PayPeriod findPayPeriod(LocalDate date) {
		unmarshall();
		PayPeriod payPeriodFound = null;

		for (PayPeriod payPeriod : payPeriods) {
			if (date.isAfter(payPeriod.getStartDate()) && date.isBefore(payPeriod.getEndDate())) {
				payPeriodFound = payPeriod;
			}
		}
		
		if (payPeriodFound == null){
			throw new PayPeriodNotFoundException(String.format("Cannot find project pay period containing date '%s'.", date.toString()));
		}
			
		return payPeriodFound;
	}

	private void marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setPayPeriods(payPeriods);
		xmlRepositoryMarshaller.marshall();
	}

	private void unmarshall() {
		xmlRepositoryMarshaller.unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		payPeriods = xmlRootNode.getPayPeriods();
	}

	public List<Integer> getAllTimeEntryUids(PayPeriod payPeriod) {
		
		return payPeriod.getTimeEntryIds();
	
	}

}
