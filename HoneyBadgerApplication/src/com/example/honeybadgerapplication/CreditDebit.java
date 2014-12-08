package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.honeybadgerapi.Teller;
import com.example.honeybadgerapi.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreditDebit extends ActionBarActivity {
	private Spinner accountSpinner;
	private Spinner actionSpinner;
	private EditText amount_edit_text;
	//Strings of existing User accounts
	private List<String> accStrings = new ArrayList<String>();
	//String Array Adapter of User accounts
	private ArrayAdapter<String> accStringsAdapt;
	private String action;
	private String accountSelected;
	private double amountRequested = 0.0;
	private User customer;
	private Teller teller;
	private int accountNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_debit);
		
		final Intent intentCustomerInfo = new Intent(CreditDebit.this, TellerCustomerInfo.class);
		final Intent intentCreditDebit = new Intent(CreditDebit.this,
				CreditDebit.class);
		
		final Button confirmButton = (Button) findViewById(R.id.conf_cred);
		final Button cancelButton = (Button) findViewById(R.id.cancel_cred);
		
		Bundle tellerBundle = this.getIntent().getExtras();
		if( tellerBundle != null)
			teller = tellerBundle.getParcelable("user");
		else{
			startActivity( intentCustomerInfo );
		}
		customer = teller.getCustomer();
		

		/*
		ParseObject transaction = null;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
		query.whereEqualTo("accountNumber", accountNumber);
			try {
				account = query.getFirst();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/


		
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
		
		accountSpinner = (Spinner) findViewById(R.id.account_num);
		actionSpinner = (Spinner) findViewById(R.id.action);
		amount_edit_text = (EditText) findViewById(R.id.amount_cred);
		
		//Creates Spinner
		accStringsAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, accStrings);
		accStringsAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountSpinner.setAdapter(accStringsAdapt);
		
		ArrayAdapter<CharSequence> actionAdapt = ArrayAdapter
				.createFromResource(this, R.array.cred_or_deb,
						android.R.layout.simple_spinner_item);
		actionAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		actionSpinner.setAdapter(actionAdapt);
		
		
		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Amount of $$$
				accountSelected = accountSpinner.getSelectedItem().toString().trim();
				action = actionSpinner.getSelectedItem().toString().trim();
				amountRequested = Double.parseDouble(amount_edit_text.getText()
						.toString().trim());
				
				if(action.equals("Debit")){
					if( accountSelected.equals("Checking Account")){
						if( customer.debit( 1, amountRequested) == false ){
							Bundle nextBundle = new Bundle();
							nextBundle.putParcelable("user", teller);
							intentCreditDebit.putExtra("user", teller);
							
							Toast.makeText(getApplicationContext(),
							"Insufficient funds to debit", Toast.LENGTH_SHORT)
							.show();
							startActivity( intentCreditDebit );	
						}
						
						else {
							ParseObject transaction = new ParseObject("Transaction");
							accountNumber = customer.getChecking();
							
							transaction.put("accNum", accountNumber);
							transaction.put("log", "debit");
							transaction.put("amount",amountRequested);
							transaction.put("accType", "Checking Account");
							transaction.put("parent", customer);
							try {
								Toast.makeText(getApplicationContext(),
								"Debit to savings account", Toast.LENGTH_SHORT)
								.show();					
								transaction.save();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					else if( accountSelected.equals("Savings Account")){
						if( customer.debit( 2, amountRequested) == false ){
							Bundle nextBundle = new Bundle();
							nextBundle.putParcelable("user", teller);
							intentCreditDebit.putExtra("user", teller);
							
							Toast.makeText(getApplicationContext(),
							"Insufficient funds to debit", Toast.LENGTH_SHORT)
							.show();
						}
						
						else {
							ParseObject transaction = new ParseObject("Transaction");
							accountNumber = customer.getSaving();
							
							transaction.put("accNum", accountNumber);
							transaction.put("log", "debit");
							transaction.put("amount",amountRequested);
							transaction.put("accType", "Savings Account");
							transaction.put("parent", customer);
							try {
								Toast.makeText(getApplicationContext(),
								"Debit to savings account", Toast.LENGTH_SHORT)
								.show();					
								transaction.save();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
				else if( action.equals("Credit")){
					if(accountSelected.equals("Checking Account")) {
						customer.credit( 1, amountRequested );
						accountNumber = customer.getChecking();
						
						ParseObject transaction = new ParseObject("Transaction");
						transaction.put("accNum", accountNumber);
						transaction.put("log", "credit");
						transaction.put("amount",amountRequested);
						transaction.put("accType", "Checking Account");
						transaction.put("parent", ParseUser.getCurrentUser());
						try {
							Toast.makeText(getApplicationContext(),
							"Credit to checking account", Toast.LENGTH_SHORT)
							.show();
							startActivity( intentCreditDebit );							
							transaction.save();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					else if( accountSelected.equals("Savings Account")){
						customer.credit( 2, amountRequested);
						ParseObject transaction = new ParseObject("Transaction");
						accountNumber = customer.getSaving();
						
						transaction.put("accNum", accountNumber);
						transaction.put("log", "credit");
						transaction.put("amount",amountRequested);
						transaction.put("accType", "Savings Account");
						transaction.put("parent", customer);
						try {
							Toast.makeText(getApplicationContext(),
							"Credit to savings account", Toast.LENGTH_SHORT)
							.show();
							startActivity( intentCreditDebit );							
							transaction.save();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				Bundle nextBundle = new Bundle();
				nextBundle.putParcelable("user", teller);
				intentCustomerInfo.putExtra("user", teller);
				
				startActivity( intentCustomerInfo );
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle nextBundle = new Bundle();
				nextBundle.putParcelable("user", teller);
				intentCustomerInfo.putExtra("user", teller);
				
				startActivity(intentCustomerInfo);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.credit_debit, menu);
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



