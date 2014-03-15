package com.ubikepoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UBikeMap3 extends FragmentActivity{

	private GoogleMap mMap;
	
	private static ArrayList<Station> data = new ArrayList<Station>();
	
	private Button list;
	private Button update;
	private TextView datetime;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubike_map3);
        
        loadweb();
        initView();
        setUpMapIfNeeded();
	}
	
	private void initView() {
		
		list = (Button) findViewById(R.id.list);
		list.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
		
		update = (Button) findViewById(R.id.update);
		update.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
		
		datetime = (TextView) findViewById(R.id.datetime);
	}
	
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
						data.add(station);
					}
					in.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				datetime.post(new Runnable(){
					@Override
					public void run() {
						datetime.setText(data.get(0).getMday());
					}
				});
			}
		}.start();
	}
	
	private void setUpMapIfNeeded() {
		if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
        	mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
	}

	private void setUpMap() {
		// TODO Auto-generated method stub
		mMap.setMyLocationEnabled(true);
		
//		setMarker();
	}

//	private void setMarker() {
//		// TODO Auto-generated method stub
//		for(int i=0; i<data.size(); i++){
//			JSONObject j = new JSONObject(data.get(i));
//			
//		}
//	}
}
