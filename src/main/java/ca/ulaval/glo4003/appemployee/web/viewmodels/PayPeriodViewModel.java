package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.appemployee.domain.Expenses;
import ca.ulaval.glo4003.appemployee.domain.Shift;
 
public class PayPeriodViewModel {
	
	private String startDate;
	private String endDate;
    private List<Shift> shifts;
    private List<Expenses> expenses;
	
	public PayPeriodViewModel() {
		shifts = new ArrayList<Shift>();
		expenses = new ArrayList<Expenses>();
	}
 
    public List<Shift> getShifts() {
        return shifts;
    }
 
    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
    
    public List<Expenses> getExpenses(){
    	return expenses;
    }
    
    public void setExpenses(List<Expenses> expenses){
    	this.expenses = expenses;
    }

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}