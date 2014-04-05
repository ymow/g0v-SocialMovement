package com.Social.Movement3;
 
import java.util.HashMap;

import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ReportsForeign extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.englishtranscript, container, false);
		//google analysis
		Tracker tracker = GoogleAnalytics.getInstance(getActivity()).getTracker("UA-49389941-1");

		HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(Fields.SCREEN_NAME, "Reports from Foreign");
		tracker.send(hitParameters);
		      WebView englishtransWebView=(WebView)rootView.findViewById(R.id.EnglishTransWebView);
			  WebSettings websettings = englishtransWebView.getSettings();  
		        websettings.setSupportZoom(true);  
		        websettings.setBuiltInZoomControls(false);   
//		        englishtransWebView = (ProgressWebView) rootView.findViewById(R.id.mWebView);
		        
		        websettings.setJavaScriptEnabled(true);  	         
		        englishtransWebView.setWebViewClient(new WebViewClient());  
//		        englishtransWebView.loadUrl("file:///android_asset/index.html");
		        englishtransWebView.loadUrl("http://bit.ly/PFnXeT");  
				 FragmentActivity ab = getActivity(); //needs  import android.app.ActionBar;
//				 ab.setTitle("My Title");
				 ab.setTitle("Reports from Foreign");
		        //getSupportActionBar().setTitle("Hello world App");  // provide compatibility to all the versions   
		        Log.v("ET WebView", "WebView OK");
		        FlurryAgent.logEvent("Reports from Foreign");
		        Tracker tracker2 = GoogleAnalytics.getInstance(getActivity()).getTracker("UA-49389941-1");

				HashMap<String, String> hitParameters2 = new HashMap<String, String>();
				hitParameters.put(Fields.SCREEN_NAME, "Happy Sunflower Map");
				tracker.send(hitParameters);
		return rootView;
		
	}
    public void onStart()
    {
       super.onStart();
       FlurryAgent.onStartSession(getActivity(), "XFSDYMVRWPS72Z595YZY");
       EasyTracker.getInstance(getActivity()).activityStart(getActivity());  // Add this method.
       // your code
    }
    public void onStop()
    {
       super.onStop();
       FlurryAgent.onEndSession(getActivity());
       EasyTracker.getInstance(getActivity()).activityStop(getActivity());  // Add this method.
       // your code
    }
}
