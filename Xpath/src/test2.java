import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;


public class test2 {
	public static void main(String[] args) {
        
        test2 parser = new test2();
        parser.getAllUserNames("http://www.youbike.com.tw/ccccc.php");
    }

	private void getAllUserNames(String url) {
		// TODO Auto-generated method stub
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            URL web = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(web.openStream()));
			
			InputSource input = new InputSource(in);
			org.w3c.dom.Document doc = builder.parse(input);
			NodeList nodeList = doc.getElementsByTagName("marker");
			
			for(int i=0; i<nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				
				Attr attr = (Attr) node.getAttributes().getNamedItem("sus");
				if (attr != null) {
                    String attribute= attr.getValue();                      
                    System.out.println("name: " + attribute);                      
                }
			}
//            Element docEle = db.parse(input).getDocumentElement();
            
            
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
