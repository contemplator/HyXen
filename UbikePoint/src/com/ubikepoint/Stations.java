package com.ubikepoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Stations {

//	private String[] name;
//	private String[] address;
//	private int[] lat;
//	private int[] lng;
//	private float[] distance;
//	private float[] tot;
//	private float[] sus;
	private ArrayList<HashMap<String, String>> stations = new ArrayList<HashMap<String,String>>();
	
	public Stations(){
		loadFile();
	}

	private void loadFile(){
		try {
            HashMap<String, String> data = new HashMap<String, String>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File("data/stations.xml");
            
            if (file.exists()) {
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();
 
                NodeList markerList = docEle.getElementsByTagName("marker");
                
                if (markerList != null && markerList.getLength() > 0) {
                    for (int i = 0; i < markerList.getLength(); i++) {
 
                        Node node = markerList.item(i);
 
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
 
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
                            
                        }
                        stations.add(data);
                    }
                }
                else {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public ArrayList getStations(){
		return stations;
	}
}
