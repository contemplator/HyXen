package com.bike;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.R.integer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
	
	private Button map;
	private TextView test;
	private ArrayList<Station> stations = new ArrayList<Station>();
	private String data;
	
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		loadweb();
//		sp = getSharedPreferences("stations", MODE_PRIVATE);
//		editor = sp.edit();
//		readPreferences();
//		savePreferences();
	}

	private void initView() {
		map = (Button) findViewById(R.id.map);
		map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Map.class);
				intent.putExtra("data", data);
				startActivity(intent); 
			}
		});
		test = (TextView) findViewById(R.id.test);
	}

//	private void checkNetwork() {
//		// TODO Auto-generated method stub
//		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//		NetworkInfo networkInfo = conManager.getNetworkInfo(1); //WIFI
//		NetworkInfo mobNetInfo = conManager.getNetworkInfo(0); //3G
//		if (networkInfo != null && networkInfo.isConnected()){
//			new AlertDialog.Builder(MainActivity.this)
//			.setMessage("有WIFI:")
//			.show();
//			network = true;
//		}
//		if (mobNetInfo != null && mobNetInfo.isConnected()){
//			new AlertDialog.Builder(MainActivity.this)
//			.setMessage("有3G:")
//			.show();
//			network = true;
//		}
//		else{
//			new AlertDialog.Builder(MainActivity.this)
//			.setMessage("沒網路")
//			.show();
//			network = false;
//		}
//	}
//
	private void loadweb() {
		new Thread(){
			@Override
			public void run() {
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
						Attr icon_type = (Attr) node.getAttributes().getNamedItem("icon_type");
						String n = name.getValue();
						String a = address.getValue();
						int t = Integer.parseInt(tot.getValue());
						int s = Integer.parseInt(sus.getValue());
						float la = Float.parseFloat(lat.getValue());
						float ln = Float.parseFloat(lng.getValue());
						String m = mDay.getValue();
						int it = Integer.parseInt(icon_type.getValue());
						Station station = new Station(n, a, t, s, la, ln, m, it);
						stations.add(station);
					}
					in.close();
					JSONArray ja = new JSONArray();
					for (int i=0; i<stations.size(); i++){
						ja.put(stations.get(i).getJsonObject());
					}
					data = ja.toString();
					
					test.post(new Runnable() {
						@Override
						public void run() {
							test.setText(data.toString());
						}
					});
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void savePreferences(){
		editor.putString("stations", data);
		editor.commit();
		
		Toast.makeText(this, "Save ok", Toast.LENGTH_SHORT).show();
	}
	
	private void readPreferences(){
		data = sp.getString("stations", null);
		if(data == null){
			loadweb();
		}
	}
}
