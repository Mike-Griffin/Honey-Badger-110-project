package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.honeybadgerapi.Account;
import com.example.honeybadgerapi.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class AccountTransfer extends ActionBarActivity {
	private ArrayAdapter<String> accountNumberFromAdapt;
	private ArrayAdapter<String> accountNumberToAdapt;
	private List<String> accStrings = new ArrayList<String>();
	private List<String> accToStrings = new ArrayList<String>();
	private Account[] userAccounts;
	private Button confirmButton;
	private Button cancelButton;
	private EditText amount_edit_text;
	private EditText email_edit_text;
	private Spinner accountFromSpinner;
	private Spinner accountToSpinner;
	private boolean otherAccount;
	private String accountFromSelected;
	private String accountToSelected;
	private String emailOrPhoneString = null;
	private ParseObject accountFromObject;
	private ParseUser accountToUser;
	private ParseObject accountToObject;
	private int accFromInt;
	private int accToInt;
	private double amount = 0;
	private int userType = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_transfer);
		
		Bundle userBundle = this.getIntent().getExtras();
		Log.d("does this even happen"," ");
		if(userBundle == null) {
			Log.d("user bunndle is null", " ");
		}
		else {
		final User customer = userBundle.getParcelable("user");

		
		final Intent intentUserHome = new Intent(AccountTransfer.this, UserHomePage.class);
		final Intent intentCustomerInfo = new Intent(AccountTransfer.this, TellerCustomerInfo.class);
		


		confirmButton = (Button) findViewById(R.id.confirmTransfer);
		cancelButton = (Button) findViewById(R.id.cancelTransfer);
		amount_edit_text = (EditText) findViewById(R.id.amountToTransfer);
		email_edit_text = (EditText) findViewById(R.id.accountEmailToTransfer);
		accountFromSpinner = (Spinner) findViewById(R.id.transferFromSpinner);
		accountToSpinner = (Spinner) findViewById(R.id.transferToSpinner);
		
		
		//accountToSpinner = (Spinner) findViewById(R.id.transferToSpinner);
		
		userAccounts = customer.getAccountList();
		userType = customer.getUserType();
		
		int aCombo = customer.getAccountCombo();

		switch(aCombo) {
			case 1: //just checking account
				accStrings.add("Checking Account");
				accToStrings.add("Checking Account");
				break;
			case 2: //just saving account
				accStrings.add("Savings Account");
				accToStrings.add("Savings Account");
			    break;
			case 3: //both accounts
			    accStrings.add("Checking Account");
			    accStrings.add("Savings Account");
			    accToStrings.add("Checking Account");
			    accToStrings.add("Savings Account");
				break;
		    default:
		    	accStrings.add("Error: No Accounts");
		    	accToStrings.add("Error: No Accounts");
		    	break;
		}
		
		accToStrings.add("To Someone Else");
		
		accountNumberFromAdapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, accStrings);
		accountNumberFromAdapt
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountFromSpinner.setAdapter(accountNumberFromAdapt);
		
		
		/*
		final List<String> accountToList = new ArrayList<String>(accStrings);
		accountToList.add("To Someone Else");
		*/
		

		
		accountNumberToAdapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, accToStrings);
		accountNumberToAdapt
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountToSpinner.setAdapter(accountNumberToAdapt);
		//accStrings.remove("To Someone Else");
		
		accountToSpinner
		.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				Log.d("this happens", " ");
				 //Getting Value of Selected Item
				String val = accountToSpinner.getSelectedItem()
						.toString();
				if (val.equals(accStrings.get(accStrings.size() - 1))) {
					otherAccount = true;
					Log.d("fired", val);
					Log.d("fired", accStrings.get(accStrings.size() - 1));
					Log.d("fired", "1");
				}
				else {
					otherAccount = false;
					Log.d("fired", val);
					Log.d("fired", accStrings.get(accStrings.size() - 1));
					Log.d("f ired", "2");
				}

				//email_edit_text.setEnabled(otherAccount);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
			
		});


		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String amountString = amount_edit_text.getText()
						.toString().trim();
				if(!amountString.equals("")) {
					amount = Double.parseDouble(amountString);
				}
				else {
					Toast.makeText(getApplicationContext(), "Error: No amount entered", 
							Toast.LENGTH_SHORT).show();
					return;
				}
				accountFromSelected = accountFromSpinner.getSelectedItem()
						.toString().trim();
				
				if(accountFromSelected.equals("Checking Account")) {
					accFromInt = 1;
					Log.d("FROM INT: ", "" + accFromInt);
				}
				
				else if(accountFromSelected.equals("Savings Account")) {
					accFromInt = 2;
					Log.d("FROM INT: ", "" + accFromInt);
				}
				
				else {
					Toast.makeText(
							getApplicationContext(),
							"Error: No from account selected", 
							Toast.LENGTH_SHORT)
							.show();
				}

				emailOrPhoneString = email_edit_text.getText().toString().trim();

				accountToSelected = accountToSpinner.getSelectedItem()
						.toString().trim();

				if(accountToSelected.equals("Checking Account")) {
					accToInt = 1;

				}
				
				else if(accountToSelected.equals("Savings Account")) {
					accToInt = 2;

				}
				
				else {
					if(!emailOrPhoneString.equals("")) {
						if(amount > 0) {
							if(customer.transfer(accFromInt,amount,emailOrPhoneString)) {
								Toast.makeText(
										getApplicationContext(),
										"Successful Transfer to other user account", 
								 		Toast.LENGTH_SHORT)
										.show();
							}
							else {
								Toast.makeText(
										getApplicationContext(),
										"Error: Transfer to other account failed", 
										Toast.LENGTH_SHORT)
										.show();
							}
						}
						else {
							Toast.makeText(getApplicationContext(), "Error: Invalid amount", 
									Toast.LENGTH_SHORT).show();

						}
					}
					else {
						Toast.makeText(getApplicationContext(), "Error: No email or phone for other account", 
								Toast.LENGTH_SHORT).show();
					}
				}
				
				if(emailOrPhoneString.equals("")&&amount > 0) {
					if(accToInt == 1 || accToInt == 2) {
						if(accToInt != accFromInt) {
							if(customer.transfer(accFromInt, amount, accToInt)) {
								Toast.makeText(
									getApplicationContext(),
									"Successful Transfer to same user", 
									Toast.LENGTH_SHORT)
									.show();
							}
							else {
								Toast.makeText(
									getApplicationContext(),
									"Error: transfer failed", 
									Toast.LENGTH_SHORT)
									.show();
							}
						}
					
						else {
							Toast.makeText(getApplicationContext(), 
								"Error: Select two different accounts", Toast.LENGTH_SHORT).show();
						}
					}
				}
				
				else {
					Log.d("EMAIL:", " " + emailOrPhoneString);
					Toast.makeText(
							getApplicationContext(),
							"Error: One of the required fields is invalid", 
							Toast.LENGTH_SHORT)
							.show();
				}
				

				
				//closes Bundle
				Bundle userBundle = new Bundle();
				userBundle.putParcelable("user", customer);
				//intentUserHome.putExtra("user", customer);
				
				// TODO Auto-generated method stub
				if(userType < 2) {
				    intentUserHome.putExtra("user", customer);
					startActivity(intentUserHome);
				}
				else {
					intentCustomerInfo.putExtra("user", customer);
					startActivity(intentCustomerInfo);
				}
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle userBundle = new Bundle();
				userBundle.putParcelable("user", customer);
				//intentUserHome.putExtra("user", customer);
				
				if(userType < 2) {
				    intentUserHome.putExtra("user", customer);
					startActivity(intentUserHome);
				}
				else {
					intentCustomerInfo.putExtra("user", customer);
					startActivity(intentCustomerInfo);
				}
			}
		});
		}
	}
	@Override
	public void onBackPressed() {
	    // do nothing.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_transfer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}
