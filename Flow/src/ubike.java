import java.awt.List;
import java.io.*;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class ubike {
	public static void main(String[] args) throws FileNotFoundException {
		try  {
//			Date date = new Date();
//		    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");       
//		    String today = bartDateFormat.format(date);       
//		    String fileName = today+".csv";
//		    System.out.println(fileName); //�o�� 20100625
//		    
//			FileWriter fw = new FileWriter(fileName);
//			fw.write(ubike.getScore("http://s.ad-locus.com/detail.php?db=hotspring-settv"));
//			fw.write("\n");
//			fw.write(ubike.getScore("http://s.ad-locus.com/detail.php?db=3hot"));
//			fw.write("\n");
//			fw.flush();
//			fw.close();
//			System.out.println("Done");
			System.out.println(ubike.getScore("http://www.youbike.com.tw/info3b.php?sno=0002"));
         }catch (Exception e) {
             e.printStackTrace();
         }
	}
	
	protected static String getScore(String url){
		String lat = "";
		String lng = "";
		String name = "";
		String adr = "";
		try {
			URL murl = new URL(url);
			URLConnection yc = murl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			// first use a Scanner to get each line
			String input = "";
			while ((input = in.readLine()) != null) {
				if(input.contains("id=\"lat\"")){
					int length = input.length();
					lat = input.substring(37, length-2);
				}
				else if(input.contains("id=\"lng\"")){
					int length = input.length();
					lat += "," + input.substring(37, length-2);
				}
			}
			in.close();
		} 	catch (MalformedURLException e) {
		} 	catch (IOException e) {
		} finally {
		}
		return lat;
	}
}
