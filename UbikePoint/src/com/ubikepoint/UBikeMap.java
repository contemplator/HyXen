package com.ubikepoint;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class UBikeMap extends FragmentActivity {

	private GoogleMap mMap;
	private String stations;
	private JSONArray data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubike_map);
		
		Intent it = getIntent();
		stations = it.getStringExtra("stations");
		try {
			data = new JSONArray(stations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setUpMapIfNeeded();
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
	
	//when i change the minSdkVersion to 11, i can use the mapFragment. And i don't meet the problem of thread. 
	private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
        
//        FragmentManager fmanager = getSupportFragmentManager();
//        Fragment fragment = fmanager.findFragmentById(R.id.map);
//        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
//        mMap = supportmapfragment.getMap();
        
//        setUpMap();
    }
	
	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
		for (int i=0; i<data.length(); i++){
			HashMap<String, String> station = new HashMap<String, String>();
			try {
				JSONObject s = data.getJSONObject(i);
				float lat = Float.parseFloat(s.getString("lat"));
				float lng = Float.parseFloat(s.getString("lng"));
				if(Integer.parseInt(s.getString("tot")) <= 10){
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getString("name"))
							.snippet("剩餘車輛:" + s.getString("tot") + "\n" + "剩餘車位:" + s.getString("sus"))
							.icon(BitmapDescriptorFactory.defaultMarker(0)));
				}else if(Integer.parseInt(s.getString("tot")) <= 20 && Integer.parseInt(s.getString("tot")) > 10){
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getString("name"))
							.snippet("剩餘車輛:" + s.getString("tot") + "\n" + "剩餘車位:" + s.getString("sus"))
							.icon(BitmapDescriptorFactory.defaultMarker(50)));
				}else{
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getString("name"))
							.snippet("剩餘車輛:" + s.getString("tot") + "\n" + "剩餘車位:" + s.getString("sus"))
							.icon(BitmapDescriptorFactory.defaultMarker(100)));
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
    }

}
