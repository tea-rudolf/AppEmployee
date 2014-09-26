package ca.ulaval.glo4003.appemployee.web.controllers;
 
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.Shift;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Controller
@RequestMapping(value = "/time")
@SessionAttributes({"email"})
public class TimeController {
 
	//TODO: Get the UserRepository from the LoginController
	
    private PayPeriodService service ;
    private PayPeriodConverter payPeriodConverter ;
    private User user;
  
	@Autowired
	public TimeController(PayPeriodService timeService, PayPeriodConverter payPeriodConverter) {
		this.service = timeService; //TODO: Get the UserRepository from the LoginController
		this.payPeriodConverter = payPeriodConverter;
	}
    
    @RequestMapping(method = RequestMethod.GET )
    public String getTime(ModelMap model, HttpSession session) {
    	
    	user = service.getUserByEmail(session.getAttribute("email").toString());
    	PayPeriod currentPayPeriod = user.getCurrentPayPeriod();
    	PayPeriodViewModel form = payPeriodConverter.convert(currentPayPeriod);
        model.addAttribute("payPeriodForm", form);
        model.addAttribute("email", user.getEmail());
        
        //Debugging purposes
        System.out.println();
        System.out.println("Before");
        printUser(); 
        System.out.println();

        return "timeSheet";
    }
    
	@RequestMapping(method = RequestMethod.POST)
    public String saveTime(@ModelAttribute("payPeriodForm") PayPeriodViewModel payPeriodForm, HttpSession session) {
    	
    	user = service.getUserByEmail(session.getAttribute("email").toString());
        service.updateUserCurrentPayPeriodShiftList(user.getEmail(), payPeriodConverter.convert(payPeriodForm));
        
      //Debugging purposes
        System.out.println();
        System.out.println("After");
        printUser(); 
        System.out.println();
        
        return "timeSheetSubmitted";
    }
	
	@RequestMapping(value = "/logout")
	public String logout(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		model.clear();
		return "redirect:/";
	}
	
	//Debugging purposes
	public void printUser() {
		User currentUser = service.getUserByEmail(user.getEmail());
		PayPeriod pay = currentUser.getCurrentPayPeriod();
		List<Shift> list = pay.getShiftsWorked();
		System.out.println("  ** " + pay.getStartDate() + "  to  " + pay.getEndDate());
		for (Shift shift : list){
			System.out.println("      " + shift.getDate() + " - " + shift.getHours() + " - " + shift.getComment());
		}
	}
}