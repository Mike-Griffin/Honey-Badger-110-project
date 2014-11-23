package com.example.honeybadgerapi;

public abstract class Account {

	protected InterestCalculator calculator;	
	protected double interestRate;
	protected double dailyAverageBalance;
	private double balance;
	private int accountNum;
	private int lastUpdated;

	
	public double getBalance(){return balance;}
	
	public void setBalance(double newBal) {balance = newBal;}
	
	public void setLastUpdated(int i){lastUpdated = i;}
	
	public int getLastUpdated(){return lastUpdated;}
	
	public abstract void updateInterestRate();
	
	// Condition is incorrect, need date for Last Updated
	public void applyInterestRate(){
		if(lastUpdated > 30)
			balance += interestRate;
	}
	
	// Condition is incorrect, need date for Last Updated
	public void updatePenalty(){
		if(lastUpdated > 30)
			balance -= 25;
	}
	

	
}
