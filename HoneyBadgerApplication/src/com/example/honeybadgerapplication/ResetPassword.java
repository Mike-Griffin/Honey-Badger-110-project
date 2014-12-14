package com.example.honeybadgerapplication;


import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends ActionBarActivity {
	
	private EditText email_edit_text;
	private String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		
		//initialize parse for use in this class
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		
		final Intent intentLogin = new Intent(ResetPassword.this, Login.class);
		final Button doneButton = (Button) findViewById(R.id.doneButton);
		final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		
		email_edit_text = (EditText) findViewById(R.id.email);
		
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentLogin);
			}
		});
		doneButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = email_edit_text.getText().toString().trim();
				ParseUser user = new ParseUser();
				
				user.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
					public void done(ParseException e){
						if(e == null) {
							Toast.makeText(getApplicationContext(), "Check your email!",
									Toast.LENGTH_SHORT).show();
							startActivity(intentLogin);
						}
						else {
							Toast.makeText(getApplicationContext(), "No such email!",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reset_password, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.
	                                                        INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
