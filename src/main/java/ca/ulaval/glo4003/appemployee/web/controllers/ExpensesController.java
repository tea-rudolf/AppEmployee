package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ca.ulaval.glo4003.appemployee.domain.Expenses;
import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.Shift;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Controller
@RequestMapping(value = "/expenses")
@SessionAttributes({"email"})
public class ExpensesController {
	
		private PayPeriodService payPeriodService;
	    private PayPeriodConverter payPeriodConverter;
	    private User user;
	  
		@Autowired
		public ExpensesController(PayPeriodService payPeriodService, PayPeriodConverter payPeriodConverter) {
			this.payPeriodService = payPeriodService; 
			this.payPeriodConverter = payPeriodConverter;
		}
		
		@RequestMapping(method = RequestMethod.GET )
	    public String getExpenses(Map<String, Object> model, HttpSession session) {
	    	
	    	user = payPeriodService.getUserByEmail(session.getAttribute("email").toString());
	    	PayPeriod currentPayPeriod = user.getCurrentPayPeriod();
	    	List<Expenses> expenses = currentPayPeriod.getExpenses();  	
	    	
	    	PayPeriodViewModel form = new PayPeriodViewModel();
	    	form.setStartDate(currentPayPeriod.getStartDate().toString());
	    	form.setEndDate(currentPayPeriod.getEndDate().toString());
	        form.setExpenses(expenses);

	        model.put("payPeriodForm", form);
	        
	        return "expenses";
	    }
		
		@RequestMapping(method = RequestMethod.POST)
	    public String saveExpenses(@ModelAttribute("payPeriodForm") PayPeriodViewModel payPeriodForm, HttpSession session) {
	    	
	    	user = payPeriodService.getUserByEmail(session.getAttribute("email").toString());
	    	payPeriodService.updateUserCurrentPayPeriodExpenses(user.getEmail(), payPeriodConverter.convert(payPeriodForm));
	        
	        return "expensesSubmitted";
	    }
		
		@RequestMapping(value = "/logout")
		public String logout(SessionStatus sessionStatus, ModelMap model) {
			sessionStatus.setComplete();
			model.clear();
			return "redirect:/";
		}
		
}
