package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;

import com.example.honeybadgerapi.Account;
import com.example.honeybadgerapi.User;
import com.parse.ParseObject;
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

public class UserHomePage extends ActionBarActivity {

	private List<Account> account = new ArrayList<Account>();
	private TableLayout accountTable;
	private TableRow row0;
	private TextView type;
	private TextView aNum;
	private TextView bal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home_page);

		final Intent intentCredDeb = new Intent(UserHomePage.this,
				CreditDebit.class);
		final Intent intentTransfer = new Intent(UserHomePage.this,
				AccountTransfer.class);
		final Intent intentClose = new Intent(UserHomePage.this,
				CloseAccount.class);

		final Button transactionButton = (Button) findViewById(R.id.transactionHistory);
		final Button transferButton = (Button) findViewById(R.id.accountTransferButton);
		final Button closeButton = (Button) findViewById(R.id.closeAccountButton);

		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		} else {
			final User customer = userBundle.getParcelable("user");

			Bundle userBundleOut = new Bundle();
			userBundleOut.putParcelable("user", customer);

			intentCredDeb.putExtra("user", customer);
			intentTransfer.putExtra("user", customer);
			intentClose.putExtra("user", customer);

			transactionButton.setOnClickListener(new View.OnClickListener() {

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
					if (customer.getAccountCombo() == 0) {
						Toast.makeText(getApplicationContext(), "No Active Accounts!",
								Toast.LENGTH_SHORT).show();
					} else {
						startActivity(intentClose);
					}
				}
			});

			// customer.updateAccountList();
			// Log.d("hi", "hi");
			// Log.d("username", Integer.toString((int)
			// customer.getBalance(1)));

			accountTable = (TableLayout) findViewById(R.id.accountTable);
			row0 = new TableRow(this);
			type = new TextView(this);
			aNum = new TextView(this);
			bal = new TextView(this);
			type.setText("Type");
			aNum.setText("Account Number");
			aNum.setPadding(60, 0, 0, 0);
			bal.setText("Balance");
			bal.setPadding(60, 0, 0, 0);
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
					balance.setText(Double
							.toString(account.get(i).getBalance()));

					row.addView(accountType);
					row.addView(accountNumber);
					row.addView(balance);
					accountTable.addView(row);
				}
			}
		}

	}

	@Override
	public void onBackPressed() {
		// do nothing.
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
		if (id == R.id.action_logout) {
			Log.d("user", ParseUser.getCurrentUser().getObjectId());
			try {
				ParseUser.logOut();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// Log.d("user", ParseUser.getCurrentUser().getObjectId());
			if (ParseUser.getCurrentUser() == null) {
				Toast.makeText(getApplicationContext(), "Successful Logout!",
						Toast.LENGTH_SHORT).show();
				final Intent intentLogin = new Intent(UserHomePage.this,
						Login.class);
				startActivity(intentLogin);
			}
			return true;
		} else if (id == R.id.action_transactionHistory) {
			final Intent intentTransactionHistory = new Intent(
					UserHomePage.this, TransactionHistory.class);
			startActivity(intentTransactionHistory);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		return super.onMenuOpened(featureId, menu);
	}
}
