package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

public class TravelControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String TRAVEL_UID = "uid";
	private static final LocalDate START_DATE = new LocalDate("2014-10-13");
	private static final LocalDate END_DATE = new LocalDate("2014-10-26");
	private static final String TRAVEL_JSP = "travel";
	private static final String CREATE_TRAVEL_JSP = "createTravelEntry";
	private static final String TRAVEL_ENTRY_SUBMIT_JSP = "travelEntrySubmitted";
	private static final String VEHICLE = "ENTERPRISE";
	private static final String EDIT_TRAVEL_ENTRY_JSP = "editTravelEntry";
	private static final String TRAVEL_REDIRECT = "redirect:/travel/";

	// TODO: fix the tests

	@Mock
	private TimeService payPeriodServiceMock;

	@Mock
	private TaskRepository taskRepositoryMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private TravelConverter travelConverterMock;

	@Mock
	private TravelService travelServiceMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private ModelMap modelMapMock;

	@Mock
	private Model modelMock;

	@Mock
	private TravelViewModel travelViewModelMock;

	@Mock
	private Travel travelMock;

	@InjectMocks
	private TravelController travelController;

	@InjectMocks
	private List<Travel> travels = new ArrayList<Travel>();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		travelController = new TravelController(taskRepositoryMock, travelServiceMock);
	}

	@Test
	public void getTravelReturnsTravelFormIfSuccessful() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);

		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		String returnedForm = travelController.showTravelsList(modelMapMock, sessionMock);

		assertEquals(TRAVEL_JSP, returnedForm);
	}

	@Test
	public void createTravelEntryReturnsCreationFormIfSuccessful() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		String returnedForm = travelController.showCreateTravelEntryForm(modelMock, travelViewModelMock, sessionMock);

		assertEquals(CREATE_TRAVEL_JSP, returnedForm);
	}

	@Test
	public void saveTravelEntryReturnsSaveFormIfSuccessful() throws Exception {
		when(travelViewModelMock.getVehicle()).thenReturn(VEHICLE);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = travelController.createTravelEntry(modelMock, travelViewModelMock, sessionMock);
		assertEquals(TRAVEL_ENTRY_SUBMIT_JSP, returnedForm);
	}

//	@Test
//	public void saveTravelEntryCallsStoreMethod() throws Exception {
//		when(travelViewModelMock.getVehicle()).thenReturn(VEHICLE);
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(travelConverterMock.convert(travelViewModelMock)).thenReturn(travelMock);
//		travelController.createTravelEntry(modelMock, travelViewModelMock, sessionMock);
//		verify(travelServiceMock, times(1)).createTravel(travelViewModelMock);
//	}

	// @Test
	// public void saveTravelEntryReturnsCreateEntryFormIfMissingVehicle()
	// throws Exception {
	// when(travelViewModelMock.getVehicle()).thenReturn("NONE");
	// when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
	// when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
	// when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
	// when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
	// String returnedForm = travelController.saveTravelEntry(modelMock,
	// travelViewModelMock, sessionMock);
	// assertEquals(CREATE_TRAVEL_JSP, returnedForm);
	// }

	@Test
	public void editTravelEntryReturnsEditFormIfSuccessful() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(travelServiceMock.findByuId(TRAVEL_UID)).thenReturn(travelMock);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		when(travelConverterMock.convert(travelMock)).thenReturn(travelViewModelMock);

		String returnedForm = travelController.showEditTravelEntryForm(TRAVEL_UID, modelMock, sessionMock);

		assertEquals(EDIT_TRAVEL_ENTRY_JSP, returnedForm);
	}

	@Test
	public void saveEditedTravelEntryReturnsValidRedirectLinkIfSuccessFul() throws Exception {
		when(travelViewModelMock.getVehicle()).thenReturn(VEHICLE);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = travelController.editTravelEntry(TRAVEL_UID, modelMock, travelViewModelMock, sessionMock);
		assertEquals(TRAVEL_REDIRECT, returnedForm);
	}

	@Test
	public void saveEditedTravelEntryCallsUpdateMethod() throws Exception {
		when(travelViewModelMock.getVehicle()).thenReturn(VEHICLE);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		travelController.editTravelEntry(TRAVEL_UID, modelMock, travelViewModelMock, sessionMock);
		verify(travelServiceMock, times(1)).updateTravel(TRAVEL_UID, travelViewModelMock);
	}

	// @Test
	// public void saveEditedTravelEntryReturnsEditEntryFormIfMissingVehicle()
	// throws Exception {
	// when(travelViewModelMock.getVehicle()).thenReturn("NONE");
	// when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
	// when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
	// when(travelServiceMock.findByuId(TRAVEL_UID)).thenReturn(travelMock);
	// when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
	// when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
	// when(travelConverterMock.convert(travelMock)).thenReturn(travelViewModelMock);
	// String returnedForm = travelController.saveEditedTravelEntry(TRAVEL_UID,
	// modelMock, travelViewModelMock, sessionMock);
	// assertEquals(EDIT_TRAVEL_ENTRY_JSP, returnedForm);
	// }

}
