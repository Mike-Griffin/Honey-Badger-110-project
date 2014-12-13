package com.example.honeybadgerapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.honeybadgerapi.Account;
import com.example.honeybadgerapi.User;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
	//declaration of local variables 
	private List<Account> account = new ArrayList<Account>();
	private TableLayout accountTable;
	private TableRow row0;
	private TextView type;
	private TextView aNum;
	private TextView bal;
	
	@Override
	//creates the customer bundle
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teller_customer_info);
		
		//the pages that will be shown
		final Intent intentClose = new Intent(TellerCustomerInfo.this, CloseAccount.class);
		final Intent intentTellerHomePage = new Intent (TellerCustomerInfo.this, TellerHomePage.class);
		final Intent intentTransfer = new Intent ( TellerCustomerInfo.this, AccountTransfer.class);
		final Intent intentCreditDebit = new Intent ( TellerCustomerInfo.this, CreditDebit.class );
		final Intent intentAddCustomerAccount = new Intent (TellerCustomerInfo.this, TellerAddAccounts.class);
		
		//links the button with id values
		final Button closeButton = (Button) findViewById(R.id.tellerCloseAccountButton);
		final Button cancelButton = (Button) findViewById(R.id.tellerCancel);
		final Button transferButton = (Button) findViewById(R.id.tellerAccountTransferButton);
		final Button creditDebitButton = (Button) findViewById(R.id.tellerCreditDebitButton);
		final Button addCustomerAccountButton = (Button) findViewById(R.id.open_customer_account);
		
		//customer bundle
		Bundle userBundle = this.getIntent().getExtras();
		if (userBundle == null) {
			Toast.makeText(getApplicationContext(), "Bundle does not exist",
					Toast.LENGTH_SHORT).show();
		} 
		else {
			final User teller = userBundle.getParcelable("user");
			final User customer = teller.getCustomer();
			//creates the table and formatting for fields
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
			//check if the customer exist if it does then by the switch statement the combos are selected
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
			//checks if the account status is true
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
			//customer account button when clicked
			addCustomerAccountButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Bundle userBundleOut = new Bundle();
					userBundleOut.putParcelable("user", teller);
					intentAddCustomerAccount.putExtra("user", teller);
					startActivity(intentAddCustomerAccount);
	
					
				}
			});
			//close button to close acccount the account
			closeButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//check if the account combo is zero
					if (customer.getAccountCombo() == 0) {
						Toast.makeText(getApplicationContext(), "No Active Accounts!",
								Toast.LENGTH_SHORT).show();
					} 
					else {
						Toast.makeText(getApplicationContext(), "Has Accounts!",
								Toast.LENGTH_SHORT).show();
						Bundle userBundleOut = new Bundle();
						userBundleOut.putParcelable("user", teller);
						intentClose.putExtra("user", teller);
						startActivity(intentClose);
					}
				}
			});
			//transfer button listener
			transferButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					//check to see if there are active accounts
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
			//debit button listener when the button is selected check if there are any active accounts
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
			//cancel button brings you back to the the teller home page
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
	// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
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
