package com.example.honeybadgerapi;

import java.util.ArrayList;

public class Customer implements User {

	private ArrayList<Account> accountList;
	private int numAccounts;
	
	
	@Override
	public void signUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBalance(double d) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumOfAccounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void transfer(int accFrom, double amount, int accTo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transfer(int accFrom, double amount, String phone_email) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Account> getAccountList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeAccount() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserInfo() {
		// TODO Auto-generated method stub

	}

	
}
