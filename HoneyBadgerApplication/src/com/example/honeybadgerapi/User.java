package com.example.honeybadgerapi;

import java.util.ArrayList;

public abstract class User {

	public abstract int getLoginStatus();

	public abstract boolean getSignUpStatus();

	public abstract void setBalance(double d);

	public abstract double getBalance();

	public abstract int getNumOfAccounts();

	public abstract void transfer(int accFrom, double amount, int accTo);

	public abstract void transfer(int accFrom, double amount, String phone_email);

	public abstract  ArrayList<Account> getAccountList();

	public abstract void closeAccount();

	public abstract void updateUserInfo();

}
