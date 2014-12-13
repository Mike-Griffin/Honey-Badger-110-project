package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.Customer;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

// Sign Up class used for a new User to create an account
public class SignUp extends ActionBarActivity implements
		DatePickerFragment.TheListener {
	// Declare all local variables used
	private Spinner stateSpinner;
	private EditText name_edit_text;
	private EditText username_edit_text;
	private EditText password_edit_text;
	private EditText email_edit_text;
	private EditText verify_password_edit_text;
	private EditText birthday_text_view;
	private EditText address_edit_text;
	private EditText city_edit_text;
	private EditText zip_edit_text;
	private EditText phone_edit_text;
	private EditText account_number_text;
	private String name;
	private String username;
	private String password;
	private String email;
	private String birthday;
	private String address;
	private String city;
	private String state;
	private String phone;
	private int zip;
	private int accountNumber;
	private int passwordLength;

	// Creates Customer Bundle for Parse (our server)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);

		// Instantiate buttons on sign up page
		final Intent intentLogin = new Intent(SignUp.this, Login.class);
		final Button doneButton = (Button) findViewById(R.id.doneButton);
		final Button cancelButton = (Button) findViewById(R.id.cancelButton);

		// Create text fields for user input
		name_edit_text = (EditText) findViewById(R.id.name);
		username_edit_text = (EditText) findViewById(R.id.username);
		password_edit_text = (EditText) findViewById(R.id.password);
		verify_password_edit_text = (EditText) findViewById(R.id.verifyPassword);
		email_edit_text = (EditText) findViewById(R.id.email);
		birthday_text_view = (EditText) findViewById(R.id.birthday);
		address_edit_text = (EditText) findViewById(R.id.address);
		city_edit_text = (EditText) findViewById(R.id.city);
		zip_edit_text = (EditText) findViewById(R.id.zip);
		phone_edit_text = (EditText) findViewById(R.id.phone);
		account_number_text = (EditText) findViewById(R.id.accountNumber);

		// Create drop-down menu for State of user
		stateSpinner = (Spinner) findViewById(R.id.state);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.state_names,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stateSpinner.setAdapter(adapter);

		// Create click listener for birthday entry
		birthday_text_view.setOnClickListener(new View.OnClickListener() {

			// Allows user to pick date from calandar
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// Auto-generated method stub
				DialogFragment picker = new DatePickerFragment();
				picker.show(getFragmentManager(), "datePicker");
			}
		});

		// create listener for the done button on the Sign Up Page
		doneButton.setOnClickListener(new View.OnClickListener() {

			// Moves the inputs to the checkers to verify valid input and
			// sets valid entries to user profile variables
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = name_edit_text.getText().toString().trim();
				username = username_edit_text.getText().toString().trim();
				password = password_edit_text.getText().toString().trim();
				passwordLength = password.length();
				
				// If the password and password verify entries do not match
				if (!password.equals(verify_password_edit_text.getText()
						.toString().trim())) {
					Toast.makeText(getApplicationContext(),
							"Passwords do not match!", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				// The password needs to be at least 6 characters, check this here
				if (passwordLength < 6) {
					Toast.makeText(getApplicationContext(),
							"Password must be at least 6 characters!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// The password must contain at least 1 letter, check this here
				if (password.matches("[0-9]+")) {
					Toast.makeText(getApplicationContext(),
							"Password must contain at least 1 letter!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// The password must contain at least 1 number, check this here
				if (password.matches("[a-zA-Z]+")) {
					Toast.makeText(getApplicationContext(),
							"Password must contain at least 1 number!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				email = email_edit_text.getText().toString().trim();
				if ( !email.contains("@") ) { // valid emails always have @
				// if entry does not contain @, give error for invalid email entry
				Toast.makeText(getApplicationContext(),
						"Invalid Email!",
						Toast.LENGTH_SHORT).show();
				return;
				}
				// Get input values and store then into corresponding local variables
				// from the text fields
				birthday = birthday_text_view.getText().toString().trim();
				address = address_edit_text.getText().toString().trim();
				city = city_edit_text.getText().toString().trim();
				state = stateSpinner.getSelectedItem().toString().trim();
				phone = phone_edit_text.getText().toString().trim();

				// Send to the server to create new User
				try {
					zip = Integer.parseInt(zip_edit_text.getText().toString()
							.trim());
				} catch (Exception NumberFormatException) {
					Toast.makeText(getApplicationContext(), "Invalid Zip Code",
							Toast.LENGTH_SHORT).show();

					return;
				}
				// Send the new account number to the server
				try {
					accountNumber = Integer.parseInt(account_number_text
							.getText().toString().trim());
				} catch (Exception NumberFormatException) {
					accountNumber = 0;
				}

				// Get the account query from the server
				ParseObject account = null;
				if (accountNumber != 0) {
					ParseQuery<ParseObject> query = ParseQuery
							.getQuery("Account");
					query.whereEqualTo("accountNumber", accountNumber);
					try {
						account = query.getFirst();
						if (account.getString("parent") != null) {
							Toast.makeText(
									getApplicationContext(),
									"This account number belongs to someone else!!",
									Toast.LENGTH_SHORT).show();

							return;
						}
					} catch (ParseException e) { // Error in finding new account
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),
								"Cannot find matched account!!",
								Toast.LENGTH_SHORT).show();

						return;
					}
				}
				
				// Save new user data into Parse.com Data Storage
				if (name.equals("") || username.equals("")
						|| password.equals("") || email.equals("")
						|| birthday.equals("") || address.equals("")
						|| city.equals("") || state.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Fill Up All Information!",
							Toast.LENGTH_SHORT).show();

					return;
				}
 
				// Create the new customer using all verified valid entreis
				Customer customer = new Customer();
				customer.signup(name, username, password, email, birthday,
						address, city, state, phone, zip, accountNumber);

				ParseUser user = ParseUser.getCurrentUser();
				if (customer.getSignUpStatus()) {
					customer.login(username, password);
					if (accountNumber != 0) {
						String accountType = account.getString("type");
						if (accountType.equals("Checking Account")) {
							user.put("accountCombo", 1);
							user.put("checkingAccount", accountNumber);
						}
						else {
							user.put("accountCombo", 2);
							user.put("savingAccount", accountNumber);
						}
						account.put("parent", user);
						try {
							account.save();
						} catch (ParseException e) {
						}

					} else {
						user.put("accountCombo", 0);
					}
					try {
						user.save();
					} catch (ParseException e) {
					} // Oh man I hope it doesn't fail this bad
					Toast.makeText(getApplicationContext(), "FUCKKK!!",
							Toast.LENGTH_SHORT).show();
					startActivity(intentLogin);
				} else { // Display Error message if sign up failed
					Toast.makeText(getApplicationContext(), "Sign Up Failed!!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// Creates listener for cancel button if the User decides they do not want to sign up
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Auto-generated method stub
				startActivity(intentLogin);
			}
		});
	}
	
	// Creates method manager for managing the inputs of the user
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		return true;
	}

	// Disables the back button
	@Override
	public void onBackPressed() {
		// do nothing.
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

	// Auto-generated method stub to set text
	@Override
	public void returnDate(String date) {
		birthday_text_view.setText(date);
	}
}
