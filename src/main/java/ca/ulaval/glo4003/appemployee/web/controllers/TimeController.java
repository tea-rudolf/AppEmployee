package ca.ulaval.glo4003.appemployee.web.controllers;
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.Shift;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Controller
@RequestMapping(value = "/time")
public class TimeController {
 
	//TODO: Get the UserRepository from the LoginController
	
    private PayPeriodService service ;
    private PayPeriodConverter payPeriodConverter ;
  
	@Autowired
	public TimeController(PayPeriodService timeService, PayPeriodConverter payPeriodConverter) {
		this.service = timeService; //TODO: Get the UserRepository from the LoginController
		this.payPeriodConverter = payPeriodConverter;
	}
    
    @RequestMapping(method = RequestMethod.GET )
    public String getTime(Map<String, Object> model) {
    	
    	//TODO: Get the email of the current session from the Login Controller 
    	User currentUser = service.getUserByEmail("test@test.com");
    	PayPeriod currentPayPeriod = currentUser.getCurrentPayPeriod();
    	
    	PayPeriodViewModel form = payPeriodConverter.convert(currentPayPeriod);
        model.put("payPeriodForm", form);
        model.put("username", currentUser.getEmail());
        //Debugging purposes
        System.out.println();
        System.out.println("Before");
        printUser(); 
        System.out.println();
        return "timeSheet";
    }
    
	@RequestMapping(method = RequestMethod.POST)
    public String saveTime(@ModelAttribute("payPeriodForm") PayPeriodViewModel payPeriodForm) {
    	
    	User currentUser = service.getUserByEmail("test@test.com");
        service.updateUserCurrentPayPeriodShiftList(currentUser.getEmail(), payPeriodConverter.convert(payPeriodForm));
        
      //Debugging purposes
        System.out.println();
        System.out.println("After");
        printUser(); 
        System.out.println();
        
        return "timeSheetSubmitted";
    }
	
	//Debugging purposes
	public void printUser() {
		User currentUser = service.getUserByEmail("test@test.com");
		PayPeriod pay = currentUser.getCurrentPayPeriod();
		List<Shift> list = pay.getShiftsWorked();
		System.out.println("  ** " + pay.getStartDate() + "  to  " + pay.getEndDate());
		for (Shift shift : list){
			System.out.println("      " + shift.getDate() + " - " + shift.getHours() + " - " + shift.getComment());
		}
	}
}