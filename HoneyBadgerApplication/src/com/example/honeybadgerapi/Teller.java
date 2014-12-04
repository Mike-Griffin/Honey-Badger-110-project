package com.example.honeybadgerapi;

import java.util.ArrayList;

import android.os.Parcel;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Teller extends User {

	private Customer activeCustomer;
	private ParseObject teller;

	public Teller(){
		
	}
	
	public Teller(String username, String password) {
		try {
			teller = ParseUser.logIn(username, password);
		} catch (ParseException e) {
		}

		if (teller != null) {
			loginStatus = 1;
		}
	}

	@Override
	public void setBalance(int code, double d) {
		// TODO Auto-generated method stub
		activeCustomer.setBalance(code, d);
	}

	@Override
	public double getBalance(int code) {
		// TODO Auto-generated method stub
		return activeCustomer.getBalance(code);
	}

	@Override
	public void setAccountCombo(int code) {
		activeCustomer.setNumOfAccounts(code);
	}
	
	@Override
	public int getAccountCombo() {
		// TODO Auto-generated method stub
		return activeCustomer.getAccountCombo();
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
	public void updateAccountList() {
		activeCustomer.updateAccountList();
	}

	@Override
	public Account[] getAccountList() {
		// TODO Auto-generated method stub
		return activeCustomer.getAccountList();
	}

	@Override
	public boolean closeAccount(int code) {
		// TODO Auto-generated method stub
		return activeCustomer.closeAccount(code);
	}

	@Override
	public void updateUserInfo() {
		// TODO Auto-generated method stub

	}

	public void updateInterest() {

	}

	public void updatePenalty() {

	}

	@Override
	public boolean credit(int code, double d) {
		return activeCustomer.credit(code, d);
	}

	@Override
	public boolean debit(int code, double d) {
		return activeCustomer.debit(code, d);
	}

	public void setCustomer(String username) {
		activeCustomer = new Customer(username);
	}

	public Customer getCustomer() {
		return activeCustomer;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
