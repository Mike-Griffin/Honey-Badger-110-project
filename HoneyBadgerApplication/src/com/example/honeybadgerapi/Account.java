package com.example.honeybadgerapi;

import android.os.Parcelable;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.parse.ParseObject;

public abstract class Account implements Parcelable {

	protected InterestCalculator calculator;
	protected double interestRate;
	protected double dailyAverageBalance;
	protected double balance;
	protected int accountNumber;
	protected int lastUpdated;
	protected boolean active;
	protected String accountType;

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setStatus(boolean status) {
		ParseObject account = query();
		if (account != null) {
			account.put("active", status);
			active = status;
			try {
				account.save();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean getStatus() {
		return active;
	}

	public void setBalance(double newBal) {
		ParseObject account = query();
		if (account != null) {
			balance = newBal;
			account.put("balance", newBal);
			try {
				account.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		if(balance < 100.00 && balance >= 25.00) {
			setBalance((balance -= 25.00));
		}
	}

	public boolean close() {
		ParseObject account = query();
		if (account.getInt("balance") != 0.00)
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

	public ParseObject query() {
		ParseObject account = null;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("accountNumber", accountNumber);
		try {
			account = query.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;
	}
}
