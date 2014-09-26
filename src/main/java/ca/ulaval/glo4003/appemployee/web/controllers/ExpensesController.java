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
	
		//TODO: Get the UserRepository from the LoginController
		
	    private PayPeriodService payPeriodService;
	    private PayPeriodConverter payPeriodConverter;
	    private User user;
	  
		@Autowired
		public ExpensesController(PayPeriodService payPeriodService, PayPeriodConverter payPeriodConverter) {
			this.payPeriodService = payPeriodService; //TODO: Get the UserRepository from the LoginController
			this.payPeriodConverter = payPeriodConverter;
		}
		
		@RequestMapping(method = RequestMethod.GET )
	    public String getExpenses(Map<String, Object> model, HttpSession session) {
	    	
	    	//TODO: Get the email of the current session from the Login Controller 
	    	user = payPeriodService.getUserByEmail(session.getAttribute("email").toString());
	    	PayPeriod currentPayPeriod = user.getCurrentPayPeriod();
	    	List<Expenses> expenses = currentPayPeriod.getExpenses();  	
	    	
	    	PayPeriodViewModel form = new PayPeriodViewModel();
	    	form.setStartDate(currentPayPeriod.getStartDate().toString());
	    	form.setEndDate(currentPayPeriod.getEndDate().toString());
	        form.setExpenses(expenses);

	        model.put("payPeriodForm", form);
	        
	        //Debugging purposes
	        System.out.println();
	        System.out.println("Before");
	        printUser(); 
	        System.out.println();
	        //
	        
	        return "expenses";
	    }
		
		@RequestMapping(method = RequestMethod.POST)
	    public String saveExpenses(@ModelAttribute("payPeriodForm") PayPeriodViewModel payPeriodForm, HttpSession session) {
	    	
	    	user = payPeriodService.getUserByEmail(session.getAttribute("email").toString());
	    	payPeriodService.updateUserCurrentPayPeriodExpenses(user.getEmail(), payPeriodConverter.convert(payPeriodForm));
	        
	      //Debugging purposes
	        System.out.println();
	        System.out.println("After");
	        printUser(); 
	        System.out.println();
	        
	        return "expensesSubmitted";
	    }
		
		@RequestMapping(value = "/logout")
		public String logout(SessionStatus sessionStatus, ModelMap model) {
			sessionStatus.setComplete();
			model.clear();
			return "redirect:/";
		}
		
		//Debugging purposes
		public void printUser() {
			User currentUser = payPeriodService.getUserByEmail(user.getEmail());
			PayPeriod pay = currentUser.getCurrentPayPeriod();
			List<Expenses> list = pay.getExpenses();
			List<Shift> list1 = pay.getShiftsWorked();
			System.out.println("  ** " + pay.getStartDate() + "  to  " + pay.getEndDate());
			System.out.println("Number of expenses = " + pay.getExpenses().size());
			for (Expenses expenses : list){
				System.out.println("      " + expenses.getDate() + " - " + expenses.getAmount() + " - " + expenses.getComment());
			}
			System.out.println("Shifts size = " + pay.getShiftsWorked().size());
			for (Shift s : list1){
				System.out.println("      " + s.getDate() + " - " + s.getHours() + " - " + s.getComment());
			}
		}
}
