import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class test {
	public void getAllUserNames(String url) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            URL web = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(web.openStream()));
			
            if (in != null) {
            	InputSource input = new InputSource(in);
                Element docEle = db.parse(input).getDocumentElement();
 
                // Print root element of the document
                System.out.println("Root element of the document: "
                        + docEle.getNodeName()); // nodeName = markers -> root node
 
                NodeList studentList = docEle.getElementsByTagName("marker"); // get the all second level node 
 
                // Print total student elements in document
                System.out
                        .println("Total students: " + studentList.getLength());
 
                if (studentList != null && studentList.getLength() > 0) {
                    for (int i = 0; i < studentList.getLength(); i++) {
 
                        Node node = studentList.item(i);
 
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
 
                            System.out
                                    .println("=====================");
 
                            Element e = (Element) node;
                            NodeList nodeList = e.getElementsByTagName("name");
//                            System.out.println("Name: "
//                                    + nodeList.item(0).getChildNodes().item(0)
//                                            .getNodeValue());
                            
                            System.out.println("Name: "
                                    + ((Attr) nodeList.item(0).getAttributes().getNamedItem("name")).getValue().toString());
 
                            nodeList = e.getElementsByTagName("grade");
                            System.out.println("Grade: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                            .getNodeValue());
 
                            nodeList = e.getElementsByTagName("age");
                            System.out.println("Age: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                            .getNodeValue());
                        }
                    }
                } else {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
         
        test parser = new test();
        parser.getAllUserNames("http://www.youbike.com.tw/ccccc.php");
    }
}
