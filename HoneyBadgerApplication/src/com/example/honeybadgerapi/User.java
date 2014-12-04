package com.example.honeybadgerapi;


import java.util.ArrayList;

import android.os.Parcelable;

public abstract class User implements Parcelable{
	
	protected int loginStatus = 0; // 0 fails, 1 success
	protected boolean signUpStatus = true;
	
	public int getLoginStatus() {
		return loginStatus;
	}

	public boolean getSignUpStatus() {
		return signUpStatus;
	}

	public abstract void setBalance(int code, double amount);

	public abstract double getBalance(int code);

	public abstract void setAccountCombo(int code);
	
	public abstract int getAccountCombo();

	public abstract void transfer(int accFrom, double amount, int accTo);

	public abstract void transfer(int accFrom, double amount, String phone_email);

	public abstract void updateAccountList();
	
	public abstract Account[] getAccountList();

	public abstract boolean closeAccount(int code);

	public abstract void updateUserInfo();
	
	public abstract boolean credit(int code, double d);

	public abstract boolean debit(int code, double d);

}
