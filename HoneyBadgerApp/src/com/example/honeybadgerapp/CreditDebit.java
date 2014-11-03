package com.example.honeybadgerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CreditDebit extends Activity {
	private Spinner accounts;
	private Spinner actionSpinner;
	private EditText amount;
	private Button confirm;
	private Button cancel;
	private List<ParseObject> accountList = new ArrayList();
	private List<Integer> accNum = new ArrayList();
	private ArrayAdapter<Integer> accNumAdapt;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_debit);
		
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("parent", ParseUser.getCurrentUser());
		
		try {
			accountList = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < accountList.size(); i++){
			accNum.add(accountList.get(i).getInt("accountNumber"));
		}
		
		accounts = (Spinner) findViewById(R.id.account_num);
		actionSpinner = (Spinner) findViewById(R.id.action);
		amount = (EditText) findViewById(R.id.amount_cred);
		confirm = (Button) findViewById(R.id.conf_cred);
		cancel = (Button) findViewById(R.id.cancel_cred);
	
		accNumAdapt = new ArrayAdapter<Integer>(getApplicationContext(),
				android.R.layout.simple_spinner_item, accNum);
		
		accounts.setAdapter(accNumAdapt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.credit_debit, menu);
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
