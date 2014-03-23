package com.ubikepoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//import com.adapter.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*create the list to select function to test
We grab the data of ubike point from Official Website,stores the data in the ArrayList result, and save the data in SharePreference sp.
Before we new the stations data, we will read the sp.*/
public class MainActivity extends Activity {
	
	private static ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	
	private String stations = null;
	private Stations data;
	private Button ubike;
	private Button ubike_list;
	private Button ubike_map;
	private Button ubike_map2;
	private Button ubike_map3;
	private Button ubike_map4;
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
		
//		readPreferences();
//		if(stations == "no data"){
//			data = new Stations(getApplicationContext());
//			result = data.getStations();
//			savePreferences();
//			readPreferences();
//		}
		loadweb();
		
		initViews();
		
	}
	
	@SuppressWarnings("null")
	private void checkNetwork() {
		// TODO Auto-generated method stub
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getNetworkInfo(1); //WIFI
		NetworkInfo mobNetInfo = conManager.getNetworkInfo(0); //3G
		if (networkInfo != null && networkInfo.isConnected()){
			Toast.makeText(this, "有WIFI", Toast.LENGTH_SHORT).show();
		}
		if (mobNetInfo != null && mobNetInfo.isConnected()){
			Toast.makeText(this, "有3G", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "沒網路", Toast.LENGTH_SHORT).show();
		}
	}

	private void initViews() {
		
		ubike = (Button) findViewById(R.id.ubike);
		ubike.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBike.class);
				intent.putExtra("stations", stations);
				startActivity(intent);
			}
		});
		
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
		
		ubike_map2 = (Button) findViewById(R.id.ubike_map2);
		ubike_map2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeMap2.class);
				intent.putExtra("stations", stations);
				startActivity(intent);
			}
		});
		
		ubike_map3 = (Button) findViewById(R.id.ubike_map3);
		ubike_map3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeMap3.class);
				intent.putExtra("stations", stations);
				startActivity(intent);
			}
		});
		
		ubike_map4 = (Button) findViewById(R.id.ubike_map4);
		ubike_map4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, UBikeMap4.class);
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
//		text_test.setText(stations);
	}
	
	private void savePreferences(){
		editor.putString("stations", data.getJson().toString());
		editor.commit();
		
		Toast.makeText(this, "Save ok", Toast.LENGTH_SHORT).show();
	}
	
	private void readPreferences(){
		stations = sp.getString("stations", "no data");
	}
	
	private static ArrayList<Station> data2 = new ArrayList<Station>();
	private void loadweb() {
		// TODO Auto-generated method stub
		new Thread(){
			@Override
			public void run(){
				try{
					URL bikeweb = new URL("http://www.youbike.com.tw/ccccc.php");
					BufferedReader in = new BufferedReader(new InputStreamReader(bikeweb.openStream()));
					InputSource input = new InputSource(in);
					
					XPath xpath = XPathFactory.newInstance().newXPath();
					String expression = "//marker";
					NodeList nodeList = (NodeList)xpath.evaluate(expression, input, XPathConstants.NODESET);
					
					for(int i=0; i<nodeList.getLength(); i++){
						Node node = nodeList.item(i);
						
						Attr name = (Attr) node.getAttributes().getNamedItem("name");
						Attr address = (Attr) node.getAttributes().getNamedItem("address");
						Attr tot = (Attr) node.getAttributes().getNamedItem("tot");
						Attr sus = (Attr) node.getAttributes().getNamedItem("sus");
						Attr lat = (Attr) node.getAttributes().getNamedItem("lat");
						Attr lng = (Attr) node.getAttributes().getNamedItem("lng");
						Attr mDay = (Attr) node.getAttributes().getNamedItem("mday");
						String n = name.getValue();
						String a = address.getValue();
						int t = Integer.parseInt(tot.getValue());
						int s = Integer.parseInt(sus.getValue());
						float la = Float.parseFloat(lat.getValue());
						float ln = Float.parseFloat(lng.getValue());
						String m = mDay.getValue();
						Station station = new Station(n, a, t, s, la, ln, m);
						data2.add(station);
					}
					in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				text_test.post(new Runnable(){
					@Override
					public void run() {
						text_test.setText(data2.get(0).getMday());
						getString();
					}
				});
				
			}
		}.start();
	}
	
	private void getString(){
		new Thread(){
			@Override
			public void run(){
				JSONArray data3 = new JSONArray();
				for(int i=0; i<data2.size(); i++){
					data3.put(data2.get(i).getJsonObject());
				}
				stations = data3.toString();
				showText();
			}

			private void showText() {
				// TODO Auto-generated method stub
				text_test.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						text_test.setText(stations);
					}
				});
			}
		}.start();
	};
}
