package com.bike;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Station {
	
	private String name;
	private String address;
	private int tot;
	private int sus;
	private float lat;
	private float lng;
	private String mDay;
	private int icon_type;
	
	Station(String n, String a, int t, int s, float la, float ln, String md, int it){
		this.name = n;
		this.address = a;
		this.tot = t;
		this.sus = s;
		this.lat = la;
		this.lng = ln;
		this.mDay = md;
		this.icon_type = it;
	}
	
	Station(JSONObject j) throws JSONException{
		this.name = j.getString("name");
		this.address = j.getString("address");
		this.tot = (Integer) j.getInt("tot");
		this.sus = (Integer) j.getInt("sus");
		this.lat = (float) j.getDouble("lat");
		this.lng = (float) j.getDouble("lng");
		this.mDay = j.getString("mDay");
		this.icon_type = j.getInt("icon_type");
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public int getTot(){
		return tot;
	}
	
	public int getSus(){
		return sus;
	}
	
	public float getLat(){
		return lat;
	}
	
	public float getLng(){
		return lng;
	}
	
	public String getMday(){
		return mDay;
	}
	
	public int getIconType(){
		return icon_type;
	}
	
	public JSONObject getJsonObject(){
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("name", name);
		h.put("address", address);
		h.put("lat", Float.toString(lat));
		h.put("lng", Float.toString(lng));
		h.put("tot", Integer.toString(tot));
		h.put("sus", Integer.toString(sus));
		h.put("mDay", mDay);
		h.put("icon_type", Integer.toString(icon_type));
		JSONObject j = new JSONObject(h);
		return j;
	}
}
