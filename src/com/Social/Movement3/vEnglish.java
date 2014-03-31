package com.Social.Movement3;
 
import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class vEnglish extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.webviewlive, container, false);
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	 
		      WebView englishtransWebView=(WebView)rootView.findViewById(R.id.webviewLive);
			  WebSettings websettings = englishtransWebView.getSettings();  
		        websettings.setSupportZoom(true);  
		        websettings.setBuiltInZoomControls(false);   
//		        englishtransWebView = (ProgressWebView) rootView.findViewById(R.id.mWebView);
		        websettings.setJavaScriptEnabled(true);  	         
		        englishtransWebView.setWebViewClient(new WebViewClient());  
//		        englishtransWebView.loadUrl("file:///android_asset/index.html");
		        englishtransWebView.loadUrl("http://www.ustream.tv/channel/17548030");  
		        
		        //getSupportActionBar().setTitle("Hello world App");  // provide compatibility to all the versions   
		        Log.v("ET WebView", "WebView OK");
		        FlurryAgent.logEvent("English Live Video");

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
