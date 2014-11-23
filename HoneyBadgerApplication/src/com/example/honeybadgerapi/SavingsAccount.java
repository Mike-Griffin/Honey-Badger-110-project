package com.example.honeybadgerapi;

public class SavingsAccount extends Account {

	public SavingsAccount(){
		this.calculator = new SavingInterest();
	}
	
	@Override
	public void updateInterestRate() {
		// TODO Auto-generated method stub
		this.interestRate = calculator.calculate(dailyAverageBalance);
	}

}
