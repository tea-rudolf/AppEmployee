package ca.ulaval.glo4003.appemployee.persistence;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.SerializationException;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;

@Repository
@Singleton
public class XMLPayPeriodRepository implements PayPeriodRepository {

	private XMLGenericMarshaller<PayPeriodXMLAssembler> serializer;
	private List<PayPeriod> payPeriods = new ArrayList<PayPeriod>();
	private static String PAYPERIODS_FILEPATH = "/payPeriods.xml";

	public XMLPayPeriodRepository() throws JAXBException  {
		serializer = new XMLGenericMarshaller<PayPeriodXMLAssembler>(PayPeriodXMLAssembler.class);
		parseXML();
	}

	public XMLPayPeriodRepository(XMLGenericMarshaller<PayPeriodXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void persist(PayPeriod payPeriod) throws FileNotFoundException, JAXBException, URISyntaxException, PayPeriodAlreadyExistsException  {
		if (payPeriods.contains(payPeriod)) {
			throw new PayPeriodAlreadyExistsException("PayPeriod already exists in repository.");
		}

		payPeriods.add(payPeriod);
		saveXML();
	}

	@Override
	public void update(PayPeriod payPeriod) throws FileNotFoundException, JAXBException, URISyntaxException  {
		int index = payPeriods.indexOf(payPeriod);
		if (index == -1) {
			throw new PayPeriodNotFoundException();
		}
		payPeriods.set(index, payPeriod);
		saveXML();
	}

	@Override
	public PayPeriod findByDate(LocalDate date) {

		for (PayPeriod payPeriod : payPeriods) {
			if (date.isAfter(payPeriod.getStartDate().minusDays(1)) && date.isBefore(payPeriod.getEndDate().plusDays(1))) {
				return payPeriod;
			}
		}

		throw new PayPeriodNotFoundException("Cannot find project pay period containing date " + date.toString());
	}

	private void saveXML() throws FileNotFoundException, JAXBException, URISyntaxException {
		PayPeriodXMLAssembler payPeriodAssembler = new PayPeriodXMLAssembler();
		payPeriodAssembler.setPayPeriods(payPeriods);
		serializer.marshall(payPeriodAssembler, PAYPERIODS_FILEPATH);
	}

	private void parseXML() throws SerializationException, JAXBException {
		payPeriods = serializer.unmarshall(PAYPERIODS_FILEPATH).getPayPeriods();
	}

}
