package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.honeybadgerapi.User;
import com.example.honeybadgerapi.Account;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CloseAccount extends ActionBarActivity {
	//creates spinner to select account
	private Spinner accountSpinner;
	// Cancel button
	private Button  cancelButton;
	// Close account
	private Button closeAccountButton;
	//Strings of existing User accounts
	private List<String> accStrings = new ArrayList<String>();
	//String Array Adapter of User accounts
	private ArrayAdapter<String> accStringsAdapt;
	//The User selected account by Spinner
	private String accountSelected;
	//Array containing User's accounts
	Account[] userAccounts;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//gets customer object
		Bundle userBundle = this.getIntent().getExtras();
		//final User customer = null;
		if(userBundle == null) {
			//error, Bundle should not be null
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		}
		else {
		  final User customer = userBundle.getParcelable("user");
		  
		  //sets view
		  super.onCreate(savedInstanceState);
	   	  setContentView(R.layout.activity_close_account);
		
		  final Intent intentUserHome = new Intent(CloseAccount.this, UserHomePage.class);
		
		  //Spinner and Button creation
	      accountSpinner = (Spinner)findViewById(R.id.accountNumber);
		  closeAccountButton = (Button) findViewById(R.id.closeAccount);
		  cancelButton = (Button) findViewById(R.id.cancelCloseAccount);
		
		  //collects the User's accounts
		  userAccounts = customer.getAccountList();
		  
		  //Finds which user accounts exist (checking or saving)
		  int aCombo = customer.getAccountCombo();
		  switch(aCombo) {
		    case 1: //just checking account
		    	accStrings.add("Checking Account");
			    break;
		    case 2: //just saving account
		    	accStrings.add("Savings Account");
			    break;
		    case 3: //both accounts
		    	accStrings.add("Checking Account");
		    	accStrings.add("Savings Account");
			    break;
		  }
		  
		  //Creates Spinner
		  accStringsAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, accStrings);
		  accStringsAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  accountSpinner.setAdapter(accStringsAdapt);
		  
		  closeAccountButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				accountSelected = accountSpinner.getSelectedItem().toString().trim();
				//for Checking account
				if(accountSelected.equals("Checking Account")) {
				    if(customer.closeAccount(1)) {
				        //update the Account Combo
				    /*	if(customer.getAccountCombo() == 3) {
				    		customer.setAccountCombo(2);
				    	}
				    	else if(customer.getAccountCombo() == 1){
				    		customer.setAccountCombo(0);
				    	}
				    	*/
				        //The Account has been successfully deleted
						Toast.makeText(
								getApplicationContext(),
								"Account has been successfully closed",
								Toast.LENGTH_SHORT)
								.show();
				    }
				    else {
				    	//display error message, account balance needs to be 0
						Toast.makeText(
								getApplicationContext(),
								"Account balance needs to be 0 in order to close account",
								Toast.LENGTH_SHORT)
								.show();
				    }
				//for Savings account
				} else if(accountSelected.equals("Savings Account")) {
				    if(customer.closeAccount(2)) {
				      /*  //update the Account Combo
				    	if(customer.getAccountCombo() == 3) {
				    		customer.setAccountCombo(1);
				    	}
				    	else if(customer.getAccountCombo() == 2){
				    		customer.setAccountCombo(0);
				    	}
				    */	
				        //The Account has been successfully deleted
						Toast.makeText(
								getApplicationContext(),
								"Account has been successfully closed", 
								Toast.LENGTH_SHORT)
								.show();
				    }
				    else {
				    	//display error message, account balance needs to be 0
						Toast.makeText(
								getApplicationContext(),
								"Account balance needs to be 0 in order to close account", 
								Toast.LENGTH_SHORT)
								.show();
				    }
				}//end of checking and savings account closure
				
				
				//closes Bundle
				Bundle userBundle = new Bundle();
				userBundle.putParcelable("user", customer);
				intentUserHome.putExtra("user", customer);
				
				//Takes user back to Homepage
				startActivity(intentUserHome);
				
			}//end of button event
		  });
		
		  cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Takes user back to Homepage
				//closes Bundle
				Bundle userBundle = new Bundle();
				userBundle.putParcelable("user", customer);
				intentUserHome.putExtra("user", customer);
				
				startActivity(intentUserHome);
			}
		  });
		} // end of null Bundle check
	}

	@Override
	public void onBackPressed() {
	    // do nothing.
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.close_account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}