package com.ubikepoint;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UBikeJson extends Activity {
	
	private TextView json_text;
	private JSONArray result;
	private Stations stations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubike_json);
		
		stations = new Stations(getApplicationContext());
		result = stations.getJson();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		json_text = (TextView) findViewById(R.id.json_text);
		json_text.setText(result.toString());
	}
	
	private void getStations(){
		ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> data = new HashMap<String, String>();
		int length = stations.getSize();
		for(int i=0 ; i<length ; i++){
			
		}
		
	}
}
