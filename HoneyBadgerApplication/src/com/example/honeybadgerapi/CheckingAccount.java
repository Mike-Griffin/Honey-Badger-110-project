package com.example.honeybadgerapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CheckingAccount extends Account {

	CheckingAccount(Parcel in){
		this.calculator = (InterestCalculator) in.readValue
				(CheckingInterest.class.getClassLoader());
		this.interestRate = in.readDouble();
		this.dailyAverageBalance = in.readDouble();
		this.balance = in.readDouble();
		this.accountNumber = in.readInt();
		this.lastUpdated = in.readInt();
		this.active = (Boolean) in.readValue(null);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("accountNumber", accountNumber);
		query.whereEqualTo("type", "Checking Account");
		try {
			account = query.getFirst();
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}
	
	public CheckingAccount(int accountNumber) {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("accountNumber", accountNumber);
		query.whereEqualTo("type", "Checking Account");
		try {
			account = query.getFirst();
		} catch(ParseException e) {
			e.printStackTrace();
		}

		this.accountNumber = accountNumber;
		balance = account.getInt("balance");
		active = account.getBoolean("active");
		calculator = new CheckingInterest();
		
	}

	@Override
	public void updateInterestRate() {
		// TODO Auto-generated method stub
		interestRate = calculator.calculate(dailyAverageBalance);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeValue(calculator);
		dest.writeDouble(interestRate);
		dest.writeDouble(dailyAverageBalance);
		dest.writeDouble(balance);
		dest.writeInt(accountNumber);
		dest.writeInt(lastUpdated);
		dest.writeValue(active);
	}

	
	static final Parcelable.Creator<CheckingAccount> CREATOR = 
			new Parcelable.Creator<CheckingAccount>(){

		@Override
		public CheckingAccount createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new CheckingAccount(source);
		}

		@Override
		public CheckingAccount[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CheckingAccount[size];
		}
		
	};
	
}
