/*AVDB Copyright*/
package com.Social.Movement3;

 

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends Activity {
protected boolean _active = true;
protected int _splashTime = 3000; // time to display the splash screen in ms
String STORE_NAME = "UserID"; 
String UserID = null;
 
private Long startTime;
private Handler handler = new Handler();

private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager 
          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.splash);
    Boolean b;
    b=isNetworkAvailable();  //true if connection,  false if not
 
     TextView textViewDay = (TextView) findViewById(R.id.splashtime);
    Time today = new Time(Time.getCurrentTimezone());
    today.setToNow();
    int intValuemonth = Integer.valueOf(today.month);
    int intValue = Integer.valueOf(today.monthDay);
    int intValue2 = Integer.valueOf(today.hour);
    int result = (intValue-1) *24 + intValue2 +350;
    String intresult = String.valueOf(result);
//    textViewDay.setText(intresult);             // Day of the month (1-31)
    Log.d("today",today.toString());
    Calendar c = Calendar.getInstance(); 
    int hourtoday = c.get(Calendar.HOUR);
    String totalresult = String.valueOf(result+hourtoday);
    textViewDay.setText(totalresult);             // Day of the month (1-31)
//    textViewDay =  (int)textViewDay-18+1;
//    textViewMonth.setText(today.month + "");              // Month (0-11)
    //固定要執行的方法
//    Runnable updateTimer1 = new Runnable() {
//        public void run() {
//            final TextView splashtime = (TextView) findViewById(R.id.splashtime);
//
//            
//            Long spentTime = System.currentTimeMillis() - startTime;
// 
//            //計算目前已過分鐘數
//            Long minius = (spentTime/1000)/60;
//            //計算目前已過秒數
//            Long seconds = (spentTime/1000) % 60;
//            splashtime.setText(minius+":"+seconds);
//            handler.postDelayed(this, 1000);
//        }
//    };

   if(!b){
       //do accordingly to no-connection
	     Log.d("no network","no network");
    	 Toast.makeText(Splash.this, "������������������������������������������", Toast.LENGTH_LONG).show();
//    	 System.exit(0);
    	   new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       Thread.sleep(5000);
                   }
                   catch (Exception e) { }
                   System.exit(0);
               }
           }).start();

		    
   }else{
	   
   
    SharedPreferences settings = getSharedPreferences("Preference", 1);
//		clear SharedPreferences @ /data/data/[package name]/shared_prefs/[preferences filename].xml
//    SharedPreferences.Editor editor = settings.edit();
//    editor.clear();
//    editor.commit();
    UserID = settings.getString("UserID", "");
    

	
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                int waited = 0;
	                while (_active && (waited < _splashTime)) {
	                    sleep(100);
	                    if (_active) {
	                        waited += 100;
	                    }
	                }
	            } catch (Exception e) {
	
	            } finally {
	            	  if(UserID == ""){
	            	        Log.d("No UserID, Sign up", UserID);
	            	        AlertDialog.Builder alert = new AlertDialog.Builder(Splash.this);
	            			
	            	        startActivity(new Intent(Splash.this,
	            	                MainActivity.class));
	            	        finish();


	            		    	
	            		}else {
	            			
			                startActivity(new Intent(Splash.this,
			                        MainActivity.class));
			                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			                finish();
			            		}
	            }
	        };
	             };
	        		 
	    splashTread.start();
		}

	}

}


