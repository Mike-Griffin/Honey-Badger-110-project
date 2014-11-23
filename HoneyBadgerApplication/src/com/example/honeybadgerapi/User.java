package com.example.honeybadgerapi;

import java.util.ArrayList;

public interface User {

	public void signUp();
	
	public void setBalance(double d);
	
	public double getBalance();
	
	public int getNumOfAccounts();
	
	public void transfer(int accFrom, double amount, int accTo);
	
	public void transfer(int accFrom, double amount, String phone_email);
	
	public ArrayList<Account> getAccountList();
	
	public void closeAccount();
	
	public void updateUserInfo();
	
}
