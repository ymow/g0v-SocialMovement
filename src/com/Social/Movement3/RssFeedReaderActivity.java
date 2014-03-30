package com.Social.Movement3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.rssfeed.helper.ReverseOrder;
import com.rssfeed.helper.RssFeedStructure;
import com.rssfeed.helper.SortingOrder;
import com.rssfeed.helper.XmlHandler;

public class RssFeedReaderActivity extends Fragment {
    /** Called when the activity is first created. */
	
	ListView _rssFeedListView;
	List<JSONObject> jobs ;
	 List<RssFeedStructure> rssStr ;
	private RssReaderListAdapter _adapter;
	String sorti = "";
	String mode = "";
	 Button sort_Btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.rssfeedreaderactivity, container, false);
        
       _rssFeedListView = (ListView)rootView.findViewById(R.id.rssfeed_listview);
       sort_Btn = (Button)rootView.findViewById(R.id.sort);
       sort_Btn.setText("Change Sorting Mode");
       sort_Btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(sorti.equalsIgnoreCase("")){
				 sorti = "sort";
			}
			if(sorti.equalsIgnoreCase("sort")){
		     sorti = "sort";
		     sort_Btn.setText("Change Reverse Mode");
			 RssFeedTask rssTask = new RssFeedTask();
		     rssTask.execute();
			}
			else if(sorti.equalsIgnoreCase("reverse")){
				 sorti = "reverse";
				 sort_Btn.setText("Change Normal Mode");
				 RssFeedTask rssTask = new RssFeedTask();
			     rssTask.execute();
			}
			else if(sorti.equalsIgnoreCase("normal")){
				sort_Btn.setText("Change Sorting Mode");
				 RssFeedTask rssTask = new RssFeedTask();
			     rssTask.execute();
			}
		}
	});
       RssFeedTask rssTask = new RssFeedTask();
       rssTask.execute();
	   
	return rootView;
	
    }
    private class RssFeedTask extends AsyncTask<String, Void, String> {
		// private String Content;
		private ProgressDialog Dialog;
		String response = "";

		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(getActivity());
			Dialog.setMessage("Rss Loading...");
			Dialog.show();
		
		}

		@Override
		protected String doInBackground(String... urls) {
			  try {
				  //String feed = "http://feeds.nytimes.com/nyt/rss/HomePage";
				  
				  String feed = "https://www.facebook.com/feeds/page.php?id=1209955526&format=rss20";
				  XmlHandler rh = new XmlHandler();
				  rssStr = rh.getLatestArticles(feed);
				  Log.d("RSS Loading fail","RSS Loading");
			        } catch (Exception e) {
			        	Log.d("RSS Loading fail","RSS FAil");
			        }
			return response;

		}

		@Override
		protected void onPostExecute(String result) {
			  if(sorti.equalsIgnoreCase("sort")){
				  sorti = "reverse";
			     Collections.sort(rssStr, new SortingOrder());
			     
			  }else if(sorti.equalsIgnoreCase("reverse")){
				  sorti = "normal";
				  Comparator comp = Collections.reverseOrder();
				  Collections.sort(rssStr, new ReverseOrder());
			  }else{
				  sorti = "";
			  }
			  if(rssStr != null){
			    _adapter = new RssReaderListAdapter(getActivity(),rssStr);
		        _rssFeedListView.setAdapter(_adapter);
			  }
		        Dialog.dismiss();
		}
	}
  
}