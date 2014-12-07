package com.example.honeybadgerapi;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckingInterest implements InterestCalculator, Parcelable {

	CheckingInterest(Parcel in){
		
	}
	
	public CheckingInterest(){
		
	}
	
	@Override
	public double calculate(double dailyAverageBalance) {
		// TODO Auto-generated method stub
		if (dailyAverageBalance >= UPPER) {
			return (dailyAverageBalance * CHECKING_UPPER_INTEREST);
		} else if (dailyAverageBalance < UPPER
				&& dailyAverageBalance >= MID) {
			return (dailyAverageBalance * CHECKING_MID_INTEREST);
		} else if (dailyAverageBalance < MID
				&& dailyAverageBalance >= LOWER) {
			return (dailyAverageBalance * CHECKING_LOWER_INTEREST);
		} else
			return dailyAverageBalance;
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

	public static final Parcelable.Creator<CheckingInterest> CREATOR = 
			new Parcelable.Creator<CheckingInterest>(){

		@Override
		public CheckingInterest createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new CheckingInterest(source);
		}

		@Override
		public CheckingInterest[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CheckingInterest[size];
		}
		
	};
	
}
