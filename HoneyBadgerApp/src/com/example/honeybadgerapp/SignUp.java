package com.example.honeybadgerapp;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUp extends Activity implements OnItemSelectedListener {
	private Spinner stateSpinner;
	private EditText name_edit_text;
	private EditText username_edit_text;
	private EditText password_edit_text;
	private EditText email_edit_text;
	private EditText verify_password_edit_text;
	private EditText month_edit_text;
	private EditText day_edit_text;
	private EditText year_edit_text;
	private EditText address_edit_text;
	private EditText city_edit_text;
	private EditText zip_edit_text;
	private String name;
	private String username;
	private String password;
	private String email;
	private String birthday;
	private String address;
	private String city;
	private String state;
	private int zip;

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

		final Intent intentSuccessfulLogin = new Intent(SignUp.this,
				SuccessfulLogin.class);
		final Intent intentLogin = new Intent(SignUp.this, MainActivity.class);
		final Button doneButton = (Button) findViewById(R.id.doneButton);
		final Button cancelButton = (Button) findViewById(R.id.cancelButtonSignUpPage);

		name_edit_text = (EditText) findViewById(R.id.name);
		username_edit_text = (EditText) findViewById(R.id.username);
		password_edit_text = (EditText) findViewById(R.id.password);
		verify_password_edit_text = (EditText) findViewById(R.id.verifyPassword);
		email_edit_text = (EditText) findViewById(R.id.email);
		month_edit_text = (EditText) findViewById(R.id.month);
		day_edit_text = (EditText) findViewById(R.id.day);
		year_edit_text = (EditText) findViewById(R.id.year);
		address_edit_text = (EditText) findViewById(R.id.address);
		city_edit_text = (EditText) findViewById(R.id.city);
		zip_edit_text = (EditText) findViewById(R.id.zip);

		stateSpinner = (Spinner) findViewById(R.id.state);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.state_names,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stateSpinner.setAdapter(adapter);

		doneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = name_edit_text.getText().toString().trim();
				username = username_edit_text.getText().toString().trim();
				password = password_edit_text.getText().toString().trim();
				email = email_edit_text.getText().toString().trim();
				//birthday = month_edit_text.getText().toString().trim() + "/"
				//		+ day_edit_text.getText().toString().trim() + "/"
				//		+ year_edit_text.getText().toString().trim();
				//address = address_edit_text.getText().toString().trim();
				//city = city_edit_text.getText().toString().trim();
				//state = stateSpinner.getSelectedItem().toString().trim();
				//zip = Integer.parseInt(zip_edit_text.getText().toString()
				//		.trim());

				// if(error check) {} else {
				// Save new user data into Parse.com Data Storage
				ParseUser user = new ParseUser();
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				user.put("name", name);
				//user.put("birthday", birthday);
				//user.put("address", address);
				//user.put("city", city);
				//user.put("state", state);
				//user.put("zipCode", zip);

				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							// Show a simple Toast message upon successful
							// registration
							Toast.makeText(getApplicationContext(),
									"Successfully Signed up.",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Sign up Error" + e, Toast.LENGTH_SHORT)
									.show();
						}
					}
				});

				startActivity(intentSuccessfulLogin);
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentLogin);
			}
		});
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// state = stateSpinner.getSelectedItem().toString().trim();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
