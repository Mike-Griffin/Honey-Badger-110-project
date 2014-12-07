package com.example.honeybadgerapi;

public class UserFactory {
	public User makeUser(int userType, String username, String password){
		User user = null;
		if(userType == 1)
			user = new Customer();
		else if(userType == 2)
			user = new Teller();
		else
			return null;
		
		user.login(username, password);
		return user;
	}
}
