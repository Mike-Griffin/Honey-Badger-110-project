package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.honeybadgerapi.Account;
import com.example.honeybadgerapi.User;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionHistory extends ActionBarActivity {
	
	private List<Account> account = new ArrayList<Account>();
	private TableLayout accountTable;
	private TableRow row0;
	private TextView type;
	private TextView aNum;
	private TextView bal;
	private TextView date;
	

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
			bal = new TextView(this);
			type.setText("Type");
			type.setPadding(10, 0, 0, 0);
			aNum.setText("Account Number");
			aNum.setPadding(10, 0, 0, 0);
			bal.setText("Balance");
			bal.setPadding(30, 0, 0, 0);
			row0.addView(date);
			row0.addView(type);
			row0.addView(aNum);
			row0.addView(bal);
			accountTable.addView(row0);

			if (customer != null) {
				Account[] list = customer.getAccountList();
				switch (customer.getAccountCombo()) {
				case 1:
					account.add(list[0]);
					break;
				case 2:
					account.add(list[1]);
					break;
				case 3:
					account.add(list[0]);
					account.add(list[1]);
					break;
				default:
					break;
				}
			}

			for (int i = 0; i < account.size(); i++) {
				if (account.get(i).getStatus() == true) {

					TableRow row = new TableRow(this);
					TextView accountType = new TextView(this);
					TextView accountNumber = new TextView(this);
					TextView balance = new TextView(this);

					accountNumber.setPadding(60, 50, 0, 0);
					balance.setPadding(60, 50, 0, 0);

					accountType.setText(account.get(i).getAccountType());
					accountNumber.setText(Integer.toString(account.get(i)
							.getAccountNumber()));
					balance.setText("$" + String.format( "%.2f", account.get(i).getBalance()));
					//Double.toString(account.get(i).getBalance()));aa

					row.addView(accountType);
					row.addView(accountNumber);
					row.addView(balance);
					accountTable.addView(row);
				}
			}
		  
		  
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
