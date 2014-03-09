package com.ubikepoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;

public class ObjectTest{

	private ArrayList<HashMap<String, String>> stations = new ArrayList<HashMap<String,String>>();
	
	ObjectTest(){
		super();
	}
	//test git hub
	public void setStations(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "捷運市政府站(3號出口)");
		data.put("tot", "30");
		data.put("sus", "20");
		stations.add(data);
	}
	
//	public void setStations(){
//		try {
//			InputSource inputSrc = new InputSource(getResources().openRawResource(R.id.stations));
//			XPath xpath = XPathFactory.newInstance().newXPath();
//			String expression = "//marker";
//			NodeList nodes = (NodeList)xpath.evaluate(expression, inputSrc, XPathConstants.NODESET);
//			if(nodes != null && nodes.getLength() > 0) {
//	            stations.clear();
//	            int len = nodes.getLength();
//	            for(int i = 0; i < len; ++i) {
//	                // query value
//	                Node node = nodes.item(i);
//	                
//	                if (node.getNodeType() == Node.ELEMENT_NODE) {}
//	            }
//	        }
//            InputStream in = getResources().getAssets().open(filename);
//            int lenght = in.available();
//            byte[]  buffer = new byte[lenght];
//            in.read(buffer);  
//            result = EncodingUtils.getString(buffer, "UTF-8");  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }
//	}
	
	public ArrayList getStations(){
		return stations;
	}
	
	

}
