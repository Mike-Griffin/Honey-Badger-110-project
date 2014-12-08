package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.honeybadgerapi.Account;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TellerCustomerInfo extends ActionBarActivity {
	private List<Account> account = new ArrayList<Account>();
	private TableLayout accountTable;
	private TableRow row0;
	private TextView type;
	private TextView aNum;
	private TextView bal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_customer_info);
		
		final Intent intentClose = new Intent(TellerCustomerInfo.this, CloseAccount.class);
		final Intent intentTellerHomePage = new Intent (TellerCustomerInfo.this, TellerHomePage.class);
		final Intent intentTransfer = new Intent ( TellerCustomerInfo.this, AccountTransfer.class);
		final Intent intentCreditDebit = new Intent ( TellerCustomerInfo.this, CreditDebit.class );
		
		final Button closeButton = (Button) findViewById(R.id.tellerCloseAccountButton);
		final Button cancelButton = (Button) findViewById(R.id.tellerCancel);
		final Button transferButton = (Button) findViewById(R.id.tellerAccountTransferButton);
		final Button creditDebitButton = (Button) findViewById(R.id.tellerCreditDebitButton);
		
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		} 
		else {
			final User teller = userBundle.getParcelable("user");
			final User customer = teller.getCustomer();
			
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
					balance.setText("$" + String.format( "%.2f", account.get(i).getBalance()));
					//Double.toString(account.get(i).getBalance()));aa

					row.addView(accountType);
					row.addView(accountNumber);
					row.addView(balance);
					accountTable.addView(row);
				}
			}
		
			closeButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (customer.getAccountCombo() == 0) {
						Toast.makeText(getApplicationContext(), "No Active Accounts!",
								Toast.LENGTH_SHORT).show();
					} 
					else {
						Toast.makeText(getApplicationContext(), "Has Accounts!",
								Toast.LENGTH_SHORT).show();
						Bundle userBundleOut = new Bundle();
						userBundleOut.putParcelable("user", customer);
						intentClose.putExtra("user", customer);
						startActivity(intentClose);
					}
				}
			});
			
			transferButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					if (customer.getAccountCombo() == 0) {
						Toast.makeText(getApplicationContext(), "No Active Accounts!",
								Toast.LENGTH_SHORT).show();
					} 
					else {
						Toast.makeText(getApplicationContext(), "Has Accounts!",
								Toast.LENGTH_SHORT).show();
						Bundle userBundleOut = new Bundle();
						userBundleOut.putParcelable("user", teller);
						intentTransfer.putExtra("user", teller);
						startActivity(intentTransfer);
					}
				}
			});
			
			creditDebitButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					if (customer.getAccountCombo() == 0) {
						Toast.makeText(getApplicationContext(), "No Active Accounts!",
								Toast.LENGTH_SHORT).show();
					} 
					else {
						Toast.makeText(getApplicationContext(), "Has Accounts!",
								Toast.LENGTH_SHORT).show();
						Bundle userBundleOut = new Bundle();
						userBundleOut.putParcelable("user", teller);
						intentCreditDebit.putExtra("user", teller);
						startActivity( intentCreditDebit );
					}		
				}
			});
			cancelButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle userBundle = new Bundle();
					userBundle.putParcelable("user", teller);
					intentTellerHomePage.putExtra("user", teller);
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
