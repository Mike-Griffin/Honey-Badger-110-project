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
import java.util.List;

public class Customer extends User {
	private int accountCombo;
	private Account[] accounts = new Account[2];
	private String customerID;
	private int checkingNumber;
	private int savingNumber;

	public Customer() {
	}
	
	Customer(Parcel in) {
		super(in);
		this.accountCombo = in.readInt();
		// Log.d("combo", Integer.toString(accountCombo));
		// this.accounts = (Account[])
		// in.readParcelableArray(Account.class.getClassLoader());
		this.accounts[0] = (Account) in.readParcelable(Account.class
				.getClassLoader());
		// if (accounts[0] == null)
		//	Log.d("ajosdiajsd", "it is null");
		this.accounts[1] = (Account) in.readParcelable(Account.class
				.getClassLoader());
		this.customerID = in.readString();
		// Log.d("customerID", customerID);
		this.checkingNumber = in.readInt();
		// Log.d("checking", Integer.toString(checkingNumber));
		this.savingNumber = in.readInt();
		// Log.d("saving", Integer.toString(savingNumber));
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

		if (customer != null) {
			loginStatus = 1;
			customerID = customer.getObjectId();
			accountCombo = customer.getInt("accountCombo");
			checkingNumber = customer.getInt("checkingAccount");
			savingNumber = customer.getInt("savingAccount");
			updateAccountList();
		}
	}

	// login
	@Override
	public void login(String username, String password) {
		ParseUser customer = null;
		// Log.d("asdasd", password);
		try {
			customer = ParseUser.logIn(username, password);
		} catch (ParseException e) {
			Log.d("Login", "fail");
		}

		if (customer != null) {
			loginStatus = 1;
		}

		if (loginStatus == 1) {
			customerID = customer.getObjectId();
			accountCombo = customer.getInt("accountCombo");
			checkingNumber = customer.getInt("checkingAccount");
			savingNumber = customer.getInt("savingAccount");
			userType = 1;
			updateAccountList();
		}
	}

	// sign up
	public void signup(String name, String username, String password,
			String email, String birthday, String address, String city,
			String state, String phoneNumber, int zip, int accountNumber) {
		signUpStatus = true;

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
		// customer.put("primaryAccount", 0);
		customer.put("phone", phoneNumber);
		customer.put("userType", 1);

		try {
			customer.signUp();
		} catch (ParseException e) {
			signUpStatus = false;
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
		ParseUser customer = null;
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("objectId", customerID);
		try {
			customer = query.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		accountCombo = code;
		customer.put("accountCombo", accountCombo);
		try {
			customer.save();
		} catch(ParseException e) {
			e.printStackTrace();
		}
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
	public boolean transfer(int accFrom, double amount, int accTo) {
		// TODO Auto-generated method stub
		ParseObject accountTo = null;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		if(accTo == 1) {
		query.whereEqualTo("accountNumber", checkingNumber);
		} else {
			query.whereEqualTo("accountNumber", savingNumber);
		}
		try {
			accountTo = query.getFirst();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (accountTo == null)
			return false;

		if (accountTo.getBoolean("active") == false)
			return false;

		if (!debit(accFrom, amount))
			return false;

		accountTo.put("balance", accountTo.getInt("balance") + amount);
		try {
			accountTo.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public Customer getCustomer(){
		return this;
	}
	@Override
	public boolean transfer(int accFrom, double amount, String phone_email) {
		// TODO Auto-generated method stub
		ParseUser accountUser = null;
		ParseObject accountTo = null;
		int accountCombo = 0;
		
		ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
		ParseQuery<ParseUser> emailQuery = ParseUser.getQuery();
		ParseQuery<ParseUser> phoneQuery = ParseUser.getQuery();
		emailQuery.whereEqualTo("email", phone_email);
		phoneQuery.whereEqualTo("phone", phone_email);
		List<ParseQuery<ParseUser>> queries = new ArrayList<ParseQuery<ParseUser>>();
		queries.add(emailQuery);
		queries.add(phoneQuery);
		userQuery = ParseQuery.or(queries);
		try {
			accountUser = userQuery.getFirst();
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		accountCombo = accountUser.getInt("accountCombo");
		switch(accountCombo) {
			case 0:
				return false;
			case 2:
				ParseQuery<ParseObject> saving = ParseQuery.getQuery("Account");
				saving.whereEqualTo("accountNumber", accountUser.get("savingAccount"));
				try {
					accountTo = saving.getFirst();
				} catch(ParseException e) {
					e.printStackTrace();
				}
				break;
			case 3:
			case 1:
				ParseQuery<ParseObject> checking = ParseQuery.getQuery("Account");
				checking.whereEqualTo("accountNumber", accountUser.get("checkingAccount"));
				try {
					accountTo = checking.getFirst();
				} catch(ParseException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
		
		if(!debit(accFrom, amount))
			return false;
		
		accountTo.put("balance", accountTo.getInt("balance") + amount);
		try{
			accountTo.save();
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		return true;
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
			if(accounts[0].close()) {
				setAccountCombo(--accountCombo);
				return true;
			}
			return false;
		case 2:
			if(accounts[1].close()) {
				setAccountCombo((accountCombo -= 2));
				return true;
			}
			return false;
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
			if (accounts[0].getActive() == false)
				return false;
			accounts[0].setBalance(accounts[0].getBalance() + d);
			return true;
		case 2:
			if (accounts[1].getActive() == false)
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
			if (accounts[0].getActive() == false)
				return false;
			if (accounts[0].getBalance() < d)
				return false;
			accounts[0].setBalance(accounts[0].getBalance() - d);
			return true;
		case 2:
			if (accounts[1].getActive() == false)
				return false;
			if (accounts[1].getBalance() < d)
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
	public void setCustomer(String customer) {

	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
		dest.writeInt(accountCombo);
		dest.writeParcelable(accounts[0], flags);
		dest.writeParcelable(accounts[1], flags);
		// dest.writeParcelableArray(accounts, flags);
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
