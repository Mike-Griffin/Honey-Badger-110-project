package com.example.honeybadgerapi;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Teller extends User {

	private Customer activeCustomer;
	private String tellerID;

	public Teller(){	
	}
	
	Teller(Parcel in) {
		super(in);
		this.activeCustomer = (Customer) in.readParcelable(Customer.class.getClassLoader());
		this.tellerID = in.readString();
	}
	
	// login
	@Override
	public void login(String username, String password) {
		ParseUser teller = null;
		try {
			teller = ParseUser.logIn(username, password);
		} catch (ParseException e) {
		}

		if (teller != null) {
			loginStatus = 1;
			tellerID = teller.getObjectId();
		}
	}
	
	public void signup(String username, String password, String email) {
		signUpStatus = true;

		ParseUser teller = new ParseUser();
		teller.setUsername(username);
		teller.setPassword(password);
		teller.setEmail(email);
		teller.put("userType", 2);

		try {
			teller.signUp();
		} catch (ParseException e) {
			signUpStatus = false;
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
	public boolean transfer(int accFrom, double amount, int accTo) {
		// TODO Auto-generated method stub
		return activeCustomer.transfer(accFrom, amount, accTo);
	}

	@Override
	public boolean transfer(int accFrom, double amount, String phone_email) {
		// TODO Auto-generated method stub
		return activeCustomer.transfer(accFrom, amount, phone_email);
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
	
	public int activeCustomer() {
		return activeCustomer.getLoginStatus();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
		dest.writeParcelable(activeCustomer, flags);
		dest.writeString(tellerID);
	}

	public static final Parcelable.Creator<Teller> CREATOR = new Parcelable.Creator<Teller>() {

		@Override
		public Teller createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Teller(source);
		}

		@Override
		public Teller[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Teller[size];
		}
	};
}
