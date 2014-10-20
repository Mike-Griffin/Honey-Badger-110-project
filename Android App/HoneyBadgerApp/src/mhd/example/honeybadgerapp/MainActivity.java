package mhd.example.honeybadgerapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	EditText userName;
	EditText userPassword;
	
	Button loginAccount;
	Button createAccount;
	Button forgotPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userName = (EditText)findViewById(R.id.input_username);
		userPassword = (EditText)findViewById(R.id.input_password);
		loginAccount = (Button)findViewById(R.id.login_button);
		createAccount = (Button)findViewById(R.id.create_account_button);
		forgotPassword = (Button)findViewById(R.id.forgot_password_button);
		
		loginAccount.setOnClickListener(this);
		createAccount.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name = userName.getText().toString();
		String password = userPassword.getText().toString();
		
		
		switch (v.getId()) {
		case R.id.login_button:
			if( name.equals("Chesong") && password.equals("Moonhead"))
			{
				Intent i = new Intent(this, SuccessfulLogin.class);
				startActivity(i);
			}
			break;
			
		case R.id.create_account_button:
			break;
		case R.id.forgot_password_button:
				userName.setText(" ");
				userPassword.setText(" ");
			break;
		}
	}
}
