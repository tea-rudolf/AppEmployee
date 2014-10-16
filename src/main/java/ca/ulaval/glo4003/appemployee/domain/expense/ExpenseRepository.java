package ca.ulaval.glo4003.appemployee.domain.expense;

import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface ExpenseRepository {

	void store(Expense expense) throws Exception;

	List<Expense> findAllExpensesByUser(String userId);

}
