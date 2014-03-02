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
            //獲取文件的字節數  
            int lenght = in.available();  
            //創建byte數組  
            byte[]  buffer = new byte[lenght];  
            //將文件中的數據讀到byte數組中  
            in.read(buffer);  
            result = EncodingUtils.getString(buffer, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
		return result;
	}
}
