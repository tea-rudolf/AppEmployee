package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelConverterTest {
	
	private static final String FIRST_ID = "1";
	private static final double FIRST_DISTANCE = 100;
	private static final LocalDate FIRST_DATE = new LocalDate("2014-11-08");
	private static final String SECOND_ID = "2";
	private static final double SECOND_DISTANCE = 200;
	private static final LocalDate SECOND_DATE = new LocalDate("2014-11-07");
	private static final double EPSILON = 0.001;
	private static final String COMMENT = "comment";
	private static final String USER_EMAIL = "employee@employee.com";
	
	
	
	private TravelConverter travelConverter;
	private TravelViewModel travelViewModelMock;
	private Travel travelMock;
	
	@Before
	public void init(){
		travelViewModelMock = mock(TravelViewModel.class);
		travelMock = mock(Travel.class);
		travelConverter = new TravelConverter();
	}
	
	@Test
	public void convertTravelListsToViewModelsConvertAllOfThem(){
		Travel firstTravel = createTravel(FIRST_ID, FIRST_DISTANCE, FIRST_DATE);
		Travel secondTravel = createTravel(SECOND_ID, SECOND_DISTANCE, SECOND_DATE);
		List<Travel> travels = new ArrayList<Travel>();
		travels.add(firstTravel);
		travels.add(secondTravel);

		TravelViewModel[] viewModels = travelConverter.convert(travels).toArray(new TravelViewModel[1]);

		assertEquals(FIRST_DISTANCE, viewModels[0].getDistanceTravelled(), EPSILON);
		assertEquals(FIRST_ID, viewModels[0].getuId());
		assertEquals(FIRST_DATE.toString(), viewModels[0].getDate());

		assertEquals(SECOND_DISTANCE, viewModels[1].getDistanceTravelled(), EPSILON);
		assertEquals(SECOND_ID, viewModels[1].getuId());
		assertEquals(SECOND_DATE.toString(), viewModels[1].getDate());
	}
	
	@Test
	public void convertToTravelConvertsViewModelToTravel(){
		when(travelViewModelMock.getDistanceTravelled()).thenReturn(FIRST_DISTANCE);
		when(travelViewModelMock.getDate()).thenReturn(FIRST_DATE.toString());
		when(travelViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(travelViewModelMock.getComment()).thenReturn(COMMENT);
		
		travelMock = travelConverter.convertToTravel(travelViewModelMock);
		
		assertEquals(travelViewModelMock.getDistanceTravelled(), travelMock.getDistanceTravelled(), EPSILON);
		assertEquals(travelViewModelMock.getDate(), travelMock.getDate().toString());
		assertEquals(travelViewModelMock.getUserEmail(), travelMock.getUserEmail());
		assertEquals(travelViewModelMock.getComment(), travelMock.getComment());
	}
	
	@Test
	public void convertToViewModelConvertsTravelToViewModel(){
		when(travelMock.getuId()).thenReturn(FIRST_ID);
		when(travelMock.getDistanceTravelled()).thenReturn(FIRST_DISTANCE);
		when(travelMock.getComment()).thenReturn(COMMENT);
		when(travelMock.getDate()).thenReturn(FIRST_DATE);
		when(travelMock.getUserEmail()).thenReturn(USER_EMAIL);
		
		travelViewModelMock = travelConverter.convertToViewModel(travelMock);
		
		assertEquals(travelMock.getuId(), travelViewModelMock.getuId());
		assertEquals(travelMock.getDistanceTravelled(), travelViewModelMock.getDistanceTravelled(), EPSILON);
		assertEquals(travelMock.getComment(), travelViewModelMock.getComment());
		assertEquals(travelMock.getDate().toString(), travelViewModelMock.getDate());
		assertEquals(travelMock.getUserEmail(), travelViewModelMock.getUserEmail());
		
	}
	
	private Travel createTravel(String id, double distance, LocalDate date) {
		Travel travel = mock(Travel.class);
		given(travel.getDistanceTravelled()).willReturn(distance);
		given(travel.getuId()).willReturn(id);
		given(travel.getDate()).willReturn(date);
		return travel;
	}


}
