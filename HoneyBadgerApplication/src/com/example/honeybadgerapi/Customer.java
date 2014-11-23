package com.example.honeybadgerapi;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

public class Customer implements User {
	private int loginStatus = 0; // 0 fails, 1 success
	private int numAccounts;

	private boolean signUpStatus = true;
	
	private ArrayList<Account> accountList;
	private ParseUser customer;

	// login
	public Customer(String username, String password) {
		try {
			customer = ParseUser.logIn(username, password);
		} catch (ParseException e) {
		}

		if (customer != null) {
			loginStatus = 1;
		}
	}

	// sign up
	public Customer(String name, String username, String password,
			String email, String birthday, String address, String city,
			String state, String phoneNumber, int zip) {
		customer = new ParseUser();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.put("name", name);
		customer.put("birthday", birthday);
		customer.put("address", address);
		customer.put("city", city);
		customer.put("state", state);
		customer.put("zipCode", zip);
		customer.put("primaryAccount", 0);
		customer.put("phone", phoneNumber);

		try {
			customer.signUp();
		} catch (ParseException e) {
			signUpStatus = false;
		}
	}

	@Override
	public int getLoginStatus() {
		// TODO Auto-generated method stub
		return loginStatus;
	}

	@Override
	public boolean getSignUpStatus() {
		// TODO Auto-generated method stub
		return signUpStatus;
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
