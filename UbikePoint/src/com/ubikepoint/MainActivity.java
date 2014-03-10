package com.ubikepoint;

import java.util.ArrayList;
import java.util.HashMap;

//import com.adapter.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//at NCCU, lat and lng are fixed
	//private static String URL_UBIKE = "http://www.youbike.com.tw/ccccc.php?lat=25.037525&lng=121.56378199999995&radius=5&mode=0";
	//create a arraylist to store data. the type of data is hashmap. it is mean to use simpleadapter
	private static ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	
	private String stations = null;
	private Stations data;
	private Button ubike_list;
	private Button ubike_map;
	private Button ubike_json;
	private TextView text_test;
	
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sp = getSharedPreferences("stations", MODE_PRIVATE);
		editor = sp.edit();
		
		checkNetwork();
		
		readPreferences();
		if(stations == "no data"){
			data = new Stations(getApplicationContext());
			result = data.getStations();
			savePreferences();
			readPreferences();
		}
		initViews();
		
	}
	
	@SuppressWarnings("null")
	private void checkNetwork() {
		// TODO Auto-generated method stub
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getNetworkInfo(1); //WIFI
		NetworkInfo mobNetInfo = conManager.getNetworkInfo(0); //3G
		if (networkInfo != null && networkInfo.isConnected()){
			new AlertDialog.Builder(MainActivity.this)
			.setMessage("有WIFI:")
			.show();
		}
		if (mobNetInfo != null && mobNetInfo.isConnected()){
			new AlertDialog.Builder(MainActivity.this)
			.setMessage("有3G:")
			.show();
		}
		else{
			new AlertDialog.Builder(MainActivity.this)
			.setMessage("沒網路")
			.show();
		}
//		if (networkInfo == null || !networkInfo.isAvailable()){
//			new AlertDialog.Builder(MainActivity.this)
//			.setMessage("沒網路")
//			.show();
//		}else{
//			new AlertDialog.Builder(MainActivity.this)
//			.setMessage("有網路")
//			.show();
//		}
	}

	private void initViews() {
		ubike_list = (Button) findViewById(R.id.ubike_list);
		ubike_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeList.class);
				intent.putExtra("stations", stations);
				startActivity(intent);
			}
		});
		
		ubike_map = (Button) findViewById(R.id.ubike_map);
		ubike_map.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeMap.class);
				intent.putExtra("stations", stations);
				startActivity(intent);
			}
		});
		
		ubike_json = (Button) findViewById(R.id.ubike_json);
		ubike_json.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeJson.class);
				startActivity(intent);
			}
		});
		
		text_test = (TextView) findViewById(R.id.text_test);
		text_test.setText(stations);
	}
	
	private void savePreferences(){
		editor.putString("stations", data.getJson().toString());
		editor.commit();
		
		Toast.makeText(this, "Save ok", Toast.LENGTH_SHORT).show();
	}
	
	private void readPreferences(){
		stations = sp.getString("stations", "no data");
	}
	
}
