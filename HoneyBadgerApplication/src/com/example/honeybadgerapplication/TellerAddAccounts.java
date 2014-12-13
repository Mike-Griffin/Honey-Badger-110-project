package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.User;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TellerAddAccounts extends ActionBarActivity {
	
	//declare local variables
	private EditText amount_edit_text;
	private double amount = 0.0;

	//customer user bundle
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_add_accounts);
		
		//parse communication
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
		
		//buttons for teller add account
		final Intent intentTellerHomePage = new Intent (TellerAddAccounts.this, TellerCustomerInfo.class);
		final Button cancelButton = (Button) findViewById(R.id.cancel);
		final Button addCheckingButton = (Button) findViewById(R.id.addChecking);
		final Button addSavingButton = (Button) findViewById(R.id.savingAccount);
		amount_edit_text = (EditText) findViewById(R.id.amount_cred);
	
		////check to see if bundle exist
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		} else {
			final User user = userBundle.getParcelable("user");
			
			Log.d("teller", user.getUser());
			//checks the user type 
			if(user.getUserType() >= 2){
				ParseUser.logOut();
				try {
					ParseUser.logIn(user.getCustomer().getUser(), user.getCustomer().getPass());

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		    
			//checking button listener 
			addCheckingButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
					//gets the text from the field and stores it into local variable amount
					amount = Double.parseDouble(amount_edit_text.getText()
							.toString().trim());
					} catch (NumberFormatException e) {
						//verifies user input
						Toast.makeText(getApplicationContext(), "Please enter a valid amount!", 
								Toast.LENGTH_SHORT).show();
						return;
					}
					//if open account then check to see if account has been made created 
					if(user.openAccount("Checking Account", amount)){
						Toast.makeText(getApplicationContext(), "Account successfully opened",
								Toast.LENGTH_SHORT).show();		  
					}
					else{
						//if account made was unsucesssful then display openeing failed
						Toast.makeText(getApplicationContext(), "Account opening failed", 
								Toast.LENGTH_SHORT).show();
					}
					
					ParseUser.logOut();
					  try {
						ParseUser.logIn(user.getUser(), user.getPass());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Bundle userBundle = new Bundle();
					userBundle.putParcelable("user", user);
					intentTellerHomePage.putExtra("user", user);
					startActivity(intentTellerHomePage);	
				}
			});
			//add saving button to create new saving account 
			addSavingButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
					//gets the value from text field and stores into amount variable 
					amount = Double.parseDouble(amount_edit_text.getText()
							.toString().trim());
					} catch (NumberFormatException e) {
						Toast.makeText(getApplicationContext(), "Please enter a valid amount!", 
								Toast.LENGTH_SHORT).show();
						return;
					}
					//checks to see if the accoutn was sucessfully created
					if(user.openAccount("Saving Account", amount)){
						Toast.makeText(getApplicationContext(), "Account successfully opened",
								Toast.LENGTH_SHORT).show();
						if(user.getAccountCombo() == 2){
							Toast.makeText(getApplicationContext(), "Account combo is 1", 
									Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getApplicationContext(), "Account combo is 0",
									Toast.LENGTH_SHORT).show();
						}
					}
					else{
						Toast.makeText(getApplicationContext(), "Account opening failed", 
								Toast.LENGTH_SHORT).show();
					}
					//logout 
					ParseUser.logOut();
					  try {
						  if(user == null) {
Log.d("null", "usernull");
}
						ParseUser.logIn(user.getUser(), user.getPass());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Bundle userBundle = new Bundle();
					userBundle.putParcelable("user", user);
					intentTellerHomePage.putExtra("user", user);
					startActivity(intentTellerHomePage);	
				}
			});
			
			
			//click listener for cancelButton
			cancelButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle userBundle = new Bundle();
					userBundle.putParcelable("user", user);
					intentTellerHomePage.putExtra("user", user);
					startActivity(intentTellerHomePage);
					
				}
			});
		}
	}

	@Override
	// Inflate the menu; this adds items to the action bar if it is present.
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.teller_add_accounts, menu);
		return true;
	}

	@Override
	// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}
