package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;

@XmlRootElement(name = "payPeriods")
public class PayPeriodXMLAssembler {

	private List<PayPeriod> payPeriods = new ArrayList<PayPeriod>();

	public List<PayPeriod> getPayPeriods() {
		return this.payPeriods;
	}

	public void setPayPeriods(List<PayPeriod> payPeriods) {
		this.payPeriods = payPeriods;
	}

}
