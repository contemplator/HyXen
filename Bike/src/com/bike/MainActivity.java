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

import org.json.JSONArray;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.R.integer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;

public class MainActivity extends Activity {

	private boolean network = true;
	private String stations;
	private ArrayList<Station> data = new ArrayList<Station>();
	private JSONArray dataJson = new JSONArray();
	
	private TextView test;
	
//	private Station tests= new Station("name", "address", 123, 123, 123, 123);
	private String tests = "Ssd";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		checkNetwork();
//		if(network == true){
			loadweb();
			getJsonArray();
//		}
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		test = (TextView) findViewById(R.id.test);
//		test.setText(data.toString());
		test.setText(tests);
	}

	private void checkNetwork() {
		// TODO Auto-generated method stub
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getNetworkInfo(1); //WIFI
		NetworkInfo mobNetInfo = conManager.getNetworkInfo(0); //3G
		if (networkInfo != null && networkInfo.isConnected()){
			new AlertDialog.Builder(MainActivity.this)
			.setMessage("有WIFI:")
			.show();
			network = true;
		}
		if (mobNetInfo != null && mobNetInfo.isConnected()){
			new AlertDialog.Builder(MainActivity.this)
			.setMessage("有3G:")
			.show();
			network = true;
		}
		else{
			new AlertDialog.Builder(MainActivity.this)
			.setMessage("沒網路")
			.show();
			network = false;
		}
	}

	private void loadweb() {
		// TODO Auto-generated method stub
		new Thread(){
			
			@Override
			public void run() {
				try{
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder builder = factory.newDocumentBuilder();
		            
		            URL bikeweb = new URL("http://www.youbike.com.tw/ccccc.php");
					URLConnection yc = bikeweb.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
					
					InputSource input = new InputSource(in);
					tests = in.toString();
//					while(in.readLine() != null){
//						tests += in.readLine();
//					}
					org.w3c.dom.Document doc = builder.parse(input);
					NodeList nodeList = doc.getElementsByTagName("marker");
					
					for(int i=0; i<nodeList.getLength(); i++){
						Node node = nodeList.item(i);
						
						Attr name = (Attr) node.getAttributes().getNamedItem("name");
						Attr address = (Attr) node.getAttributes().getNamedItem("address");
						Attr tot = (Attr) node.getAttributes().getNamedItem("tot");
						Attr sus = (Attr) node.getAttributes().getNamedItem("sus");
						Attr lat = (Attr) node.getAttributes().getNamedItem("lat");
						Attr lng = (Attr) node.getAttributes().getNamedItem("lng");
						String n = name.getValue();
						String a = address.getValue();
						int t = Integer.parseInt(tot.getValue());
						int s = Integer.parseInt(sus.getValue());
						float la = Float.parseFloat(lat.getValue());
						float ln = Float.parseFloat(lng.getValue());
						
						Station station = new Station(n, a, t, s, la, ln);
						data.add(station);
					}
					in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void getJsonArray(){
		for(int i=0; i<data.size(); i++){
			dataJson.put(data.get(i).getJsonObject());
		}
	}
}
