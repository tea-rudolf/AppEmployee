package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;

@Service
public class PayPeriodService {
	
	private UserRepository userRepository;
	
	@Autowired
	public PayPeriodService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUserByEmail(String email) throws UserNotFoundException {
		return userRepository.findByEmail(email);
	}
	
	public void updateUserCurrentPayPeriod(User user) throws UserNotFoundException {
		userRepository.update(user);
	}

	public void updateUserCurrentPayPeriodShiftList(String email,
			PayPeriod payPeriod) {
		User user = userRepository.findByEmail(email);
		user.getCurrentPayPeriod().setShiftsWorked(payPeriod.getShiftsWorked());
		userRepository.update(user);
	}
	
	
	public void updateUserCurrentPayPeriodExpenses(String email,
			PayPeriod payPeriod) {
		User user = userRepository.findByEmail(email);
		user.getCurrentPayPeriod().setExpenses(payPeriod.getExpenses());
		userRepository.update(user);
	}
	
}