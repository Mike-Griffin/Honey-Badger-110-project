package com.example.honeybadgerapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.honeybadgerapi.UserFactory;
import com.example.honeybadgerapi.User;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseObject;
import com.parse.PushService;

import android.app.Activity;
import android.app.ProgressDialog;
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

	private int numberOfAttempts = 0;
	private String username;
	private String password;
	private EditText username_edit_text;
	private EditText password_edit_text;
	ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	private long loginStatus = 0;
	
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
					numberOfAttempts++;
					
					Toast.makeText(
							getApplicationContext(),
							"Login Failed!!", Toast.LENGTH_SHORT).show();
					
					return;
				}
				
				final Intent nextPage = new Intent();
				Bundle userBundle = new Bundle();
				userBundle.putParcelable("user", user);
				
				if(userType == 1)
					nextPage.setClass(Login.this, UserHomePage.class);
				else if(userType == 2)
					nextPage.setClass(Login.this, TellerHomePage.class);
				
				nextPage.putExtra("user", user);
			
				
				progressBar = new ProgressDialog(v.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Securing Log in...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressBar.setProgress(0);
				progressBar.setMax(100);
				progressBar.show();
				
				progressBarStatus = 0;
				loginStatus = 0;
				
				new Thread(new Runnable() {
					public void run() {
						while(progressBarStatus < 100 ){
							progressBarStatus = progressBar();
							
							if(progressBarStatus < 100) {
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e){
								e.printStackTrace();
							}
							
							progressBarHandler.post(new Runnable() {
								public void run() {
									progressBar.setProgress(progressBarStatus);
								}
							});
						}
						
						if(progressBarStatus >= 100) {
							try {
								Thread.sleep(2000);
								startActivity(nextPage);
							} catch(InterruptedException e){
								e.printStackTrace();
							}
							progressBar.dismiss();
						}
					}
					
				}).start();
				
				}
				
			}
			
		});
    }
    
    public int progressBar() {
    	while (loginStatus <= 1000000) {
    		 
    		loginStatus++;
    		
 
			if (loginStatus == 100000) {
				return 10;
			} else if (loginStatus == 200000) {
				return 20;
			} else if (loginStatus == 300000) {
				return 30;
			} else if (loginStatus == 400000) {
				return 40;
			} else if (loginStatus == 500000) {
				return 50;
			} else if (loginStatus == 600000) {
				return 60;
			} else if (loginStatus == 700000) {
				return 70;
			} else if (loginStatus == 800000) {
				return 80;
			} else if (loginStatus == 900000) {
				return 90;
			} else {
				return 100;
			}
		}
		return 100;
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
