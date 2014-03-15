package com.ubikepoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
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
//implements OnMarkerClickListener,OnInfoWindowClickListener
public class UBikeMap2 extends FragmentActivity implements OnMarkerClickListener, LocationListener{
	
	private GoogleMap mMap;
	private String stations;
	private JSONArray data;
	private TextView topText;
	private Button update;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_map2);
        
        Intent it = getIntent();
		stations = it.getStringExtra("stations");
		try {
			data = new JSONArray(stations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		initView();
        setUpMapIfNeeded();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		topText = (TextView) findViewById(R.id.top_text);
		JSONObject s;
		try {
			s = data.getJSONObject(0);
			topText.setText(s.getString("mday"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		update = (Button) findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
	}

	@Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        
        Intent it = getIntent();
		stations = it.getStringExtra("stations");
		try {
			data = new JSONArray(stations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
        	mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
		
		mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
		mMap.setOnMarkerClickListener(this);
		
		final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
		for (int i=0; i<data.length(); i++){
			try {
				JSONObject s = data.getJSONObject(i);
				float lat = Float.parseFloat(s.getString("lat"));
				float lng = Float.parseFloat(s.getString("lng"));
				//icon_type 0:lack place 2:all 3:lack bike
				if(s.getString("icon_type") == "2"){
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getString("name"))
							.snippet("剩餘車輛:" + s.getString("tot") + "\n" + "剩餘車位:" + s.getString("sus") + s.getString("icon_type"))
							.icon(BitmapDescriptorFactory.defaultMarker(0)));
				}else if(s.getString("icon_type") == "1"){
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getString("name"))
							.snippet("剩餘車輛:" + s.getString("tot") + "\n" + "剩餘車位:" + s.getString("sus") + s.getString("icon_type"))
							.icon(BitmapDescriptorFactory.defaultMarker(33)));
				}else if(s.getString("icon_type") == "0"){
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getString("name"))
							.snippet("剩餘車輛:" + s.getString("tot") + "\n" + "剩餘車位:" + s.getString("sus") + s.getString("icon_type"))
							.icon(BitmapDescriptorFactory.defaultMarker(67)));
				}else{
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(s.getString("name"))
							.snippet("剩餘車輛:" + s.getString("tot") + "\n" + "剩餘車位:" + s.getString("sus") + s.getString("icon_type"))
							.icon(BitmapDescriptorFactory.defaultMarker(100)));
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
//	@Override
//	public void onInfoWindowClick(Marker arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean onMarkerClick(Marker arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	class CustomInfoWindowAdapter implements InfoWindowAdapter {

		private final View mWindow;
//        private final View mContents;
		
        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
//            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }
        
		@Override
		public View getInfoContents(Marker marker) {
//			render(marker, mWindow);
            return null;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			render(marker, mWindow);
            return mWindow;
		}
		
		private void render(Marker marker, View view) {
			((ImageView) view.findViewById(R.id.badge)).setImageResource(R.drawable.badge_nsw);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }
            
            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
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

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
