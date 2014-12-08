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
	private int passwordLength;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_sign_up);
		
		final Button confirmButton = (Button) findViewById(R.id.confirmButton);
		final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		
		final Intent intentTellerHomePage = new Intent( TellerSignUp.this,
				TellerHomePage.class);
	
		name_edit_text = (EditText) findViewById(R.id.name);
		username_edit_text = (EditText) findViewById(R.id.username);
		password_edit_text = (EditText) findViewById(R.id.password);
		verify_password_edit_text = (EditText) findViewById(R.id.verifyPassword);
		email_edit_text = (EditText) findViewById(R.id.email);		
		
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		}
		else {
			final User teller = userBundle.getParcelable("teller");
			confirmButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					name = name_edit_text.getText().toString().trim();
					username = username_edit_text.getText().toString().trim();
					password = password_edit_text.getText().toString().trim();
					passwordLength = password.length();
					
					if (!password.equals(verify_password_edit_text.getText()
							.toString().trim())) {
						Toast.makeText(getApplicationContext(),
							"Passwords do not match!", Toast.LENGTH_SHORT)
							.show();
						return;
					}
					if (passwordLength < 6) {
						Toast.makeText(getApplicationContext(),
							"Password must be at least 6 characters!",
							Toast.LENGTH_SHORT).show();
						return;
					}
					if (password.matches("[0-9]+")) {
						Toast.makeText(getApplicationContext(),
							"Password must contain at least 1 letter!",
							Toast.LENGTH_SHORT).show();
						return;
					}
					if (password.matches("[a-zA-Z]+")) {
						Toast.makeText(getApplicationContext(),
							"Password must contain at least 1 number!",
							Toast.LENGTH_SHORT).show();
						return;
					}	
					email = email_edit_text.getText().toString().trim();
					if ( !email.contains("@") ) {
					Toast.makeText(getApplicationContext(),
							"Invalid Email!",
							Toast.LENGTH_SHORT).show();
					return;
					}
					
					
					if (name.equals("") || username.equals("")
						|| password.equals("") || email.equals("")) {
						Toast.makeText(getApplicationContext(),
							"Please Fill Up All Information!",
							Toast.LENGTH_SHORT).show();

						return;
					}
					Teller teller = new Teller();
					teller.signup( username, password, email);
					
					// TODO Auto-generated method stub
					Bundle userBundleOut = new Bundle();
					userBundleOut.putParcelable("user", teller);
					intentTellerHomePage.putExtra("user", teller);
					startActivity(intentTellerHomePage);
				}
			});	
			
			cancelButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
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
	
	//hello
}
