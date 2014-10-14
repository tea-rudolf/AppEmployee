package ca.ulaval.glo4003.appemployee.domain.expense;

import java.util.List;

import javax.inject.Singleton;
import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface ExpenseRepository {

	void add(Expense expense);

	void update(Expense expense);
	
	List<Expense> findAllExpensesForUser(String userId);
	
}
