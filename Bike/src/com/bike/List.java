package com.bike;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.R.array;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class List extends ListActivity {
	private ListView lview;
	private SimpleAdapter adapter;
	private String[] from = {"distance", "time", "name", "address", "tot", "sus"};
	private int[] to = {R.id.distance, R.id.time, R.id.name, R.id.address, R.id.tot, R.id.sus};
	private static ArrayList<HashMap<String, Object>> array_hashmap = new ArrayList<HashMap<String, Object>>();
	private String data;
	private JSONArray ja;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lview = getListView();
		getData();
		
		adapter = new SimpleAdapter(this, array_hashmap, R.layout.activity_list, from, to);
		
		lview.setAdapter(adapter);
	}

	private void getData() {
		Intent it = getIntent();
		data = it.getStringExtra("data");
		try {
			ja = new JSONArray(data);
			for(int i=0; i<ja.length(); i++){
				HashMap<String, Object> hm = new HashMap<String, Object>();
				Station s = new Station(ja.getJSONObject(i));
				hm.put("name", s.getName());
				hm.put("address", s.getAddress());
				hm.put("tot", s.getTot());
				hm.put("sus", s.getSus());
				hm.put("lat", s.getLat());
				hm.put("lng", s.getLng());
				hm.put("distance", "30");
				hm.put("time", "15");
				array_hashmap.add(hm);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
