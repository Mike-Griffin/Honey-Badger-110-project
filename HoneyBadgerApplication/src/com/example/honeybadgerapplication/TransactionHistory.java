package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.User;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TransactionHistory extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_history);
		
		//gets customer object
		Bundle userBundle = this.getIntent().getExtras();
		//final User customer = null;
		if(userBundle == null) {
			//error, Bundle should not be null
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		}
		else {
		  final User customer = userBundle.getParcelable("user");
		  
		  
		}  
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_history, menu);
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
