package ca.ulaval.glo4003.appemployee.domain.repository;

import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;

@Repository
@Singleton
public interface ExpenseRepository {

	void store(Expense expense) throws Exception;

	List<Expense> findAllExpensesByUser(String userId);

	Expense findByUid(String uId);

}
