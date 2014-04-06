package com.Social.Movement3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.Session;
import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

public class fbSunFlowerMovement extends Fragment {
 	//public static final String ARG_PAGER_NUMBER = "page_number";
	   private String JSONString;
	   String feedUrl = "https://www.facebook.com/feeds/page.php?format=json&id=319460198179142";

	   ArrayList<String> feedUrl2 = new ArrayList<String>();
		ListView NTUEforumList;
		ArrayList<String> NTUEforumArrayList = new ArrayList<String>();
		ArrayAdapter<String> NTUEforumAdapter;
		fbSunFlowerMovement context;
//		private Session.StatusCallback callback = new Session.StatusCallback() {
//		    @Override
//		    public void call(Session session, SessionState state, Exception exception) {
//		        onSessionStateChange(session, state, exception);
//		    }
//		};
//		NTU-E String http://graph.facebook.com/155846434444584/feed?
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.ntueforum, container, false);
//		  URL url = new URL("http://congress-text-live.herokuapp.com/json/");
		Log.d("d","on create");		
		//google analysis
		Tracker tracker = GoogleAnalytics.getInstance(getActivity()).getTracker("UA-49389941-1");

		HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(Fields.SCREEN_NAME, "NTU Law 318");
		tracker.send(hitParameters);


//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;
		NTUEforumList = (ListView) rootView.findViewById(R.id.NTUeforumlist);
		NTUEforumAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fb_list_item_en, NTUEforumArrayList);
		NTUEforumList.setAdapter(NTUEforumAdapter);
			
		NTUEforumList.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
		    { 
		        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());


	            alert.setTitle("Sunflower Movement 說");

	            WebView wv = new WebView(getActivity());
	            wv.getSettings().setJavaScriptEnabled(true);
	            final String [] stockArr = feedUrl2.toArray(new String[position]);
	            final int pos = position;
	           System.out.println(position);
	           System.out.println((position-1)/2);
	           
	           if(position%2==0)
	           {
	        	   position = position+ 2 ;
	           }
	            wv.loadUrl(stockArr[(position-1)/2]);
	            wv.setWebViewClient(new WebViewClient() {
	                @Override
	                public boolean shouldOverrideUrlLoading(WebView view,
	                        String url) {
	                    view.loadUrl(url);

	                    return true;
	                }
	            });

	            alert.setNegativeButton("Close",
	                    new DialogInterface.OnClickListener() {
	                        @Override
	                        public void onClick(DialogInterface dialog, int id) {
	                        	FlurryAgent.logEvent("Live Note Close");
	                        }
	                    });
	            alert.setPositiveButton("Share",
	                    new DialogInterface.OnClickListener() {
	                        @Override
	                        public void onClick(DialogInterface dialog, int id) {
	                      	  String playStoreLink = "https://play.google.com/store/apps/details?id=" +"com.Social.Movement3";
	                    		String yourShareText = "  Pray for Taiwan, Build from http://g0v.toady， "+" Install this app " + playStoreLink;


	                  		 Intent intent = new Intent(Intent.ACTION_SEND);
//	                  	        intent.setComponent(new ComponentName("jp.naver.line.android",
//	                  	                "com.facebook.katana"));
	                  	        intent.setType("text/plain"); 
	                  	        intent.putExtra(Intent.EXTRA_SUBJECT, "跟我一起到g0v關注黑箱服貿協議");
	                  	        intent.putExtra(Intent.EXTRA_TEXT, stockArr[(pos-1)/2] + yourShareText);
	                  	      startActivity(intent);
	                  	   	   FlurryAgent.logEvent("NTULaw318 FanPage Share");
 
	                  	
	                        }
	                        
	                    });
	            
	            Dialog d = alert.setView(wv).create();
	            d.show();
	            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	            lp.copyFrom(d.getWindow().getAttributes());
	            lp.width = WindowManager.LayoutParams.FILL_PARENT;
	            lp.height = WindowManager.LayoutParams.FILL_PARENT;
	            d.getWindow().setAttributes(lp);
		    	Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_LONG).show();
		    }
		});
		NTUeTask loaderTask = new NTUeTask();
		loaderTask.execute();
		   FlurryAgent.logEvent("fbSunFlowerMovement");
		   Tracker tracker2 = GoogleAnalytics.getInstance(getActivity()).getTracker("UA-49389941-1");

			HashMap<String, String> hitParameters2 = new HashMap<String, String>();
			hitParameters.put(Fields.SCREEN_NAME, "fbSunFlower Movement");
			tracker.send(hitParameters);
		 FragmentActivity ab = getActivity(); //needs  import android.app.ActionBar;
