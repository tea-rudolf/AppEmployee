package ca.ulaval.glo4003.appemployee;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

public class AuthenticationInterceptorTest {

	@Mock
	private HttpServletRequest requestMock;

	@Mock
	private HttpServletResponse responseMock;

	@Mock
	private Object handlerMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private LoginFormViewModel loginViewModelMock;

	@InjectMocks
	private AuthenticationInterceptor interceptor;

	private static String INDEX_PAGE = "/";
	private static String LOGIN_PAGE = "/login";
	private static String OTHER_PAGE = "/dummyPage";
	private static String SESSION_ATTRIBUTE = "LOGGEDIN_USER";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		interceptor = new AuthenticationInterceptor();
	}

	@Test
	public void canInstantiateInterceptor() {
		assertNotNull(interceptor);
	}

	@Test
	public void preHandleReturnsTrueWhenRequestURIIsIndexPage() throws Exception {
		when(requestMock.getRequestURI()).thenReturn(INDEX_PAGE);
		boolean response = interceptor.preHandle(requestMock, responseMock, handlerMock);
		assertTrue(response);
	}

	@Test
	public void preHandleReturnsTrueWhenRequestURIIsLoginPage() throws Exception {
		when(requestMock.getRequestURI()).thenReturn(LOGIN_PAGE);
		boolean response = interceptor.preHandle(requestMock, responseMock, handlerMock);
		assertTrue(response);
	}

	@Test
	public void preHandleReturnsTrueWhenUserDataNotNull() throws Exception {
		when(requestMock.getRequestURI()).thenReturn(OTHER_PAGE);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(SESSION_ATTRIBUTE)).thenReturn(loginViewModelMock);

		boolean response = interceptor.preHandle(requestMock, responseMock, handlerMock);

		assertTrue(response);
	}

	@Test
	public void preHandleReturnsFalseWhenUserDataIsNull() throws Exception {
		when(requestMock.getRequestURI()).thenReturn(OTHER_PAGE);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(SESSION_ATTRIBUTE)).thenReturn(null);

		boolean response = interceptor.preHandle(requestMock, responseMock, handlerMock);

		assertFalse(response);
	}

	@Test
	public void preHandleRedirectsToIndexPageWhenUserDataIsNull() throws Exception {
		when(requestMock.getRequestURI()).thenReturn(OTHER_PAGE);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(SESSION_ATTRIBUTE)).thenReturn(null);

		interceptor.preHandle(requestMock, responseMock, handlerMock);

		verify(responseMock, times(1)).sendRedirect(INDEX_PAGE);
	}
}
