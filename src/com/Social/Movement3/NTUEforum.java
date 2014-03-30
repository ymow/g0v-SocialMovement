package com.Social.Movement3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.Session;

public class NTUEforum extends Fragment {
 	//public static final String ARG_PAGER_NUMBER = "page_number";
	   private String JSONString;
	   String feedUrl = "http://congress-text-live.herokuapp.com/json/";
		ListView NoteList;
		ArrayList<String> noteArrayList = new ArrayList<String>();
		ArrayAdapter<String> noteAdapter;
		NTUEforum context;
 
//		NTU-E String http://graph.facebook.com/155846434444584/feed?
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.livenote, container, false);
//		  URL url = new URL("http://congress-text-live.herokuapp.com/json/");
		Log.d("d","on create");			
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;
		NoteList = (ListView) rootView.findViewById(R.id.LiveNoteList);
		noteAdapter = new ArrayAdapter<String>(getActivity(), R.layout.news_list_item, noteArrayList);
		NoteList.setAdapter(noteAdapter);

		final VideoListTask loaderTask = new VideoListTask();
		loaderTask.execute();
		Session session = Session.getActiveSession();
		if (session != null && session.getState().isOpened()){
		     Log.i("sessionToken", session.getAccessToken());
		     Log.i("sessionTokenDueDate", session.getExpirationDate().toLocaleString());
		}else{
			 
		}

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
			noteAdapter.notifyDataSetChanged();

			super.onPostExecute(result);
		}
	}
	 private Session createSession() {
	        Session activeSession = Session.getActiveSession();
	        if (activeSession == null || activeSession.getState().isClosed()) {
	            activeSession = new Session.Builder(getActivity()).setApplicationId("@string/app_id").build();
	            Session.setActiveSession("676201322438987|kh45RIm5z8KT2K29d5itncGd5fg");
	        }
	        return activeSession;
	    }
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		final Handler handler = new Handler();
		handler.postDelayed( new Runnable() {

		    @Override
		    public void run() {
		    	noteAdapter.notifyDataSetChanged();
		        handler.postDelayed( this, 60 * 1000 );
				VideoListTask loaderTask = new VideoListTask();
				loaderTask.execute();
				Log.d("d","+1");
		    }
		}, 60 * 1000 );
	}	
}
