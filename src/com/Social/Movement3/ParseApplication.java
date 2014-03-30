package com.Social.Movement3;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.PushService;
public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "DZ29rJZ1UTIYLGHDKNLnYgreP5j05smAOfB2Hv59", "zUYERFkwzcWuOWWGki0Cw1VoajpJAJEfmx00jWaS");

		 ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		PushService.setDefaultPushCallback(this, MainActivity.class);   
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
	}
	

}
