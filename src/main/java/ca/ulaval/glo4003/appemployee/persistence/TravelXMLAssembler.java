package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;

@XmlRootElement(name = "travels")
public class TravelXMLAssembler {

	private List<Travel> travels = new ArrayList<Travel>();

	public List<Travel> getTravels() {
		return this.travels;
	}

	public void setTravels(List<Travel> travels) {
		this.travels = travels;
	}

}
