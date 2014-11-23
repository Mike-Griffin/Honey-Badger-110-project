package com.example.honeybadgerapi;

public abstract class Account {

	protected InterestCalculator calculator;	
	private double interestRate;
	private double balance;
	private double penalty;
	private int accountNum;

	
	public double getBalance(){return 0.0;}
	
	public void setBalance() {return;}
	
	public abstract void updateInterest();
	
	public void updatePenalty(){penalty = 0.0;}
	
}
