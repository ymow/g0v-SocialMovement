package com.Social.Movement3;
 
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class About extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.about, container, false);
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		TextView GoogleLegalNotices = (TextView)rootView.findViewById(R.id.textView12);
//		googleservice.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getActivity()));
		GoogleLegalNotices.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            //DO you work here
		  AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());


          alert.setTitle("Google Legal Notices");
          alert.setNegativeButton("OK",
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int id) {
                      	FlurryAgent.logEvent("Live Note OK");
                      }
                  });
 
          
	           Dialog d = alert.setMessage(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getActivity())).create();
          d.show();
	        	}
	        });
		
          
		        FragmentActivity ab = getActivity(); //needs  import android.app.ActionBar;
				 ab.setTitle("So Appreciated");
				 Log.d("DEBUG", GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getActivity()));
		   
		return rootView;
		
	}
}
