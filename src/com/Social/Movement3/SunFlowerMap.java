package com.Social.Movement3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
public class SunFlowerMap extends Fragment {
 
	  private static final int REQUEST_CODE_FSQ_CONNECT = 200;
	  private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;
	    
 
    public static final String CLIENT_ID = "NX1D4CU0PWDDATA3DFPRDLURPI1CFHHBFFNMCRUXOXAM401G";
    public static final String CLIENT_SECRET = "ZUFXTM4LGBBY52G2DMCHLHEXGSWDTLFBRAFNMKDBMYIHBUK4";
//    static final LatLng BottleCapp = new LatLng(51.371986, 0.065593);
    static final LatLng lyLocation = new LatLng(25.044187, 121.519457);
    private GoogleMap map;
 
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
		View rootView = inflater.inflate(R.layout.sunflowermap, container, false);
//		 setUpMapIfNeeded(rootView);
		map =((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//		SupportMapFragment mapFrag=(SupportMapFragment)getFragmentManager().findFragmentById(R.id.map);
			Marker ly = map.addMarker(new MarkerOptions().position(lyLocation).title("立法院").snippet("太陽花之役"));
			map.getUiSettings().setZoomControlsEnabled(true);
	        // Move the camera instantly to NKUT with a zoom of 16.
//	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lyLocation, 16));
		 FragmentActivity ab = getActivity(); //needs  import android.app.ActionBar;
		 ab.setTitle("開心立法院");
		 
	//	dummyTextView.setText(Integer.toString(getArguments().getInt(
	//			ARG_PAGER_NUMBER)));
		return rootView;
		
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
