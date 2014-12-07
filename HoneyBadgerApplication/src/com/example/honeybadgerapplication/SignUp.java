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

public class SignUp extends ActionBarActivity implements
		DatePickerFragment.TheListener {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		// ParseACL.setDefaultACL(defaultACL, this);

		final Intent intentUserHomePage = new Intent(SignUp.this,
				UserHomePage.class);
		final Intent intentLogin = new Intent(SignUp.this, Login.class);
		final Button doneButton = (Button) findViewById(R.id.doneButton);
		final Button cancelButton = (Button) findViewById(R.id.cancelButton);

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

		stateSpinner = (Spinner) findViewById(R.id.state);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.state_names,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stateSpinner.setAdapter(adapter);

		birthday_text_view.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment picker = new DatePickerFragment();
				picker.show(getFragmentManager(), "datePicker");
			}
		});

		doneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = name_edit_text.getText().toString().trim();
				username = username_edit_text.getText().toString().trim();
				password = password_edit_text.getText().toString().trim();
				if (!password.equals(verify_password_edit_text.getText()
						.toString().trim())) {
					Toast.makeText(getApplicationContext(),
							"Passwords do not match!", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				email = email_edit_text.getText().toString().trim();
				birthday = birthday_text_view.getText().toString().trim();
				address = address_edit_text.getText().toString().trim();
				city = city_edit_text.getText().toString().trim();
				state = stateSpinner.getSelectedItem().toString().trim();
				phone = phone_edit_text.getText().toString().trim();

				try {
					zip = Integer.parseInt(zip_edit_text.getText().toString()
							.trim());
				} catch (Exception NumberFormatException) {
					Toast.makeText(getApplicationContext(), "Invalid Zip Code",
							Toast.LENGTH_SHORT).show();

					return;
				}

				try {
					accountNumber = Integer.parseInt(account_number_text
							.getText().toString().trim());
				} catch (Exception NumberFormatException) {
					accountNumber = 0;
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
					} catch (ParseException e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),
								"Cannot find matched account!!",
								Toast.LENGTH_SHORT).show();

						return;
					}
				}

				Customer customer = new Customer();
				customer.signup(name, username, password, email, birthday,
						address, city, state, phone, zip, accountNumber);

				if (customer.getSignUpStatus()) {
					customer.login(username, password);
					if (accountNumber != 0) {
						account.put("parent", ParseUser.getCurrentUser());
						try {
							account.save();
						} catch (ParseException e) {
						}
					}
					startActivity(intentUserHomePage);
				} else
				{
					Toast.makeText(getApplicationContext(),
							"Sign Up Failed!!",
							Toast.LENGTH_SHORT).show();
				}
				/*
				 * ParseUser user = new ParseUser(); user.setUsername(username);
				 * user.setPassword(password); user.setEmail(email);
				 * user.put("name", name); user.put("birthday", birthday);
				 * user.put("address", address); user.put("city", city);
				 * user.put("state", state); user.put("zipCode", zip);
				 * user.put("primaryAccount", 0); user.put("phone", phone);
				 * 
				 * user.signUpInBackground(new SignUpCallback() { public void
				 * done(ParseException e) { if (e == null) { // Show a simple
				 * Toast message upon successful // registration
				 * Toast.makeText(getApplicationContext(),
				 * "Successfully Signed up.", Toast.LENGTH_SHORT).show(); } else
				 * { Toast.makeText(getApplicationContext(), "Sign up Error" +
				 * e, Toast.LENGTH_SHORT) .show(); } } });
				 */
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentLogin);
			}
		});

		/*
		 * // Temporary Intent for going back to login, both done and cancel go
		 * back to login final Intent intentTemp = new Intent(SignUp.this,
		 * Login.class);
		 * 
		 * final Button doneButton = (Button) findViewById(R.id.doneButton);
		 * final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		 * 
		 * doneButton.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub startActivity(intentTemp); } });
		 * 
		 * cancelButton.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub startActivity(intentTemp); } });
		 */
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
	
	@Override
	public void onBackPressed() {
	    // do nothing.
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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

	@Override
	public void returnDate(String date) {
		// TODO Auto-generated method stub
		birthday_text_view.setText(date);
	}
}
