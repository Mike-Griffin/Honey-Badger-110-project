package com.example.honeybadgerapi;

public class SavingsAccount extends Account {

	public SavingsAccount(){
		this.calculator = new SavingInterest();
	}
	
	@Override
	public void updateInterest() {
		// TODO Auto-generated method stub

	}

}
