package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.honeybadgerapi.Account;
import com.example.honeybadgerapi.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionHistory extends ActionBarActivity {
	
	private List<ParseObject> accountHistory = new ArrayList<ParseObject>();
	private TableLayout accountTable;
	private TableRow row0;
	private TextView type;
	private TextView aNum;
	private TextView bal;
	private TextView date;
	
	// private final Button backButton = (Button) findViewById(R.id.backButton);
	private final Intent userHomePageIntent = new Intent( TransactionHistory.this, UserHomePage.class);

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
		  final User user = userBundle.getParcelable("user");
		  final User customer = user.getCustomer();
		  
		  accountTable = (TableLayout) findViewById(R.id.accountTable);
			row0 = new TableRow(this);
			date = new TextView(this);
			type = new TextView(this);
			aNum = new TextView(this);
			//bal = new TextView(this);
			date.setText("Date");
			date.setPadding(40, 0, 0, 0);
			type.setText("Type");
			type.setPadding(40, 0, 0, 0);
			aNum.setText("Account Number");
			aNum.setPadding(40, 0, 0, 0);
			//bal.setText("Balance");
			//bal.setPadding(40, 0, 0, 0);
			row0.addView(date);
			row0.addView(type);
			row0.addView(aNum);
			//row0.addView(bal);
			accountTable.addView(row0);
			//List<ParseObject> transaction = new ArrayList<ParseObject>();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
			query.whereEqualTo("parent", ParseUser.getCurrentUser());
			try {
				accountHistory = query.find();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.d("string", "size: " + accountHistory.size());
			
			if (customer != null) {
				Account[] list = customer.getAccountList();
				int checkingNumber = customer.getChecking();				
				}
				
				for (int i = 0; i < accountHistory.size(); i++) {
					//if (accountHistory.get(i).getBoolean("active") == true) {

						TableRow row = new TableRow(this);
						TextView transactionDate = new TextView(this);
						TextView accountType = new TextView(this);
						TextView accountNumber = new TextView(this);
						//TextView balance = new TextView(this);

						accountNumber.setPadding(60, 50, 0, 0);
						//balance.setPadding(60, 50, 0, 0);
						String date = accountHistory.get(i).getCreatedAt().toString();
						String newDate = date.substring(0,9);
						transactionDate.setText(newDate);
						accountType.setText(accountHistory.get(i).getString("accType"));
						accountNumber.setText(Integer.toString(accountHistory.get(i)
								.getInt("accNum")));
						//balance.setText("$" + String.format( "%.2f", accountHistory.get(i).getBalance()));
						
						row.addView(transactionDate);
						row.addView(accountType);
						row.addView(accountNumber);
						//row.addView(balance);
						accountTable.addView(row);
					//}
				}
				  //Bundle outBundle = new Bundle();
				  //outBundle.putParcelable("user", customer);
				  //userHomePageIntent.putExtra("user", customer);
				
			}
		/*backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(userHomePageIntent);
			}
		});*/
		 	
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
		return super.onOptionsItemSelected(item);
	}
}
