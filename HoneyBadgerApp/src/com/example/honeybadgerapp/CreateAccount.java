package com.example.honeybadgerapp;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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

public class CreateAccount extends Activity implements OnItemSelectedListener {
	private Spinner accountTypeSpinner;
	private EditText amount_edit_text;
	private String accountType;
	private double amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");

		final Intent intentAccountInfo = new Intent(CreateAccount.this,
				AccountInfo.class);
		final Button confirmButton = (Button) findViewById(R.id.confirmButton);
		final Button cancelButton = (Button) findViewById(R.id.cancelButtonCreateAccountPage);

		amount_edit_text = (EditText) findViewById(R.id.amount);

		accountTypeSpinner = (Spinner) findViewById(R.id.account_types);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.account_types,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountTypeSpinner.setAdapter(adapter);

		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accountType = accountTypeSpinner.getSelectedItem().toString().trim();
				amount = Double.parseDouble(amount_edit_text.getText()
						.toString().trim());
				
				// Save new user data into Parse.com Data Storage
				final ParseObject account = new ParseObject("Account");
				final ParseObject user = ParseUser.getCurrentUser();
				account.put("type", accountType);
				account.put("balance", amount);
				account.put("parent", user);
				account.put("active", true);
				account.saveInBackground(new SaveCallback() {
					  public void done(ParseException e) {
					    if (e == null) {
					      // Success!
					    	account.put("accountNumber", account.getObjectId().hashCode());
					    	if(user.getInt("primaryAccount") == 0 && accountType.equals("Checking Account"))
					    		user.put("primaryAccount", account.getObjectId().hashCode());
					    	
					    	account.saveInBackground();
							startActivity(intentAccountInfo);
					    } else {
					      // Failure!
					    }
					  }
					});
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

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
		getMenuInflater().inflate(R.menu.create_account, menu);
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
		// accountType = accountTypeSpinner.getSelectedItem().toString().trim();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
