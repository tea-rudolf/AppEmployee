package ca.ulaval.glo4003.appemployee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ca.ulaval.glo4003.appemployee.domain.UserRepository;
import ca.ulaval.glo4003.appemployee.persistence.MapUserRepository;
import ca.ulaval.glo4003.appemployee.web.controllers.security.UserSecurityService;

@Configuration
public class AppConfig {

	@Bean
	public UserRepository userRepository() {
		return new MapUserRepository();
	}

	@Bean(name = "userSecurityService")
	public UserSecurityService userSecurityService() throws Exception {
		return new UserSecurityService(userRepository());
	}

	@Bean
	ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
