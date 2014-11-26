package com.example.honeybadgerapi;

import com.parse.ParseException;

import com.parse.ParseObject;

public abstract class Account {

	protected InterestCalculator calculator;
	protected double interestRate;
	protected double dailyAverageBalance;
	protected double balance;
	protected int accountNumber;
	protected int lastUpdated;
	protected boolean active;
	
	protected ParseObject account;

	public double getBalance() {
		return balance;
	}
	
	public void setStatus(boolean status) {
		if(account != null) {
			account.put("active", status);
			try {
				account.save();
			} catch(ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean getStatus() {
		if(account != null)
			return account.getBoolean("active");
		else
			return false;
	}

	public void setBalance(double newBal) {
		balance = newBal;
		account.put("balance", newBal);
		try {
			account.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLastUpdated(int i) {
		lastUpdated = i;
	}

	public int getLastUpdated() {
		return lastUpdated;
	}

	public abstract void updateInterestRate();

	// Condition is incorrect, need date for Last Updated
	public void applyInterestRate() {
		if (lastUpdated > 30)
			balance += interestRate;
	}

	// Condition is incorrect, need date for Last Updated
	public void updatePenalty() {
		if (lastUpdated > 30)
			balance -= 25;
	}
	
	public boolean close() {
		if(account.getInt("balance") != 0.00)
			return false;
		account.put("active", false);
		active = false;
		try {
			account.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean getActive() {
		return active;
	}
}
