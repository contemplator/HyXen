package com.ubikepoint;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;
import android.content.Intent;

public class UBikeList extends Activity {
	
	private static ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	//layout to use
	private ListView mListView;
//	simpleAdapter(activity, data, from, to)
	private SimpleAdapter mSimpleAdapter;
//	private ObjectTest stations;
//	private Stations stations;
	
	private String stations;
	private JSONArray data;
	
	private static final String[] FROM = new String[]{
		"name",
		"tot",
		"sus"
	};
	
	private static final int[] TO = new int[]{
		R.id.Text_Name,
		R.id.Text_Tot,
		R.id.Text_Sus
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubike_list);
		
		Intent it = getIntent();
		String stations = it.getStringExtra("stations");
		try {
			data = new JSONArray(stations);
			toHashMap();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		stations = new Stations(getApplicationContext());
//		result = stations.getStations();
		
		initViews();
	}
	
	private void initViews() {
		mListView = (ListView) findViewById(R.id.ListView_Test);
		mSimpleAdapter = new SimpleAdapter(this, result, R.layout.list_cell, FROM, TO);
		mListView.setAdapter(mSimpleAdapter);
	}
	
	private void toHashMap() throws JSONException{
		HashMap<String, String> station = new HashMap<String, String>();
		for(int i=0; i<data.length(); i++){
			JSONObject s = data.getJSONObject(i);
			station.put("name", s.getString("name"));
			station.put("tot", s.getString("tot"));
			station.put("sus", s.getString("sus"));
			result.add(station);
		}
	}

}
