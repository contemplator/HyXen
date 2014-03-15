package com.ubikepoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class UBikeJson extends Activity {
	
	private boolean network = true;
	private String stations;
	private static ArrayList<Station> data = new ArrayList<Station>();
	private static JSONArray dataJson = new JSONArray();
	
	private TextView json_text;
	
//	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubike_json);
		
		checkNetwork();
		if(network == true){
			loadweb();
		}
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		json_text = (TextView) findViewById(R.id.json_text);
	}

	private void checkNetwork() {
		// TODO Auto-generated method stub
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getNetworkInfo(1); //WIFI
		NetworkInfo mobNetInfo = conManager.getNetworkInfo(0); //3G
		if (networkInfo != null && networkInfo.isConnected()){
			Toast.makeText(this, "有WIFI", Toast.LENGTH_SHORT).show();
			network = true;
		}
		if (mobNetInfo != null && mobNetInfo.isConnected()){
			Toast.makeText(this, "有3G", Toast.LENGTH_SHORT).show();
			network = true;
		}
		else{
			Toast.makeText(this, "沒網路", Toast.LENGTH_SHORT).show();
			network = false;
		}
	}

	private void loadweb() {
		// TODO Auto-generated method stub
		new Thread(){
			
			@Override
			public void run() {
				try{
//					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		            DocumentBuilder builder = factory.newDocumentBuilder();
		            
		            URL bikeweb = new URL("http://www.youbike.com.tw/ccccc.php");
//					URLConnection yc = bikeweb.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(bikeweb.openStream()));
					InputSource input = new InputSource(in);
					
					XPath xpath = XPathFactory.newInstance().newXPath();
					String expression = "//marker";
					NodeList nodeList = (NodeList)xpath.evaluate(expression, input, XPathConstants.NODESET);
//					while(in.readLine() != null){
//						tests += in.readLine();
//					}
//					org.w3c.dom.Document doc = builder.parse(input);
//					NodeList nodeList = doc.getElementsByTagName("marker");
					
					for(int i=0; i<nodeList.getLength(); i++){
//						Node node = nodes.item(i);
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
						data.add(station);
					}
					in.close();
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
//				mHandler.post(new Runnable() {
				json_text.post(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						getJsonArray();
						json_text.setText(dataJson.toString());
					}
				});
			}
		}.start();
	}
	
	private void getJsonArray(){
		for(int i=0; i<data.size(); i++){
			dataJson.put(data.get(i).getJsonObject());
		}
	}
}
