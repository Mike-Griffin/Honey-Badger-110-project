package com.example.honeybadgerapplication;

import org.apache.http.ParseException;

import com.example.honeybadgerapi.User;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TellerHomePage extends ActionBarActivity {
	
	private EditText username_edit_text;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_home_page);
		
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
	
		final Intent intentTellerCustomerInfo = new Intent (TellerHomePage.this, TellerCustomerInfo.class);
		final Button lookUpButton = (Button) findViewById(R.id.lookUp);
		username_edit_text = (EditText) findViewById(R.id.username);
		
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		} else {
			final User teller = userBundle.getParcelable("user");

			
		
		
			lookUpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = username_edit_text.getText().toString().trim();
				ParseObject parseUser = null;
				User user = null;
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.whereEqualTo("username", username);
				try {
					parseUser = query.getFirst();
					teller.setCustomer(username);
					Bundle userBundleOut = new Bundle();
					userBundleOut.putParcelable("teller", teller);
					intentTellerCustomerInfo.putExtra("teller", teller);
					startActivity(intentTellerCustomerInfo);
				} catch (com.parse.ParseException e) {
					// TODO Auto-generated catch block
					Toast.makeText(
						getApplicationContext(),
						"No such user exist, please signup"
						+ e, Toast.LENGTH_SHORT)
						.show();
				}
				
			}
		});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teller_home_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			Log.d("user", ParseUser.getCurrentUser().getObjectId());
			try{
			ParseUser.logOut();
			}catch(ParseException e) {
				e.printStackTrace();
			}
			//Log.d("user", ParseUser.getCurrentUser().getObjectId());
			if (ParseUser.getCurrentUser() == null) {
				Toast.makeText(getApplicationContext(), "Successful Logout!",
						Toast.LENGTH_SHORT).show();
				final Intent intentLogin = new Intent(TellerHomePage.this,
						Login.class);
				startActivity(intentLogin);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
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
}
