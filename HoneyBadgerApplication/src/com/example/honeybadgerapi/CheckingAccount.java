package com.example.honeybadgerapi;

public class CheckingAccount extends Account {

	public CheckingAccount(){
		this.calculator = new CheckingInterest();
	}
	
	@Override
	public void updateInterest() {
		// TODO Auto-generated method stub

	}

}
