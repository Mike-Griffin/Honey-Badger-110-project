package com.example.honeybadgerapi;

public class CheckingAccount extends Account {

	public CheckingAccount() {
		this.calculator = new CheckingInterest();
	}

	@Override
	public void updateInterestRate() {
		// TODO Auto-generated method stub
		this.interestRate = calculator.calculate(dailyAverageBalance);
	}

}
