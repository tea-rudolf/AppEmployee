package ca.ulaval.glo4003.appemployee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (!request.getRequestURI().equals("/")
				&& !request.getRequestURI().equals("/login")) {
			LoginFormViewModel userData = (LoginFormViewModel) request
					.getSession().getAttribute("LOGGEDIN_USER");
			if (userData == null) {
				response.sendRedirect("/");
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
