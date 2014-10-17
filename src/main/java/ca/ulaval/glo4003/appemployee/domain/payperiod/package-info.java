@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class) })
package ca.ulaval.glo4003.appemployee.domain.payperiod;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import org.joda.time.LocalDate;

import ca.ulaval.glo4003.appemployee.LocalDateAdapter;

