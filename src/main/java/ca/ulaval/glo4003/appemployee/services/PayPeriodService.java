package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.dao.UserRepository;
import ca.ulaval.glo4003.appemployee.exceptions.NoCurrentPayPeriod;
import ca.ulaval.glo4003.appemployee.exceptions.UserNotFoundException;

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
			PayPeriod payPeriod) throws UserNotFoundException, NoCurrentPayPeriod {
		User user = userRepository.findByEmail(email);
		user.getCurrentPayPeriod().setShiftsWorked(payPeriod.getShiftsWorked());
	}
	
	
	public void updateUserCurrentPayPeriodExpenses(String email,
			PayPeriod payPeriod) throws UserNotFoundException, NoCurrentPayPeriod {
		User user = userRepository.findByEmail(email);
		user.getCurrentPayPeriod().setExpenses(payPeriod.getExpenses());
	}
	
}