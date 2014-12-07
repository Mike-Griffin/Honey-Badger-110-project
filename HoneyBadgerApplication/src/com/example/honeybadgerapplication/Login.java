package com.example.honeybadgerapplication;

import com.example.honeybadgerapi.UserFactory;
import com.example.honeybadgerapi.User;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {

	private String username;
	private String password;
	private EditText username_edit_text;
	private EditText password_edit_text;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
		Parse.initialize(this, "vqe8lK8eYQMNQoGS2e70O9RpbTLv5cektEfMFKiL",
				"ZGPv4cdFtApvYktTgRp5wIACsrihpUAJ7QFOTln2");

		final Intent intentSignUp = new Intent(Login.this, SignUp.class);
		final Intent intentForgotPassword = new Intent(Login.this, ResetPassword.class);
		final Button signUpButton = (Button) findViewById(R.id.signUp);
		final Button loginButton = (Button) findViewById(R.id.login);
		final Button forgotPasswordButton = (Button)findViewById(R.id.forgotPassword);
        
		username_edit_text = (EditText) findViewById(R.id.username);
		password_edit_text = (EditText) findViewById(R.id.password);
		
		
		signUpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(intentSignUp);
			}
		});
		
		forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(intentForgotPassword);
				
			}
		});
		
		loginButton.setOnClickListener(new View.OnClickListener(){		
			public void onClick(View v){
				username = username_edit_text.getText().toString().trim();
				password = password_edit_text.getText().toString().trim();
				
				int userType = 0;
				ParseObject parseUser = null;
				User user = null;
				
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.whereEqualTo("username", username);
				// query.whereEqualTo("password", password);
				UserFactory factory = new UserFactory();
				
				try {
					parseUser = query.getFirst();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Toast.makeText(
						getApplicationContext(),
						"No such user exist, please signup"
						+ e, Toast.LENGTH_SHORT)
						.show();
				}
				
				//Log.d("parse user address", parseUser.getString("address"));
				
				/*if(parseUser == null)
					Toast.makeText(
							getApplicationContext(),
							"No such user exist, please signup", Toast.LENGTH_SHORT).show();
				}
				else
				{*/
				if(parseUser != null) {
				userType = parseUser.getInt("userType");
				
				user = factory.makeUser(userType, username, password);
				
				if(user.getLoginStatus() == 0) {
					Toast.makeText(
							getApplicationContext(),
							"Login Failed!!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Intent nextPage = new Intent();
				Bundle userBundle = new Bundle();
				userBundle.putParcelable("user", user);
				
				
				if(userType == 1)
					nextPage.setClass(Login.this, UserHomePage.class);
				else if(userType == 2)
					nextPage.setClass(Login.this, TellerHomePage.class);
				
				nextPage.putExtra("user", user);
				startActivity(nextPage);
				}
				//}
				
			}
			
		});
    }


    @Override
    public void onBackPressed() {
        // do nothing.
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
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
}
