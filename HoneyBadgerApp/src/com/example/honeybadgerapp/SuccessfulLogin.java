package com.example.honeybadgerapp;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SuccessfulLogin extends Activity {



	private List<ParseObject> account = new ArrayList();
	private List<String> accType = new ArrayList();
	private List<String> accNum = new ArrayList();
	private List<Integer> accBal = new ArrayList();

	private ListView lv1;
	private ListView lv2;
	private ListView lv3;
	private ArrayAdapter<String> adapter1;

	private ArrayAdapter<String> adapter2;



	private ArrayAdapter<Integer> adapter3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_successful_login);

		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
		final Button creditDebitButton = (Button) findViewById(R.id.creditDebitButton);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		// query.whereEqualTo("parent", ParseUser.getCurrentUser());
		query.whereEqualTo("parent", ParseUser.getCurrentUser());

		query.whereEqualTo("parent", ParseUser.getCurrentUser());

		try {
			account = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * query.findInBackground(new FindCallback<ParseObject>() { public void
		 * done(List<ParseObject> accountList, ParseException e) { //
		 * commentList now contains the last ten comments, and the // "post" //
		 * field has been populated. For example: account = accountList;
		 * Log.d("dosjasodaisojdoisad", Integer.toString(account.size())); } });
		 */

		if (account == null) {
			Log.d("dosjasodaisojdoisad",
					"why the fuck is it not working... whyyy");
		}

		// Get account info in the three lists
		for (int i = 0; i < account.size(); i++) {
			accType.add(account.get(i).getString("type"));
			accNum.add(account.get(i).getObjectId());
			accBal.add(account.get(i).getInt("balance"));
		}
		
		lv1 = (ListView) findViewById(R.id.listView1);
		lv2 = (ListView) findViewById(R.id.listView2);
		lv3 = (ListView) findViewById(R.id.listView3);

		adapter1 = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.custom_list_item, accType);
		adapter2 = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.custom_list_item, accNum);
		adapter3 = new ArrayAdapter<Integer>(getApplicationContext(),
				R.layout.custom_list_item, accBal);
		
		lv1.setAdapter(adapter1);
		lv2.setAdapter(adapter2);
		lv3.setAdapter(adapter3);

		creditDebitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SuccessfulLogin.this,
						CreditDebit.class));
			}
		});}

		/*query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> accountList, ParseException e) {
				// commentList now contains the last ten comments, and the
				// "post"
				// field has been populated. For example:
				account = accountList;
				Log.d("dosjasodaisojdoisad", Integer.toString(account.size()));
>>>>>>> FETCH_HEAD
			}
		});*/
		

		/*
		 * ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		 * query.whereEqualTo("parent", ParseUser.getCurrentUser());
		 * query.findInBackground(new FindCallback<ParseObject>() { public void
		 * done(List<ParseObject> accountList, ParseException e) { //
		 * commentList now contains the last ten comments, and the // "post" //
		 * field has been populated. For example: account = accountList;
		 * Log.d("dosjasodaisojdoisad", Integer.toString(account.size())); } });
		 * 
		 * if(account == null) { Log.d("dosjasodaisojdoisad",
		 * "why the fuck is it not working... whyyy"); }
		 */

		/*
		 * for (ParseObject account_iterator : account) { // This does not
		 * require a network access. String accountType =
		 * account_iterator.getString("type"); String accountNumber =
		 * account_iterator.getObjectId(); int balance =
		 * account_iterator.getInt("balance");
		 * 
		 * RelativeLayout rl = (RelativeLayout)
		 * findViewById(R.id.relativeLayout);
		 * 
		 * int width = 90; int height = 60;
		 * 
		 * TextView textview = new TextView(this);
		 * textview.setText(accountNumber); textview.setId(2000 + 1);
		 * 
		 * if (true) { RelativeLayout.LayoutParams rlp2 = new
		 * RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.FILL_PARENT,
		 * RelativeLayout.LayoutParams.WRAP_CONTENT);
		 * rlp2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		 * rlp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		 * textview.setLayoutParams(rlp2); rl.addView(textview);
		 * RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(
		 * width, height); rlp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		 * rlp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); } else {
		 * RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(
		 * RelativeLayout.LayoutParams.FILL_PARENT,
		 * RelativeLayout.LayoutParams.WRAP_CONTENT);
		 * rlp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		 * textview.setLayoutParams(rlp2); rl.addView(textview);
		 * RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(
		 * width, height); rlp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); }
		 * 
		 * 
		 * 
		 * 
		 * // add text view TextView tv1 = new TextView(this);
		 * tv1.setText(accountType); TextView tv2 = new TextView(this);
		 * tv2.setText(accountNumber); TextView tv3 = new TextView(this);
		 * tv3.setText(balance); // ll.addView(tv1); // ll.addView(tv2); //
		 * ll.addView(tv3);
		 * 
		 * Log.d("account type", accountType); Log.d("account account number",
		 * accountNumber); Log.d("account balance", Integer.toString(balance));
		 * 
		 * }
		 */


		/*for (ParseObject account_iterator : account) {
			// This does not require a network access.
			String accountType = account_iterator.getString("type");
			String accountNumber = account_iterator.getObjectId();
			int balance = account_iterator.getInt("balance");

			RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);
			
	        int width = 90;
	        int height = 60;

	        TextView textview = new TextView(this);
	        textview.setText(accountNumber);
	        textview.setId(2000 + 1);

	        if (true) {
	            RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(
	                    RelativeLayout.LayoutParams.FILL_PARENT,
	                    RelativeLayout.LayoutParams.WRAP_CONTENT);
	            rlp2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	            rlp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	            textview.setLayoutParams(rlp2);
	            rl.addView(textview);
	            RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(
	                    width, height);
	            rlp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	            rlp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	        } else {
	            RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(
	                    RelativeLayout.LayoutParams.FILL_PARENT,
	                    RelativeLayout.LayoutParams.WRAP_CONTENT);
	            rlp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	            textview.setLayoutParams(rlp2);
	            rl.addView(textview);
	            RelativeLayout.LayoutParams rlp1 = new RelativeLayout.LayoutParams(
	                    width, height);
	            rlp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	        }
			
			
			
			
	        // add text view
	        TextView tv1 = new TextView(this);
	        tv1.setText(accountType);
	        TextView tv2 = new TextView(this);
	        tv2.setText(accountNumber);
	        TextView tv3 = new TextView(this);
	        tv3.setText(balance);
	        // ll.addView(tv1);
	        // ll.addView(tv2);
	        // ll.addView(tv3);

			Log.d("account type", accountType);
			Log.d("account account number", accountNumber);
			Log.d("account balance", Integer.toString(balance));
			
		}*/

		// Log.d("abcdefg", "asdasdasdsad");
	//}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.successful_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.add_account:
			// Do something
			final Intent intentCreateAccount = new Intent(SuccessfulLogin.this,
					CreateAccount.class);
			startActivity(intentCreateAccount);

			return true;
		case R.id.log_out:
			ParseUser.logOut();
			if (ParseUser.getCurrentUser() == null) {
				Toast.makeText(getApplicationContext(), "successfully logout!",
						Toast.LENGTH_SHORT).show();
				final Intent intentLogin = new Intent(SuccessfulLogin.this,
						MainActivity.class);
				startActivity(intentLogin);
			}

			// Logout
			// setContentView(R.layout.activity_main);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
