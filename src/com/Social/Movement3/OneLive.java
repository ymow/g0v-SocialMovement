


package com.Social.Movement3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.parse.ParseObject;

public class OneLive extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.englishtranscript, container, false);
		 
		      WebView englishtransWebView=(WebView)rootView.findViewById(R.id.EnglishTransWebView);
			  WebSettings websettings = englishtransWebView.getSettings();  
		        websettings.setSupportZoom(true);  
		        websettings.setBuiltInZoomControls(false);   
//		        englishtransWebView = (ProgressWebView) rootView.findViewById(R.id.mWebView);
		        
		        websettings.setJavaScriptEnabled(true);  	         
		        englishtransWebView.setWebViewClient(new WebViewClient());  
//		        englishtransWebView.loadUrl("file:///android_asset/index.html");
		        
		        //Parse Live Url
//		        ParseObject NewUriParse = new ParseObject("OneLive");
//		        String newUri = NewUriParse.getString("NewUri");
//		       Uri myUri = Uri.parse(newUri);
//		        Log.v("ET uri", newUri);
//		       System.out.println(myUri.toString());
//		        Uri LiveOneUrl = "https://www.youtube.com/watch?v=j7AA2EsI_vI#start=0:00;end=9917:04;cycles=-1;autoreplay=false;showoptions=false"
		        englishtransWebView.loadUrl("http://www.youtube.com/watch?v=6anMM8dwq18&feature=share");  
 
		        //getSupportActionBar().setTitle("Hello world App");  // provide compatibility to all the versions   
		        Log.v("ET WebView", "WebView OK");
		   
		return rootView;
		
	}
}
