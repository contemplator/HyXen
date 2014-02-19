package com.bufferedreader;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader;

public class MainActivity extends Activity {

	private TextView mTextView;
	
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		Thread t = new Thread()
		{
			@Override
			public void run()
			{
				read();
				super.run();
			}
		};
		t.start();
	}

	private void initViews()
	{
		// TODO Auto-generated method stub

		mTextView = (TextView) findViewById(R.id.TextView_Test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void read(){
		try{
			URL url = new URL("http://twtraffic.tra.gov.tw/twrail/mobile/TrainDetail.aspx?searchdate=2014/02/12&traincode=51");
			URLConnection yc = url.openConnection();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			final StringBuilder sb = new StringBuilder();
			while ((inputLine = buffer.readLine()) != null) {
				sb.append(inputLine);
			}
			buffer.close();
			mHandler.post(new Runnable()
			{	
				@Override
				public void run()
				{
					mTextView.setText(sb.toString());
				}
			});
		} catch (IOException e){
			
		}
	}
}
