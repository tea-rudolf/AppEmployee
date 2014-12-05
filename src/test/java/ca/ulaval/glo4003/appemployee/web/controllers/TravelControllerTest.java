package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

public class TravelControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String TRAVEL_UID = "uid";
	private static final String TRAVEL_JSP = "travel";
	private static final String CREATE_TRAVEL_JSP = "createTravelEntry";
	private static final String EDIT_TRAVEL_ENTRY_JSP = "editTravelEntry";
	private static final String TRAVEL_REDIRECT = "redirect:/travel/";
	private static final String EDIT_TRAVEL_REDIRECT = "redirect:/travel/uid/edit";

	private Collection<TravelViewModel> travelViewModels;

	@Mock
	private TimeService timeServiceMock;

	@Mock
	private TravelService travelServiceMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private ModelMap modelMapMock;

	@Mock
	private Model modelMock;

	@Mock
	private TravelViewModel travelViewModelMock;

	@InjectMocks
	private TravelController travelController;

	@InjectMocks
	private List<Travel> travels = new ArrayList<Travel>();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		travelController = new TravelController(travelServiceMock,
				timeServiceMock);
	}

	@Test
	public void retrieveUserTravelViewModelCallsTravelService() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		travelController.retrieveUserTravelViewModel(sessionMock);
		verify(travelServiceMock, times(1)).retrieveUserTravelViewModel(
				VALID_EMAIL);
	}

	@Test
	public void retrieveCurrentPayPeriodViewModelCallsTravelService() {
		travelController.retrieveCurrentPayPeriodViewModel();
		verify(timeServiceMock, times(1)).retrieveCurrentPayPeriodViewModel();
	}

	@Test
	public void showTravelsListReturnsTravelFormIfSessionAttributeIsNotNull() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(
				travelServiceMock
						.retrieveUserTravelViewModelsForCurrentPayPeriod(VALID_EMAIL))
				.thenReturn(travelViewModels);

		String returnedForm = travelController.showTravelsList(modelMapMock,
				sessionMock);

		assertEquals(TRAVEL_JSP, returnedForm);
	}

	@Test
	public void showCreateTravelEntryFormReturnsCreationForm() {
		String returnedForm = travelController.showCreateTravelEntryForm(
				modelMock, travelViewModelMock, sessionMock);
		assertEquals(CREATE_TRAVEL_JSP, returnedForm);
	}

	@Test
	public void createTravelEntryReturnsSaveFormIfSuccessful() throws Exception {
		String returnedForm = travelController.createTravelEntry(modelMock,
				travelViewModelMock, sessionMock);
		assertEquals(TRAVEL_REDIRECT, returnedForm);
	}

	@Test
	public void createTravelEntryCallsCorrectServiceMethod() throws Exception {
		travelController.createTravelEntry(modelMock, travelViewModelMock,
				sessionMock);
		verify(travelServiceMock, times(1)).createTravel(travelViewModelMock);
	}

	@Test
	public void editTravelEntryReturnsEditFormIfSuccessful() throws Exception {
		when(travelServiceMock.retrieveTravelViewModel(TRAVEL_UID)).thenReturn(
				travelViewModelMock);
		String returnedForm = travelController.showEditTravelEntryForm(
				TRAVEL_UID, modelMock, sessionMock);
		assertEquals(EDIT_TRAVEL_ENTRY_JSP, returnedForm);
	}

	@Test
	public void editTravelEntryReturnsValidRedirectLinkIfSuccessFul()
			throws Exception {
		String returnedForm = travelController.editTravelEntry(TRAVEL_UID,
				modelMock, travelViewModelMock, sessionMock);
		assertEquals(EDIT_TRAVEL_REDIRECT, returnedForm);
	}

	@Test
	public void editTravelEntryCallsServiceUpdateMethod() throws Exception {
		travelController.editTravelEntry(TRAVEL_UID, modelMock,
				travelViewModelMock, sessionMock);
		verify(travelServiceMock, times(1)).editTravel(TRAVEL_UID,
				travelViewModelMock);
	}
}
