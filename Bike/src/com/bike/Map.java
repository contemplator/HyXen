package com.bike;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends FragmentActivity implements OnMarkerClickListener, LocationListener{
	
	private String data;
	private ArrayList<Station> stations = new ArrayList<Station>();
	private TextView datetime;
	private Button list;
	private GoogleMap mMap;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        getData();
        initView();
        try {
			setUpMapIfNeeded();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        updata();
	}

	private void getData() {
		// TODO Auto-generated method stub
		Intent it = getIntent();
		data = it.getStringExtra("data");
		try {
			JSONArray ja = new JSONArray(data);
			for(int i=0; i<ja.length(); i++){
				Station s = new Station(ja.getJSONObject(i));
				stations.add(s);
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initView(){
		datetime = (TextView) findViewById(R.id.datetime);
		
		list = (Button) findViewById(R.id.list);
		list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Map.this, List.class);
				intent.putExtra("data", data);
				startActivity(intent); 
			}
		});
	}
	
	private void updata(){
		datetime.setText(stations.get(0).getMday());
	}
	
	private void setUpMapIfNeeded() throws JSONException {
		if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
        	mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
	}
	
	private void setUpMap() throws JSONException {
		//icon1 black stop work
		//icon2 green normal
		//icon3 orange have no car
		//icon4 red have no place
		mMap.setMyLocationEnabled(true);
//		mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
		mMap.setOnMarkerClickListener(this);
		for(int i=0; i<stations.size(); i++){
			Station s = stations.get(i);
			if(s.getIconType() == 1){
				mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getName())
						.snippet("剩餘車輛:" + s.getTot() + " " + "剩餘車位:" + s.getSus())
						.icon(BitmapDescriptorFactory.defaultMarker(120)));
			}
			else if(s.getIconType() == 2){
				mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getName())
						.snippet("剩餘車輛:" + s.getTot() + " " + "剩餘車位:" + s.getSus())
						.icon(BitmapDescriptorFactory.defaultMarker(60)));
			}
			else if(s.getIconType() == 3){
				mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getName())
						.snippet("剩餘車輛:" + s.getTot() + " " + "剩餘車位:" + s.getSus())
						.icon(BitmapDescriptorFactory.defaultMarker(15)));
			}
			else if(s.getIconType() == 4){
				mMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getName())
						.snippet("剩餘車輛:" + s.getTot() + " " + "剩餘車位:" + s.getSus())
						.icon(BitmapDescriptorFactory.defaultMarker(0)));
			}
		}
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Click on Marker", Toast.LENGTH_SHORT).show();
		return false;
	}
	
	@Override
    public void onLocationChanged(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.moveCamera(cameraUpdate);
    }

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
