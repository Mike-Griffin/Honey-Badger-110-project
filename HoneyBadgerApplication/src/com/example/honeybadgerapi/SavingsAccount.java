package com.example.honeybadgerapi;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SavingsAccount extends Account {

	
	public SavingsAccount(int accountNumber) {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("accountNumber", accountNumber);
		query.whereEqualTo("type", "Saving Account");
		try {
			account = query.getFirst();
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		this.accountNumber = accountNumber;
		balance = account.getInt("balance");
		active = account.getBoolean("active");
		calculator = new SavingInterest();
		
	}

	@Override
	public void updateInterestRate() {
		// TODO Auto-generated method stub
		interestRate = calculator.calculate(dailyAverageBalance);
	}

}
