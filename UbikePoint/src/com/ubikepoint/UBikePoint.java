package com.ubikepoint;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;
import android.content.Intent;

public class UBikePoint extends Activity {
	
	private static ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	//layout to use
	private ListView mListView;
	//simpleAdapter(activity, data, from, to)
	private SimpleAdapter mSimpleAdapter;
//	private ObjectTest stations;
	private Stations stations;
	
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
		setContentView(R.layout.activity_ubike_point);
		
		stations = new Stations(getApplicationContext());
		result = stations.getStations();
		
		initViews();
	}
	
	private void initViews() {
		mListView = (ListView) findViewById(R.id.ListView_Test);
		mSimpleAdapter = new SimpleAdapter(this, result, R.layout.list_cell, FROM, TO);
		mListView.setAdapter(mSimpleAdapter);
	}

}