//SKIP BTN
/*	    Button skipBtn = (Button)findViewById(R.id.button1);
	    skipBtn.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	 		   Intent newAct = new Intent();
	 		  newAct.setClass( Splash.this, MainActivity.class );
	            
	            // ???????????? Activity Class
	            startActivity( newAct );
	            
	            // ??????????????? Activity Class
	            Splash.this.finish();
	        }
	    });


/*FACEBOOK SIGN In
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.widget.LoginButton;
import com.parse.ParseFacebookUtils;

public class Splash extends Activity {
	   private SharedPreferences mPrefs;
	   Facebook facebook = new Facebook("442218349229015");
 
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);

    Button skipBtn = (Button)findViewById(R.id.button1);
    LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
 //   ParseFacebookUtils.initialize("442218349229015");
 
      authButton.setReadPermissions(Arrays.asList("basic_info","email"));
 
    authButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                loginToFacebook();
            }
    });
    skipBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
 		   Intent newAct = new Intent();
           newAct.setClass( Splash.this, MainActivity.class );
            }
    });
//    ParseFacebookUtils.logIn(this, new LogInCallback() {
//    	  @Override
//    	  public void done(ParseUser user, ParseException err) {
//    	    if (user == null) {
//    	      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
//    	    } else if (user.isNew()) {
//    	      Log.d("MyApp", "User signed up and logged in through Facebook!");
//    	    } else {
//    	      Log.d("MyApp", "User logged in through Facebook!");
//    	    }
//    	  }
//    	});
 
  }

  protected void loginToFacebook() {
	// TODO Auto-generated method stub
      mPrefs = getPreferences(MODE_PRIVATE);
	  String access_token = mPrefs.getString("access_token", null);
	  long expires = mPrefs.getLong("access_expires", 0);
	  if (access_token != null) {
		  facebook.setAccessToken(access_token);
	    }
	 
	    if (expires != 0) {
	        facebook.setAccessExpires(expires);
	    }
	 
	    if (!facebook.isSessionValid()) {
	        facebook.authorize(this,
	                new String[] { "email", "publish_stream" },
	                new DialogListener() {
	        	
	                    @Override
	                    public void onCancel() {
	                        // Function to handle cancel event
	                    }
	 
	                    @Override
	                    public void onComplete(Bundle values) {
	                        // Function to handle complete event
	                        // Edit Preferences and update facebook acess_token
	                        SharedPreferences.Editor editor = mPrefs.edit();
	                        editor.putString("access_token",
	                                facebook.getAccessToken());
	                        editor.putLong("access_expires",
	                                facebook.getAccessExpires());
	                        editor.commit();
	                    }

						@Override
						public void onFacebookError(FacebookError e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onError(DialogError e) {
							// TODO Auto-generated method stub
							
						}
	        });
	    }
  }
@Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
  }

}
  /*
  private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	  TextView userInfoTextView = (TextView) findViewById(R.id.welcome);

	    if (state.isOpened()) {
	      //  userInfoTextView.setVisibility(view.VISIBLE);
	    } else if (state.isClosed()) {
	       // userInfoTextView.setVisibility(View.INVISIBLE);
	    }
	}
  private String buildUserInfoDisplay(GraphUser user) {
	    StringBuilder userInfo = new StringBuilder("");

	    // Example: typed access (name)
	    // - no special permissions required
	    userInfo.append(String.format("Name: %s\n\n", 
	        user.getName()));

	    // Example: typed access (birthday)
	    // - requires user_birthday permission
	    userInfo.append(String.format("Birthday: %s\n\n", 
	        user.getBirthday()));

	    // Example: partially typed access, to location field,
	    // name key (location)
	    // - requires user_location permission
	    userInfo.append(String.format("Location: %s\n\n", 
	        user.getLocation().getProperty("name")));

	    // Example: access via property name (locale)
	    // - no special permissions required
	    userInfo.append(String.format("Locale: %s\n\n", 
	        user.getProperty("locale")));

	    // Example: access via key for array (languages) 
	    // - requires user_likes permission
	    JSONArray languages = (JSONArray)user.getProperty("languages");
	    if (languages.length() > 0) {
	        ArrayList<String> languageNames = new ArrayList<String> ();
	        for (int i=0; i < languages.length(); i++) {
	            JSONObject language = languages.optJSONObject(i);
	            // Add the language name to a list. Use JSON
	            // methods to get access to the name field. 
	            languageNames.add(language.optString("name"));
	        }           
	        userInfo.append(String.format("Languages: %s\n\n", 
	        languageNames.toString()));
	    }

	    return userInfo.toString();
	}
  
}
 

/*    authButton.setReadPermissions(Arrays.asList("basic_info"));
    authButton.setOnClickListener(new Button.OnClickListener(){ 

        @Override

        public void onClick(View v) {

            // TODO Auto-generated method stub

        	 // start Facebook Login
            Session.openActiveSession(this, true, new Session.StatusCallback() {

              // callback when session changes state
              @Override
              public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {

                  // make request to the /me API
                  Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                    // callback after Graph API response with user object
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                      if (user != null) {
                        TextView welcome = (TextView) findViewById(R.id.welcome);
                        welcome.setText("Hello " + user.getName() + "!");
                      }
                    }
                  });
                }
              }
            });      
        }         

    });     
*/