package com.intentservice;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MyIntentService extends IntentService {
	
	public MyIntentService() {
        super("MyIntentService");
    }
 
    @Override
    protected void onHandleIntent(Intent intent) {
 
        Bundle data = intent.getExtras();
        String url = data.getString("url");
        String name = data.getString("name");
 
        HttpPost request = new HttpPost(url);
 
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
 
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = new DefaultHttpClient().execute(request);
 
            if (response.getStatusLine().getStatusCode() == 200) {
                // responseStr ºô­¶¦^À³¦r¦ê
                String responseStr = EntityUtils.toString(response.getEntity());
                Log.d("read", responseStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
