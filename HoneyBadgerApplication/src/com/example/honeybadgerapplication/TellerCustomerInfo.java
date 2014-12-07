package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.User;

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
import android.widget.Toast;

public class TellerCustomerInfo extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_customer_info);
		
		
		final Intent intentClose = new Intent(TellerCustomerInfo.this,
				CloseAccount.class);
		final Intent intentTellerHomePage = new Intent (TellerCustomerInfo.this, TellerHomePage.class);
		
		final Button closeButton = (Button) findViewById(R.id.tellerCloseAccountButton);
		final Button cancelButton = (Button) findViewById(R.id.tellerCancel);
		
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		} else {
			final User teller = userBundle.getParcelable("teller");
			final User customer = teller.getCustomer();
			
			
		
		
			closeButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (customer.getAccountCombo() == 0) {
						Toast.makeText(getApplicationContext(), "No Active Accounts!",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "Has Accounts!",
								Toast.LENGTH_SHORT).show();
						//Bundle userBundleOut = new Bundle();
						//userBundleOut.putParcelable("user", teller);
						//intentClose.putExtra("user", teller);
						//startActivity(intentClose);
					}
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
		getMenuInflater().inflate(R.menu.teller_customer_info, menu);
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
	public void onBackPressed() {
	    // do nothing.
	}
	
	public static int accountGenerator() {

        long timeSeed = System.nanoTime(); // to get the current date time value

        double randSeed = Math.random() * 1000; // random number generation

        long midSeed = (long) (timeSeed * randSeed); // mixing up the time and
                                                        // rand number.

                                                        // variable timeSeed
                                                        // will be unique
                                                       // variable rand will 
                                                       // ensure no relation 
                                                      // between the numbers

        String s = midSeed + "";
        String subStr = s.substring(0, 8);

        int finalSeed = Integer.parseInt(subStr);    // integer value
        return finalSeed;
    }

}
