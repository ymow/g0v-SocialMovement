package com.Social.Movement3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;

public class NTUEforum extends Fragment {
 	//public static final String ARG_PAGER_NUMBER = "page_number";
	   private String JSONString;
	   String feedUrl = "https://www.facebook.com/feeds/page.php?format=json&id=155846434444584";
		ListView NTUEforumList;
		ArrayList<String> NTUEforumArrayList = new ArrayList<String>();
		ArrayAdapter<String> NTUEforumAdapter;
		NTUEforum context;
		private Session.StatusCallback callback = new Session.StatusCallback() {
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		        onSessionStateChange(session, state, exception);
		    }
		};
//		NTU-E String http://graph.facebook.com/155846434444584/feed?
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.ntueforum, container, false);
//		  URL url = new URL("http://congress-text-live.herokuapp.com/json/");
		Log.d("d","on create");			
//		try {
//		    PackageInfo info = getActivity().getPackageManager().getPackageInfo(
//		          "com.Social.Movement3", PackageManager.GET_SIGNATURES);
//		    for (Signature signature : info.signatures){
//		           MessageDigest md = MessageDigest.getInstance("SHA");
//		           md.update(signature.toByteArray());
//		           Log.d("ymowkey:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//		    }
//		} catch (NameNotFoundException e) {
//		} catch (NoSuchAlgorithmException e) {
//		}

//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;
		NTUEforumList = (ListView) rootView.findViewById(R.id.NTUeforumlist);
		NTUEforumAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fb_list_item, NTUEforumArrayList);
		NTUEforumList.setAdapter(NTUEforumAdapter);

		final NTUeTask loaderTask = new NTUeTask();
		loaderTask.execute();

		 FragmentActivity ab = getActivity(); //needs  import android.app.ActionBar;
//		 ab.setTitle("My Title");
		 ab.setTitle("NTU-EForum");
		 
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
				for(int i =0; i<entries.length(); i++)
				{
					JSONObject news = entries.getJSONObject(i);
					System.out.println(i);			
	
//					Log.d("time",news.getString("time"));
//					Log.d("location",news.getString("location"));
//					Log.d("content",news.getString("content"));
//					String location = new JSONObject(jsonData).getString("location");
//					System.out.println(b.indexOf('a', 10));
//					int Time = news.getString("time").indexOf("\"", i);
					String Content = news.getString("content");
					String content1 = Content.replace("\"","");
					String content2 = content1.replace("[", "");
					String content3 = content2.replace("]", "");
					String content4 = content3.replace("<br />", "");
					String content5 = content4.replace("<br/>", "");
//					TextView txt3 = null  ;
//					txt3.setText(content3);
//					txt3.setTextColor(color.pink);
					NTUEforumArrayList.add(news.getString("published").replace("\"", ""));
					NTUEforumArrayList.add(content5);
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    Session.getActiveSession().onActivityResult(getActivity(), requestCode,
	            resultCode, data);
	}
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		  if (state.isOpened()) {
		        Log.i("TAG", "Logged in...");
		        Log.e("sessionopened", "true");

	            
		    } else if (state.isClosed()) {
		        Log.i("TAG", "Logged out...");
		        Log.d("TAG", session.toString());
		         session = Session.getActiveSession();
		        Request request = Request.newGraphPathRequest(session, "155846434444584", null);
		        com.facebook.Response response = Request.executeAndWait(request);
//		        session = 676201322438987|kh45RIm5z8KT2K29d5itncGd5fg;
		    }
		    else
		        Log.i("TAG", session.toString());
		
	}
}
