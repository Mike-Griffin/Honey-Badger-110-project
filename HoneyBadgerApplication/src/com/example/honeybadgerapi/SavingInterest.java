package com.example.honeybadgerapi;

import android.os.Parcel;
import android.os.Parcelable;

public class SavingInterest implements InterestCalculator, Parcelable {

	SavingInterest(Parcel in){
		
	}
	
	public SavingInterest(){
		
	}
	
	@Override
	public double calculate(double dailyAverageBalance) {
		// TODO Auto-generated method stub
		if (dailyAverageBalance >= UPPER) {
			return (dailyAverageBalance * SAVING_UPPER_INTEREST);
		} else if (dailyAverageBalance < UPPER
				&& dailyAverageBalance >= MID) {
			return (dailyAverageBalance * SAVING_MID_INTEREST);
		} else if (dailyAverageBalance < MID
				&& dailyAverageBalance >= LOWER) {
			return (dailyAverageBalance * SAVING_LOWER_INTEREST);
		} else
			return 0.0;
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

	public static final Parcelable.Creator<SavingInterest> CREATOR = 
			new Parcelable.Creator<SavingInterest>(){

		@Override
		public SavingInterest createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new SavingInterest(source);
		}

		@Override
		public SavingInterest[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SavingInterest[size];
		}
		
	};
	
}
