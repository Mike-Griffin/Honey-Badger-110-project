package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.User;
import com.parse.Parse;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TellerAddAccounts extends ActionBarActivity {
	
	private EditText amount_edit_text;
	private double amount = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_add_accounts);
		
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
		
		final Intent intentTellerHomePage = new Intent (TellerAddAccounts.this, TellerHomePage.class);
		final Button cancelButton = (Button) findViewById(R.id.cancel);
		final Button addCheckingButton = (Button) findViewById(R.id.addChecking);
		amount_edit_text = (EditText) findViewById(R.id.amount_cred);
	
		
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		} else {
			final User teller = userBundle.getParcelable("teller");
			final User customer = teller.getCustomer();
			
			addCheckingButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					amount = Double.parseDouble(amount_edit_text.getText()
							.toString().trim());			
				}
			});
			cancelButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle userBundle = new Bundle();
					userBundle.putParcelable("teller", customer);
					intentTellerHomePage.putExtra("teller", customer);
					startActivity(intentTellerHomePage);
					
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teller_add_accounts, menu);
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
