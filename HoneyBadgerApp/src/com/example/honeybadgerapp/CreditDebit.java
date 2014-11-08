package com.example.honeybadgerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CreditDebit extends Activity implements OnItemSelectedListener {
	private Spinner accountSpinner;
	private Spinner actionSpinner;
	private EditText amount_edit_text;
	private Button confirmButton;
	private Button cancelButton;
	private List<ParseObject> accountList = new ArrayList<ParseObject>();
	private List<String> accNum = new ArrayList<String>();
	private ArrayAdapter<String> accNumAdapt;
	private ArrayAdapter<String> actionAdapt;
	private String action;
	private String accountNumber;
	private double amount = 0.0;
	private ParseObject accountObject;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_debit);
		
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");
		
		final Intent intentSuccessfulLogin = new Intent(CreditDebit.this,
				SuccessfulLogin.class);
		final Intent intentCreditDebit = new Intent(CreditDebit.this,
				CreditDebit.class);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
		query.whereEqualTo("parent", ParseUser.getCurrentUser());
		try {
			accountList = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < accountList.size(); i++){
			accNum.add(accountList.get(i).getObjectId());
		}
		
		accountSpinner = (Spinner) findViewById(R.id.account_num);
		actionSpinner = (Spinner) findViewById(R.id.action);
		amount_edit_text = (EditText) findViewById(R.id.amount_cred);
		confirmButton = (Button) findViewById(R.id.conf_cred);
		cancelButton = (Button) findViewById(R.id.cancel_cred);
		
		accNumAdapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, accNum);
		accNumAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accountSpinner.setAdapter(accNumAdapt);
		
		/*ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.state_names,
						android.R.layout.simple_spinner_item);*/
		
		ArrayAdapter<CharSequence> actionAdapt = ArrayAdapter
				.createFromResource(this, R.array.cred_or_deb,
						android.R.layout.simple_spinner_item);
		actionAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		actionSpinner.setAdapter(actionAdapt);
		
		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accountNumber = accountSpinner.getSelectedItem().toString().trim();
				action = actionSpinner.getSelectedItem().toString().trim();
				amount = Double.parseDouble(amount_edit_text.getText()
						.toString().trim());
				
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
				// query.whereEqualTo("parent", ParseUser.getCurrentUser());
				try {
					accountObject = query.get(accountNumber);;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(action.equals("Debit")) {
					amount = -amount;
				}
				double num = accountObject.getInt("balance");
				if((num + amount) < 0) {
					Toast.makeText(getApplicationContext(),
							"Need More Money", Toast.LENGTH_SHORT)
							.show();
					startActivity(intentCreditDebit);
				}
				
				Log.d("amount", Integer.toString((int)amount));
				
				accountObject.put("balance", accountObject.getInt("balance") + amount);
				try {
					accountObject.save();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				startActivity(intentSuccessfulLogin);
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentSuccessfulLogin);
			}
		});
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
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// state = stateSpinner.getSelectedItem().toString().trim();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}