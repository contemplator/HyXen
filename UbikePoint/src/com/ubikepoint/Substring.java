package com.ubikepoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Substring extends Activity {

	//at NCCU, lat and lng are fixed
	private static String URL_UBIKE = "http://www.youbike.com.tw/ccccc.php?lat=25.037525&lng=121.56378199999995&radius=5&mode=0";
	//create a arraylist to store data. the type of data is hashmap. it is mean to use simpleadapter
	private static ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	//layout to use
	private ListView mListView;
	//simpleAdapter(activity, data, from, to)
	private SimpleAdapter mSimpleAdapter;
	
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
		setContentView(R.layout.activity_xpath_point);
		
		loadWeb(URL_UBIKE);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.ListView_Test);
		mSimpleAdapter = new SimpleAdapter(this, result, R.layout.list_cell, FROM, TO);
		mListView.setAdapter(mSimpleAdapter);
	}

	private void loadWeb(final String url) {
		// TODO Auto-generated method stub
		new Thread(){
			@Override
			public void run(){
				try{
					URL bikeweb = new URL(url);
					URLConnection yc = bikeweb.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())); //to get the resource of web
					//call the function to read the resource and classify the data
					ArrayList<HashMap<String, String>> data = readline(in);
					
				}catch(Exception e){

					e.printStackTrace();
				}
			}

			private ArrayList<HashMap<String, String>> readline(BufferedReader in) {
				// TODO Auto-generated method stub
				String inputline = "";
				try{
					in.readLine(); //ignore the first line
					while((inputline = in.readLine()).contains("address")){
						String[] data = getString(inputline); //call the function to split the data
						String[] column = getCol(); //call the function to create column to match the data
						HashMap<String, String> map = new HashMap<String, String>(); //fill the HashMap with column and data
						for(int i = 0; i<12; i++){
							map.put(column[i], data[i]);
						}
						result.add(map); //fill the ArrayList with HashMap
					}
					in.close(); //close the bufferedReader
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					
				}
				return result;
			}

			private String[] getCol() {
				// TODO Auto-generated method stub
				String[] column = {"name", "address", "nameen", "addressen", "lat", "lng", "tot", "sus", "distance", "mday", "icon_type", "qqq"};
				return column;
			}

			private String[] getString(String inputline) {
				// TODO Auto-generated method stub
				String[] piece = new String[12];
				int start = 0, end = 0;
				for (int i = 0; i<12 ; i++){
					if(i < 1){
						start = inputline.indexOf("\"");
						end = inputline.indexOf("\"", start + 1);
						if(start == -1) return null;
					}else{
						start = inputline.indexOf("\"", end + 1);
						end = inputline.indexOf("\"", start + 1);
					}
					piece[i] = inputline.substring(start+1, end);
				}
				return piece;
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xpath_point, menu);
		return true;
	}

}
