package com.ubikepoint;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class TestAssets extends Activity {
	
	private TextView fa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_assets);
		
		fa = (TextView) findViewById(R.id.from_asset);
		fa.setText(getAssets("stations.xml"));
	}
	
	private String getAssets(String filename){
		String result ="";
		
		try {  
            InputStream in = getResources().getAssets().open(filename);  
            //�����󪺦r�`��  
            int lenght = in.available();  
            //�Ы�byte�Ʋ�  
            byte[]  buffer = new byte[lenght];  
            //�N��󤤪��ƾ�Ū��byte�Ʋդ�  
            in.read(buffer);  
            result = EncodingUtils.getString(buffer, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
		return result;
	}
}
