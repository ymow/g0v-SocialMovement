package com.Social.Movement3;

import java.util.HashMap;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.parse.ParseInstallation;
import com.parse.PushService;

 
public class MainActivity extends FragmentActivity {
	final String[] menuEntries = { "現場文字轉播","English Transcript","台大新聞E論壇","台大法律學生挺318",
			"English Live Stream","關於g0v"};
//	,"English Live","議場內 樓上", "議場內 樓上（Apple)",,"Sunflower Movement 太陽花學運"
//	"議場內 樓下（五六）","議場內 樓下（音地）","青島東 北側（g0v）","濟南路 機動（g0v）","濟南路 南測","議會外(Apple)"
	final String[] fragments = { "com.Social.Movement3.LiveNote","com.Social.Movement3.EnglishTranscript"
			,"com.Social.Movement3.NTUEforum","com.Social.Movement3.NTULaw318"
			,	"com.Social.Movement3.vEnglish","com.Social.Movement3.About"
			};
//	"com.Social.Movement3.vEnglish","com.Social.Movement3.vly2f","com.Social.Movement3.vly2fApple", 
//	"com.Social.Movement3.vly1f56","com.Social.Movement3.vly1fMusic","com.Social.Movement3.lyOutIslandNorthg0v",
//	"com.Social.Movement3.lyOutIsGSouthg0v","com.Social.Movement3.lyOutIsGSouth2","com.Social.Movement3.lyOutApple"
//	,"com.Social.Movement3.SunFlower"
	private ActionBarDrawerToggle drawerToggle;
	 private ShareActionProvider mShareActionProvider;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	 
		PushService.setDefaultPushCallback(this, MainActivity.class);
		// Save the current Installation to Parse.
		ParseInstallation.getCurrentInstallation().saveInBackground();
		//		google analysis
		Tracker tracker = GoogleAnalytics.getInstance(this).getTracker("UA-49389941-1");

		HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(Fields.SCREEN_NAME, "Home Screen");
		tracker.send(hitParameters);
		
		//		Flurry

		FlurryAgent.logEvent("Home Screen");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar()
				.getThemedContext(), android.R.layout.simple_list_item_1,
				menuEntries);
		final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		final ListView navList = (ListView) findViewById(R.id.left_drawer);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {

			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {

			}
		};
		drawer.setDrawerListener(drawerToggle);
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int pos, long id) {
				drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						FragmentTransaction tx = getSupportFragmentManager()
								.beginTransaction();
						tx.replace(R.id.content_frame, Fragment.instantiate(
								MainActivity.this, fragments[pos]));
						tx.commit();
					}
				});
				drawer.closeDrawer(navList);
			}
		});
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		//Screen Start
		 tx.replace(R.id.content_frame,Fragment.instantiate(MainActivity.this, fragments[0]));
		tx.commit();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			// This event will also be sent with &cd=Home%20Screen.
			Tracker tracker = GoogleAnalytics.getInstance(this).getTracker("UA-49389941-1");

			tracker.set(Fields.SCREEN_NAME, "Menu");
			tracker.send(MapBuilder
			    .createEvent("UX", "touch", "menuButton", null)
			    .build()
			);
			   FlurryAgent.logEvent("Menu");

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		 // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_share);

//	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_share).getActionProvider();
	    mShareActionProvider.setShareIntent(getDefaultShareIntent());

		return true;
	}

	private Intent getDefaultShareIntent() {
		// TODO Auto-generated method stub
		  String playStoreLink = "https://play.google.com/store/apps/details?id=" +
  		        getPackageName();
  		String yourShareText = "Pray for Taiwan, Build from http://g0v.toady， "+" Install this app " + playStoreLink;
  	   FlurryAgent.logEvent("Share");

		 Intent intent = new Intent(Intent.ACTION_SEND);
//	        intent.setComponent(new ComponentName("jp.naver.line.android",
//	                "com.facebook.katana"));
	        intent.setType("text/plain"); 
	        intent.putExtra(Intent.EXTRA_SUBJECT, "跟我一起到g0v關注黑箱服貿協議");
	        intent.putExtra(Intent.EXTRA_TEXT, yourShareText);
	        return intent;
	}

	// Call to update the share intent
	private void setShareIntent(Intent shareIntent) {
	    if (mShareActionProvider != null) {
	        mShareActionProvider.setShareIntent(shareIntent);
	    }
	}
//	@Override
//    public void onStart() {
//        super.onStart();
//        // The rest of your onStart() code.
//        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
//	}
//    @Override
//    public void onStop() {
//        super.onStop();
//        // The rest of your onStop() code.
//        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
//}  
    public void onStart()
    {
       super.onStart();
       FlurryAgent.onStartSession(this, "XFSDYMVRWPS72Z595YZY");
       EasyTracker.getInstance(this).activityStart(this);  // Add this method.
       // your code
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(this);
       EasyTracker.getInstance(this).activityStop(this);  // Add this method.
       // your code
    }

}