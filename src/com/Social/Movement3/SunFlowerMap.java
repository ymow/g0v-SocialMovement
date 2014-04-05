package com.Social.Movement3;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
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
			Marker ly = map.addMarker(new MarkerOptions().position(lyLocation).title("立法院").snippet("Sunflower Movement"));
//			 map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			map.getUiSettings().setZoomControlsEnabled(true);

	        // Move the camera instantly to ly with a zoom of 17.
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lyLocation, 17));

	        LocationManager lm = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
	        Criteria criteria = new Criteria();
	        String  bestProvider = lm.getBestProvider(criteria, false);
	        Location location = lm.getLastKnownLocation(bestProvider);
	        
	        LatLng markerLoc=new LatLng(location.getLatitude(), location.getLongitude());
	        final CameraPosition cameraPosition = new CameraPosition.Builder()
	        .target(lyLocation)      // Sets the center of the map to Mountain View
	        .zoom(17)                   // Sets the zoom
	        .bearing(90)                // Sets the orientation of the camera to east
	        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
	        .build();                   //
//	        map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Me"));
	        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	        map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener(){
	        	@Override
	            public boolean onMyLocationButtonClick() {
	        		
//	        	    map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Me"));

	                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	                return true;
	            }
	        });
//		    map.addMarker(new MarkerOptions() .position(new LatLng(xxxxxx,xxxxxx)) .title("Current Location")
//		            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball_pointer))
//		            .snippet("xxxxx"));
	        Location LyLocation = new Location("ly");
//	        25.044187, 121.519457
	        LyLocation.setLatitude(25.044187);
	        LyLocation.setLongitude(121.519457);

	        float distance = location.distanceTo(LyLocation);

		 FragmentActivity ab = getActivity(); //needs  import android.app.ActionBar;
		 ab.setTitle(distance+" m");
		 
	//	dummyTextView.setText(Integer.toString(getArguments().getInt(
	//			ARG_PAGER_NUMBER)));
		return rootView;
		
	}
	


	

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		  Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
	        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	        ft.remove(fragment);
	        ft.commit();
 
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
