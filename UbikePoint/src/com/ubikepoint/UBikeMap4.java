package com.ubikepoint;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

@SuppressLint("HandlerLeak")
public class UBikeMap4 extends FragmentActivity {
	
	private GoogleMap mMap;
	
	private static ArrayList<Station> data = new ArrayList<Station>();
	
	private Button list;
	private Button update;
	private TextView datetime2;
	private String str;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_map4);
        
        initView();
        setUpMapIfNeeded();
        getData();
        
	}
	
	private void getData() {
		Intent it = getIntent();
		str = it.getStringExtra("stations");
		new Thread(){
			@Override
			public void run(){
				try {
					JSONArray ja = new JSONArray(str);
					for(int i=0; i<ja.length(); i++){
						Station s = new Station(ja.getJSONObject(i));
						data.add(s);
						update();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}

	private void initView() {
		
		list = (Button) findViewById(R.id.list);
		list.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
		
		update = (Button) findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
		
		datetime2 = (TextView) findViewById(R.id.datetime);
//		datetime.setText(data.get(0).getMday());
	}
	
	private void setUpMapIfNeeded() {
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
	}

	private void update() {
		datetime2.post(new Runnable() {
			@Override
			public void run() {
				datetime2.setText(data.get(0).getMday());
			}
		});
	}
}

