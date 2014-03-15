package com.bike;

import java.util.HashMap;

import org.json.JSONObject;

public class Station {
	
	private String name;
	private String address;
	private int tot;
	private int sus;
	private float lat;
	private float lng;
	
	Station(String n, String a, int t, int s, float la, float ln){
		this.name = n;
		this.address = a;
		this.tot = t;
		this.sus = s;
		this.lat = la;
		this.lng = ln;
	}
	
	public int getTot(){
		return tot;
	}
	
	public int getSus(){
		return sus;
	}
	
	public JSONObject getJsonObject(){
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("name", name);
		h.put("address", address);
		h.put("lat", Float.toString(lat));
		h.put("lng", Float.toString(lng));
		h.put("tot", Integer.toString(tot));
		h.put("sus", Integer.toString(sus));
		JSONObject j = new JSONObject(h);
		return j;
	}
}
