package com.adapter;

/**
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}**/


import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.MemoryHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String KEY_NAME = "name";
	private static final String KEY_NUMBER = "number";
	private static final String KEY_TIME = "time";
	private static final String KEY_DELAY = "delay";
	
	private static final String[] FROM = new String[]
	{
		KEY_NAME,
		KEY_NUMBER,
		KEY_TIME,
		KEY_DELAY
	};
	
	private static final int[] TO = new int[]
	{
		R.id.Text_Name,
		R.id.Text_Number,
		R.id.Text_Time,
		R.id.Text_Delay
	};
	
	private ListView mListView;
	private SimpleAdapter mSimpleAdapter;
	private ArrayList<HashMap<String, String>> mData = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		LIST = new String[]{};
		
		HashMap<String, String> row;
		
		for (int i = 0; i < 20; i++)
		{
			row = new HashMap<String, String>();
			row.put(KEY_NAME, "name" + i);
			row.put(KEY_NUMBER, "number" + i);
			row.put(KEY_TIME, "time" + i);
			row.put(KEY_DELAY, "delay" + i);
			mData.add(row);
		}
		
		initViews();
	}
	
	private void initViews() {
	  mListView = (ListView) findViewById(R.id.ListView_Test);
	  mSimpleAdapter = new SimpleAdapter(this, mData, R.layout.list_cell, FROM, TO);
	  mListView.setAdapter(mSimpleAdapter);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}