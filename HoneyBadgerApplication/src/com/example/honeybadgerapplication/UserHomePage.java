package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.User;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UserHomePage extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home_page);
		
		final Intent intentCredDeb = new Intent(UserHomePage.this, CreditDebit.class);
		final Intent intentTransfer = new Intent(UserHomePage.this, AccountTransfer.class);
		final Intent intentClose = new Intent(UserHomePage.this, CloseAccount.class);
		
		final Button credDebButton = (Button) findViewById(R.id.creditDebitButton);
		final Button transferButton = (Button) findViewById(R.id.accountTransferButton);
		final Button closeButton = (Button) findViewById(R.id.closeAccountButton);
		
		credDebButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentCredDeb);
			}
		});
		
		transferButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentTransfer);
			}
		});
		
		closeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentClose);
			}
		});
		
		Bundle userBundle = this.getIntent().getExtras();
		User customer = null;
		if(userBundle != null)
			customer = userBundle.getParcelable("user");
		
		customer.updateAccountList();
		Log.d("hi", "hi");
		Log.d("username", Integer.toString((int) customer.getBalance(1)));
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_home_page, menu);
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
