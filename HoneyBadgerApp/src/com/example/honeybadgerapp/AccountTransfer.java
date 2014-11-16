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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AccountTransfer extends Activity {
	private Spinner accountFromSpinner;
	private Spinner accountToSpinner;
	private EditText amount_edit_text;
	private EditText email_edit_text;
	private Button confirm_button;
	private Button cancel_button;
	private List<String> accountList = new ArrayList<String>();
	private ArrayAdapter<String> accountNumberFromAdapt;
	private ArrayAdapter<String> accountNumberToAdapt;
	private List<ParseObject> accountObjectList = new ArrayList<ParseObject>();
	private ParseObject accountFromObject;
	private ParseUser accountToUser;
	private ParseObject accountToObject;
	private String accountFromSelected;
	private String accountToSelected;
	private String accountToUsername;
	private boolean otherAccount = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_transfer);

		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
		final Intent intentAccountInfo = new Intent(AccountTransfer.this,
				AccountInfo.class);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("parent", ParseUser.getCurrentUser());
		query.whereEqualTo("active", true);
		try {
			accountObjectList = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < accountObjectList.size(); i++) {
			accountList.add(Integer.toString(accountObjectList.get(i).getInt(
					"accountNumber")));
		}

		// account from spinner
		accountFromSpinner = (Spinner) findViewById(R.id.transferFromSpinner);
		accountNumberFromAdapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, accountList);
		accountNumberFromAdapt
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountFromSpinner.setAdapter(accountNumberFromAdapt);

		final List<String> accountToList = new ArrayList<String>(accountList);
		accountToList.add("To Someone Else");

		// account to spinner
		accountToSpinner = (Spinner) findViewById(R.id.transferToSpinner);
		accountNumberToAdapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, accountToList);
		accountNumberToAdapt
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountToSpinner.setAdapter(accountNumberToAdapt);

		// amount field
		amount_edit_text = (EditText) findViewById(R.id.amountToTransfer);
		email_edit_text = (EditText) findViewById(R.id.accountEmailToTransfer);
		confirm_button = (Button) findViewById(R.id.confirmButton);
		cancel_button = (Button) findViewById(R.id.cancelButton);

		accountToSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// Getting Value of Selected Item
						String val = accountToSpinner.getSelectedItem()
								.toString();
						if (val.equals(accountToList.get(accountToList.size() - 1)))
							otherAccount = true;
						else
							otherAccount = false;

						email_edit_text.setEnabled(otherAccount);
					}

					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		confirm_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double amount = Double.parseDouble(amount_edit_text.getText()
						.toString().trim());
				accountFromSelected = accountFromSpinner.getSelectedItem()
						.toString().trim();
				accountToSelected = accountToSpinner.getSelectedItem()
						.toString().trim();
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
				query.whereEqualTo("accountNumber",
						Integer.parseInt(accountFromSelected));
				try {
					accountFromObject = query.getFirst();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (accountToSelected.equals(accountToList.get(accountToList
						.size() - 1))) {
					ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
					userQuery.whereEqualTo("email", email_edit_text.getText().toString()
							.trim());
					try {
						accountToUser = userQuery.getFirst();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getApplicationContext(),
								email_edit_text.toString()
								.trim() + "   " + e, Toast.LENGTH_SHORT)
								.show();
						e.printStackTrace();
						return;
					}

					query = ParseQuery.getQuery("Account");
					query.whereEqualTo("accountNumber",
							accountToUser.getInt("primaryAccount"));
					try {
						accountToObject = query.getFirst();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				double accountFromBalance = accountFromObject.getInt("balance");
				double accountToBalance = accountToObject.getInt("balance");
				if(accountFromBalance < amount) {
					Toast.makeText(getApplicationContext(),
							"Need More Money", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				accountFromObject.put("balance", accountFromBalance - amount);
				accountToObject.put("balance", accountToBalance + amount);
				try {
					accountFromObject.save();
					accountToObject.save();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				startActivity(intentAccountInfo);
			}
		});

		cancel_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentAccountInfo);
			}
		});

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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
