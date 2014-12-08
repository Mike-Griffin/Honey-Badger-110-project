package com.example.honeybadgerapi;

import android.os.Parcel;
import android.os.Parcelable;

public class TellerHelper implements Parcelable{

	private String customerName;
	private String customerPass;
	private boolean fromTeller;
	
	protected TellerHelper(Parcel in){
		this.customerName = in.readString();
		this.customerPass = in.readString();
		this.fromTeller = (Boolean) in.readValue(null);
	}
	
	public TellerHelper(String name, String pass, boolean teller){
		customerName = name;
		customerPass = pass;
		teller = true;
	}
	
	public String getCustName(){
		return customerName;
	}
	
	public void setCustName(String newName){
		customerName = newName;
	}
	
	public String getCustPass(){
		return customerPass;
	}
	
	public void setCustPass(String newPass){
		customerPass = newPass;
	}
	
	public boolean getFromTeller(){
		return fromTeller;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(customerName);
		dest.writeString(customerPass);
		dest.writeValue(fromTeller);
		
	}
	
	public static final Parcelable.Creator<TellerHelper> CREATOR = new Parcelable.Creator<TellerHelper>(){

		@Override
		public TellerHelper createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new TellerHelper(source);
		}

		@Override
		public TellerHelper[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TellerHelper[size];
		}
		
	};
	
}
