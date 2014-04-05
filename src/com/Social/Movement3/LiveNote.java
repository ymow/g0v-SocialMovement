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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

public class LiveNote extends Fragment {
 	//public static final String ARG_PAGER_NUMBER = "page_number";
	   private String JSONString;
	   String feedUrl = "http://congress-text-live.herokuapp.com/json/";
	   ArrayList<String> feedUrl2 = new ArrayList<String>();
		
		ListView NoteList;
		ArrayList<String> noteArrayList = new ArrayList<String>();
		ArrayAdapter<String> noteAdapter;
		LiveNote context;
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.livenote, container, false);
//		  URL url = new URL("http://congress-text-live.herokuapp.com/json/");
		Log.d("d","on create");		
		//google analysis
		Tracker tracker = GoogleAnalytics.getInstance(getActivity()).getTracker("UA-49389941-1");

		HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(Fields.SCREEN_NAME, "LiveNote");
		tracker.send(hitParameters);
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;
		NoteList = (ListView) rootView.findViewById(R.id.LiveNoteList);
		noteAdapter = new ArrayAdapter<String>(getActivity(), R.layout.news_list_item, noteArrayList);
		NoteList.setAdapter(noteAdapter);
		
		NoteList.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
		    { 
		        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());


	            alert.setTitle("立法院現場直播");
 
	            final String  stockArr[] = feedUrl2.toArray(new String[position]);
	            final int pos = position;
//	            System.out.println(shareText);
	           System.out.println(position);
	           System.out.println((position-1)/2);

	           if(position%2==0)
	           {
	        	   position = position+ 2 ;
	           }

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
	                    		String yourShareText = "Pray for Taiwan, Build from http://g0v.toady， "+" Install this app " + playStoreLink;


	                  		 Intent intent = new Intent(Intent.ACTION_SEND);
//	                  	        intent.setComponent(new ComponentName("jp.naver.line.android",
//	                  	                "com.facebook.katana"));
	                  	        intent.setType("text/plain"); 
	                  	        intent.putExtra(Intent.EXTRA_SUBJECT, "跟我一起到g0v關注黑箱服貿協議");
	                  	        intent.putExtra(Intent.EXTRA_TEXT, stockArr[(pos-1)/2] + yourShareText);
	                  	      startActivity(intent);
	                  	   	   FlurryAgent.logEvent("Live Note Share");
 
	                  	
	                        }
	                        
	                    });
	            
		           Dialog d = alert.setMessage(stockArr[(position-1)/2]).create();
	            d.show();
	            
	            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	            lp.copyFrom(d.getWindow().getAttributes());
	            lp.width = WindowManager.LayoutParams.FILL_PARENT;
	            lp.height = WindowManager.LayoutParams.FILL_PARENT;
	            d.getWindow().setAttributes(lp);
//		    	Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_LONG).show();
		    }
		});
		   FlurryAgent.logEvent("Load text Live");

		final VideoListTask loaderTask = new VideoListTask();
		loaderTask.execute();

		 FragmentActivity ab = getActivity(); //needs  import android.app.ActionBar;
//		 ab.setTitle("My Title");
		 ab.setTitle("現場文字轉播");
		 
	//	dummyTextView.setText(Integer.toString(getArguments().getInt(
	//			ARG_PAGER_NUMBER)));
		return rootView;
		
	}
	
	public class VideoListTask extends AsyncTask<Void, Void, Void>{

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
				JSONArray latest = json.getJSONArray("latest");
//				String time = new JSONObject(jsonData).getString("time");
//				String location = new JSONObject(jsonData).getString("location");
//				String content = new JSONObject(jsonData).getString("content");
//				JSONArray time = latest.getJSONArray("time");
//				JSONArray location = latest.getJSONArray("location");
//				JSONArray content = latest.getJSONArray("content");
 
////				String mJsonText = EntityUtils.toString(responce.getEntity());
//				String mTitle = new JSONObject(new JSONObject(mJsonText).getString("latest")).getString("content");
//
//				Log.d("d",mTitle);			
				Log.d("d","before");			
				for(int i =0; i<latest.length(); i++)
				{
					JSONObject news = latest.getJSONObject(i);
					System.out.println(i);			
	
					Log.d("time",news.getString("time"));
//					Log.d("location",news.getString("location"));
//					Log.d("content",news.getString("content"));
//					String location = new JSONObject(jsonData).getString("location");
//					System.out.println(b.indexOf('a', 10));
//					int Time = news.getString("time").indexOf("\"", i);
					
					String Content = news.getString("content");
					String content1 = Content.replace("\"","");
					String content2 = content1.replace("[", "");
					String content3 = content2.replace("]", "");
//					TextView txt3 = null  ;
//					txt3.setText(content3);
//					txt3.setTextColor(color.pink);
					noteArrayList.add(news.getString("time").replace("\"", "")+" "+news.getString("location").replace("\"", ""));
					noteArrayList.add(content3);
					feedUrl2.add(content3);
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
			if (isCancelled()) {
				cancel(true);
			}
	    
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			noteAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
 
		}
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
//		    	noteAdapter.notifyDataSetChanged();
//		        handler.postDelayed( this, 60 * 1000 );
//				VideoListTask loaderTask = new VideoListTask();
//				loaderTask.execute();
//				Log.d("d","+1");
//		    }
//		}, 60 * 1000 );
		 
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
 
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
