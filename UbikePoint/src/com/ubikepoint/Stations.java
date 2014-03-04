package com.ubikepoint;

import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.Context;

public class Stations{
	
	private ArrayList<HashMap<String, String>> stations = new ArrayList<HashMap<String,String>>();
	private Context context;
	
	public Stations(Context con){
		super();
		this.context = con;
		getAssets("stations.xml");
	}

	private void getAssets(String filename) {
		// TODO Auto-generated method stub
		
		try {
			InputSource inputSrc = new InputSource(context.getResources().getAssets().open(filename));
			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "//marker";
			NodeList nodes = (NodeList)xpath.evaluate(expression, inputSrc, XPathConstants.NODESET);
			if(nodes != null && nodes.getLength() > 0) {
	            stations.clear();
	            int len = nodes.getLength();
	            for(int i = 0; i < len; ++i) {
	                // query value
	                Node node = nodes.item(i);
	                
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                	HashMap<String, String> data = new HashMap<String, String>();
                        Attr name = (Attr) node.getAttributes().getNamedItem("name");  
                        data.put("name", name.getValue());

                        Attr address = (Attr) node.getAttributes().getNamedItem("address");  
                        data.put("address", address.getValue());
                
                        Attr nameen = (Attr) node.getAttributes().getNamedItem("address");  
                        data.put("nameen", nameen.getValue());
                
	                    Attr addressen = (Attr) node.getAttributes().getNamedItem("address");  
	                    data.put("addressen", addressen.getValue());
	                    
	                    Attr lat = (Attr) node.getAttributes().getNamedItem("lat");  
	                    data.put("lat", lat.getValue());
	                    
	                    Attr lng = (Attr) node.getAttributes().getNamedItem("lng");  
	                    data.put("lng", lng.getValue());
	                    
	                    Attr tot = (Attr) node.getAttributes().getNamedItem("tot");  
	                    data.put("tot", tot.getValue());
	
	                    Attr sus = (Attr) node.getAttributes().getNamedItem("sus");  
	                    data.put("sus", sus.getValue());
	                    
	                    Attr distance = (Attr) node.getAttributes().getNamedItem("distance");  
	                    data.put("distance", distance.getValue());
	                    
	                    Attr mday = (Attr) node.getAttributes().getNamedItem("mday");  
	                    data.put("mday", mday.getValue());
	                    
	                    Attr icon_type = (Attr) node.getAttributes().getNamedItem("icon_type");  
	                    data.put("icon_type", icon_type.getValue());
	                    
	                    Attr qqq = (Attr) node.getAttributes().getNamedItem("qqq");  
	                    data.put("qqq", qqq.getValue());
	                    stations.add(data);
	                }
	            }
	        }
//            InputStream in = getResources().getAssets().open(filename);
//            int lenght = in.available();
//            byte[]  buffer = new byte[lenght];
//            in.read(buffer);  
//            result = EncodingUtils.getString(buffer, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
	
	public ArrayList getStations(){
		return stations;
	}
}
