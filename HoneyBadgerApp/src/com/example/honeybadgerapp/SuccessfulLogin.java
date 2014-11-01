package com.example.honeybadgerapp;

import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SuccessfulLogin extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_successful_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.successful_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_account:
	            // Do something
	            return true;
	        case R.id.log_out:
	        	ParseUser.logOut();
	        	if(ParseUser.getCurrentUser() == null) {
		        	Toast.makeText(getApplicationContext(), "successfully logout!", Toast.LENGTH_SHORT).show();
		        	final Intent intentLogin = new Intent(SuccessfulLogin.this, MainActivity.class);
					startActivity(intentLogin);
	        	}
	        	
	        	// Logout
	        	// setContentView(R.layout.activity_main);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
