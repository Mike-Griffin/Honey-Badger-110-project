package com.example.honeybadgerapi;

public class UserFactory {
	public User makeUser(int userType, String username, String password){
		if(userType == 1)
			return new Customer();
		else if(userType == 2)
			return new Teller();
		else
			return null;
	}
}