//		 ab.setTitle("My Title");
		 ab.setTitle("Sunflower Movement 粉絲團");
		 
	//	dummyTextView.setText(Integer.toString(getArguments().getInt(
	//			ARG_PAGER_NUMBER)));
		return rootView;
		
	}
	
	public class NTUeTask extends AsyncTask<Void, Void, Void>{

		ProgressDialog dialog;
		@Override
		protected void onPreExecute() 
		{
//			Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
			dialog = new ProgressDialog(getActivity());
			dialog.setTitle("Loading ..");
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(feedUrl);
			Log.d("d","httpClient");	
			try 
			{
				HttpResponse responce = client.execute(getRequest);
				StatusLine statusLine = responce.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				
				if(statusCode != 200)
				{
					return null;
				}
				
				InputStream jsonStream = responce.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
				StringBuilder builder = new StringBuilder();
				String line;

				while((line = reader.readLine())!=null)
				{
					builder.append(line);
				}
				
				String jsonData = builder.toString();
//				JSONArray latest = null;
				JSONObject json = new JSONObject(jsonData);
				JSONArray entries = json.getJSONArray("entries");	 
//				JSONArray names = json.getJSONArray("author");	 
//				String NAME = new JSONObject(jsonData).getString("author"); 
//				String time = new JSONObject(jsonData).getString("time");
//				String location = new JSONObject(jsonData).getString("location");
//				String content = new JSONObject(jsonData).getString("content");
//				JSONArray time = latest.getJSONArray("time");
//				JSONArray location = latest.getJSONArray("location");
//				JSONArray content = latest.getJSONArray("content");
				 
////				String mJsonText = EntityUtils.toString(responce.getEntity());
//				String mTitle = new JSONObject(new JSONObject(mJsonText).getString("latest")).getString("content");

//				String Name = new JSONObject(new JSONObject(jsonData).getString("author")).getString("name");
//				Log.d("NAME",NAME);
//				Log.d("NAME",Name);
				Log.d("d","before");			
				for(int i =0; i<entries.length(); i++)
				{
					JSONObject news = entries.getJSONObject(i);

//					String Author = news.getString("author");
//					System.out.println(Author);		
//					JSONObject NAMES  = new JSONObject(Author);
//					String Name = NAMES.getString("name");
// 					System.out.println(Name);	
//					JSONArray Name = json.getJSONArray("author");	 
//					for(int k =0; k<Name.length(); k++)
//					{
//						JSONObject NAME = Name.getJSONObject(k);
//						String names = NAME.getString("name");
//						System.out.println(names);			
//					}
					
//					Log.d("time",news.getString("time"));
//					Log.d("location",news.getString("location"));
//					Log.d("content",news.getString("content"));
//					String location = new JSONObject(jsonData).getString("location");
//					System.out.println(b.indexOf('a', 10));
//					int Time = news.getString("time").indexOf("\"", i);
					String Time = news.getString("published");
					String time1 = Time.replace("T"," ");
					 int index1 = time1.indexOf("+");
					 Log.d("site",Integer.toString(index1));
					 String time2 = time1.substring(0, index1);
 
					 
//						System.out.println(names);		
					final String Content = news.getString("content");
					String content1 = Content.replace("\"","");
					String content2 = content1.replace("[", "");
					String content3 = content2.replace("]", "");
					String content4 = content3.replace("<br />", "");
					String content5 = content4.replace("<br/>", "");
					String content6 = content5.replace("&quot;", "\"");
					String content7 = content6.replace("&#39;", "'");
					Log.d("site",content7);	
					 int index = content7.indexOf("<a");
					 int indexEnd = content7.indexOf("</a>");
					final String referencesite = news.getString("alternate");
					feedUrl2.add(referencesite);
						Log.d("referencesite",referencesite);	
					 String idx = Integer.toString(index);
					 if(index > 0){
						 final String content8 = content7.substring(0,index-2);
						 NTUEforumArrayList.add(time2);
							NTUEforumArrayList.add(content8 + "...");

					 }else{
						 NTUEforumArrayList.add(time2);
							NTUEforumArrayList.add(content7);
					 }
//					String site = content5.substring(idx,idx+1500);		
//					String content6 = content5.replace(site, "");
//					TextView txt3 = null  ;
//					txt3.setText(content3);
//					txt3.setTextColor(color.pink);


				}
				
				
			}
			catch (ClientProtocolException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			NTUEforumAdapter.notifyDataSetChanged();

			super.onPostExecute(result);
		}
	}
	 private Session createSession() {
	        Session activeSession = Session.getActiveSession();
	        if (activeSession == null || activeSession.getState().isClosed()) {
	            activeSession = new Session.Builder(getActivity()).setApplicationId("@string/app_id").build();
	            Session.setActiveSession(activeSession);
	        }
	        return activeSession;
	    }
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		final Handler handler = new Handler();
//		handler.postDelayed( new Runnable() {
//
//		    @Override
//		    public void run() {
//		    	NTUEforumAdapter.notifyDataSetChanged();
//		        handler.postDelayed( this, 60 * 1000 );
//		        NTUeTask loaderTask = new NTUeTask();
//				loaderTask.execute();
//				Log.d("d","+1");
//		    }
//		}, 60 * 1000 );
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
