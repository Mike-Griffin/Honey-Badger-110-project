package com.example.honeybadgerapp;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CloseAccount extends Activity {
	// Spinner to list all account
	private Spinner accountSpinner;
	// Cancel button
	private Button  cancelButton;
	// Close account
	private Button closeAccountButton;
	// Display account number
	//private String  accountNumber;
	// Parse account numbers text
	private List<String> accNum = new ArrayList<String>();
	// Parse account objects
	private List<ParseObject> accountList = new ArrayList<ParseObject>();
	// List account number
	private ArrayAdapter<String> accNumAdapt;
	// Account object
	private ParseObject accountObject;
	// the selected account
	private String accountSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_close_account);
		
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
		final Intent intentSuccessfulLogin = new Intent(CloseAccount.this,
				AccountInfo.class);
		final Intent intentCloseAccount = new Intent(CloseAccount.this,
				CloseAccount.class);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("parent", ParseUser.getCurrentUser());
		try {
			accountList = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < accountList.size(); i++){
			accNum.add(accountList.get(i).getObjectId());
		}
		accountSpinner = (Spinner)findViewById(R.id.account_num);
		cancelButton = (Button)findViewById(R.id.canel_close_account);
		closeAccountButton = (Button)findViewById(R.id.close_account);
		
		accNumAdapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, accNum);
		accNumAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountSpinner.setAdapter(accNumAdapt);
		

		
		
		// Close account button
		closeAccountButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Gets the user selected account
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
				accountSelected = accountSpinner.getSelectedItem().toString().trim();
				
				try {
					accountObject = query.get(accountSelected);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double num = accountObject.getInt("balance");
				Log.d("num", " "+ num);
				
				if(( num ) != 0) {
					Toast.makeText(getApplicationContext(),
							"Account must have zero balance", Toast.LENGTH_SHORT)
							.show();
					startActivity(intentCloseAccount);
				}
				else{
					accountObject.put("active", false);
					
					try {
						accountObject.save();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					startActivity(intentSuccessfulLogin);
				}
			}
		});
		
		
		// Cancel Button
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentSuccessfulLogin);
			}
		});

		
		
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
