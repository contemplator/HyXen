package com.ubikepoint;

import java.util.ArrayList;
import java.util.HashMap;

//import com.adapter.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	
	//at NCCU, lat and lng are fixed
	//private static String URL_UBIKE = "http://www.youbike.com.tw/ccccc.php?lat=25.037525&lng=121.56378199999995&radius=5&mode=0";
	//create a arraylist to store data. the type of data is hashmap. it is mean to use simpleadapter
	private static ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	//layout to use
	private ListView mListView;
	//simpleAdapter(activity, data, from, to)
	private SimpleAdapter mSimpleAdapter;
//	private ObjectTest stations;
	private Stations stations;
	private Button ubike_list;
	private Button ubike_map;
	
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
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		stations = new ObjectTest();
//		stations.setStations();
		stations = new Stations(getApplicationContext());
		result = stations.getStations();
		
		initViews();
	}
	
	private void initViews() {
		ubike_list = (Button) findViewById(R.id.ubike_list);
		ubike_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeList.class);
				startActivity(intent);
			}
		});
		
		ubike_map = (Button) findViewById(R.id.ubike_map);
		ubike_map.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeMap.class);
				startActivity(intent);
			}
		});
	}
	
}
