package com.example.honeybadgerapi;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

public class Customer extends User {

	private int accountCombo;

	private Account[] accounts = new Account[2];  // [0] is checking, [1] is saving
	private String customerID;
	private int checkingNumber;
	private int savingNumber;

	public Customer(){
		
	}
	
	Customer(Parcel in){
		super(in);
		this.accountCombo = in.readInt();
		Log.d("combo", Integer.toString(accountCombo));
		// this.accounts = (Account[]) in.readParcelableArray(Account.class.getClassLoader());
		this.accounts[0] = (Account) in.readParcelable(Account.class.getClassLoader());
		if(accounts[0] == null)
			Log.d("ajosdiajsd", "it is null");
		this.accounts[1] = (Account) in.readParcelable(Account.class.getClassLoader());
		this.customerID = in.readString();
		Log.d("customerID", customerID);
		this.checkingNumber = in.readInt();
		Log.d("checking", Integer.toString(checkingNumber));
		this.savingNumber = in.readInt();
		Log.d("saving", Integer.toString(savingNumber));
	}
	
	// login
	public Customer(String username, String password) {
		ParseUser customer = null;
		// Log.d("asdasd", password);
		try {
			customer = ParseUser.logIn(username, password);
		} catch (ParseException e) {
		}

		if (customer != null) {
			loginStatus = 1;
		}

		if (loginStatus == 1) {
			customerID = customer.getObjectId();
			accountCombo = customer.getInt("accountCombo");
			checkingNumber = customer.getInt("checkingAccount");
			savingNumber = customer.getInt("savingAccount");
			updateAccountList();
		}
	}

	// sign up
	public Customer(String name, String username, String password,
			String email, String birthday, String address, String city,
			String state, String phoneNumber, int zip) {
		ParseUser customer = new ParseUser();
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

	// teller look up
	public Customer(String username) {
		ParseUser customer = null;
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username", username);
		try {
			customer = query.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setBalance(int code, double d) {
		// TODO Auto-generated method stub
		switch (code) {
		case 1:
			accounts[0].setBalance(d);
			break;
		case 2:
			accounts[1].setBalance(d);
			break;
		default:
			break;
		}
	}

	@Override
	public double getBalance(int code) {
		// TODO Auto-generated method stub
		switch (code) {
		case 1:
			return accounts[0].getBalance();
		case 2:
			return accounts[1].getBalance();
		default:
			return 0.00;
		}
	}

	@Override
	public void setAccountCombo(int code) {
		accountCombo = code;
	}

	public void setNumOfAccounts(int code) {
		accountCombo = code;
	}

	@Override
	public int getAccountCombo() {
		// TODO Auto-generated method stub
		return accountCombo;
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
		accounts[0] = null;
		accounts[1] = null;

		// just checking account
		if (accountCombo == 1) {
			accounts[0] = new CheckingAccount(checkingNumber);
		}
		// just saving account
		else if (accountCombo == 2) {
			accounts[1] = new SavingsAccount(savingNumber);
		}
		// both checking and saving
		else if (accountCombo == 3) {
			accounts[0] = new CheckingAccount(checkingNumber);
			accounts[1] = new SavingsAccount(savingNumber);
		}
	}

	@Override
	public Account[] getAccountList() {
		// TODO Auto-generated method stub
		return accounts;
	}

	@Override
	public boolean closeAccount(int code) {
		// TODO Auto-generated method stub
		switch (code) {
		case 1:
			return accounts[0].close();
		case 2:
			return accounts[1].close();
		default:
			return false;
		}
	}

	@Override
	public void updateUserInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean credit(int code, double d) {
		// TODO Auto-generated method stub
		switch (code) {
		case 1:
			if(accounts[0].getActive() == false)
				return false;
			accounts[0].setBalance(accounts[0].getBalance() + d);
			return true;
		case 2:
			if(accounts[1].getActive() == false)
				return false;
			accounts[1].setBalance(accounts[1].getBalance() + d);
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean debit(int code, double d) {
		// TODO Auto-generated method stub
		switch (code) {
		case 1:
			if(accounts[0].getActive() == false)
				return false;
			if(accounts[0].getBalance() < d)
				return false;
			accounts[0].setBalance(accounts[0].getBalance() - d);
			return true;
		case 2:
			if(accounts[1].getActive() == false)
				return false;
			if(accounts[1].getBalance() < d)
				return false;
			accounts[1].setBalance(accounts[1].getBalance() - d);
			return true;
		default:
			return false;
		}
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
		dest.writeInt(accountCombo);
		dest.writeParcelable(accounts[0], flags);
		dest.writeParcelable(accounts[1], flags);
		//dest.writeParcelableArray(accounts, flags);
		dest.writeString(customerID);
		dest.writeInt(checkingNumber);
		dest.writeInt(savingNumber);
	}

	public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {

		@Override
		public Customer createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Customer(source);
		}

		@Override
		public Customer[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Customer[size];
		}
		
	};
}
