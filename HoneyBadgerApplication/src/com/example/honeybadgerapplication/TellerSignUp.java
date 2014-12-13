package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.Teller;
import com.example.honeybadgerapi.User;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TellerSignUp extends ActionBarActivity {
	private EditText name_edit_text;
	private EditText username_edit_text;
	private EditText password_edit_text;
	private EditText email_edit_text;
	private EditText verify_password_edit_text;
	private String name;
	private String username;
	private String password;
	private String email;
	private int passwordLength;		// for checking if length of pw is valid
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_sign_up); // load signup view
		// Confirm and Cancel buttons after finishing sign up
		final Button confirmButton = (Button) findViewById(R.id.confirmButton);
		final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		// Intent to load next view -> Teller Home Page after signing up
		final Intent intentTellerHomePage = new Intent( TellerSignUp.this,
				TellerHomePage.class);
		// text views to capture user input
		name_edit_text = (EditText) findViewById(R.id.name);
		username_edit_text = (EditText) findViewById(R.id.username);
		password_edit_text = (EditText) findViewById(R.id.password);
		verify_password_edit_text = (EditText) findViewById(R.id.verifyPassword);
		email_edit_text = (EditText) findViewById(R.id.email);		
		// Check if bundle with Teller from prev. view exists - error if not
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		}
		// Bundle is valid
		else {
			// Grab Teller object from bundle
			final User teller = userBundle.getParcelable("teller");
			// Set click listener on the confirm button -> check all inputs
			confirmButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// Grab teller sign up info
					name = name_edit_text.getText().toString().trim();
					username = username_edit_text.getText().toString().trim();
					password = password_edit_text.getText().toString().trim();
					passwordLength = password.length();
					// Check if password entry and password verify match
					if (!password.equals(verify_password_edit_text.getText()
							.toString().trim())) {
						Toast.makeText(getApplicationContext(),
							"Passwords do not match!", Toast.LENGTH_SHORT)
							.show();
						return;
					}
					// Password must be > 6 characters long
					if (passwordLength < 6) {
						Toast.makeText(getApplicationContext(),
							"Password must be at least 6 characters!",
							Toast.LENGTH_SHORT).show();
						return;
					}
					// Password must not be only all numbers
					if (password.matches("[0-9]+")) {
						Toast.makeText(getApplicationContext(),
							"Password must contain at least 1 letter!",
							Toast.LENGTH_SHORT).show();
						return;
					}
					// Password must not be only all letters
					if (password.matches("[a-zA-Z]+")) {
						Toast.makeText(getApplicationContext(),
							"Password must contain at least 1 number!",
							Toast.LENGTH_SHORT).show();
						return;
					}
					// Email must be a valid email or Parse -> error
					email = email_edit_text.getText().toString().trim();
					if ( !email.contains("@") ) {
					Toast.makeText(getApplicationContext(),
							"Invalid Email!",
							Toast.LENGTH_SHORT).show();
					return;
					}
					
					// If any fields are empty, reject
					if (name.equals("") || username.equals("")
						|| password.equals("") || email.equals("")) {
						Toast.makeText(getApplicationContext(),
							"Please Fill Up All Information!",
							Toast.LENGTH_SHORT).show();

						return;
					}
					// Sign up for a teller with provided info
					Teller teller = new Teller();
					teller.signup( username, password, email);
					
					// Bundle Teller object to send to Teller Home Page view
					Bundle userBundleOut = new Bundle();
					userBundleOut.putParcelable("user", teller);
					intentTellerHomePage.putExtra("user", teller);
					startActivity(intentTellerHomePage);
				}
			});	
			// Set cancel button click listener
			cancelButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// Bundle Teller object to just return to Teller Home Page
					Bundle userBundleOut = new Bundle();
					userBundleOut.putParcelable("user", teller);
					intentTellerHomePage.putExtra("user", teller);
					startActivity(intentTellerHomePage);
				}
			});		
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teller_sign_up, menu);
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
