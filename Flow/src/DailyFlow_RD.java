import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class DailyFlow_RD {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//ex: getFlow("easymall-zcbear", "2014-03-11");
		
		//ex: getFlow_onlyFlow("easymall-zcbear", "2014-03-11");
		
		getFlow("promo-rail-1", "2014-06-25");
		getFlow("promo-rail-1", "2014-06-26");
		getFlow("promo-rail-1", "2014-06-27");
		getFlow("promo-rail-1", "2014-06-28");
		getFlow("promo-rail-1", "2014-06-29");
		getFlow("promo-rail-1", "2014-06-30");
		getFlow("promo-rail-1", "2014-07-01");
		getFlow("promo-rail-1", "2014-07-02");
		getFlow("promo-rail-1", "2014-07-03");
		getFlow("promo-rail-1", "2014-07-04");
		getFlow("promo-rail-1", "2014-07-05");
		getFlow("promo-rail-1", "2014-07-06");
	}

	public static void getFlow(String piping, String date){
		String link = "http://s.ad-locus.com/detail.php?db=";
		String flow = "";
		int tot = 0;
		try {
			link += piping;
			URL url = new URL(link);
			URLConnection yc = url.openConnection(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String input = "";
			while ((input = in.readLine()) != null) {
				if(input.contains(date)){
					int score;
					score = Integer.parseInt(input.substring(29));
					tot += score;
				}
			}
			System.out.println(date +"：" + tot);
			in.close();
		} 	catch (MalformedURLException e) {
		} 	catch (IOException e) {
		} finally {
		}
	}
	
	public static void getFlow_onlyFlow(String piping, String date){
		String link = "http://s.ad-locus.com/detail.php?db=";
		String flow = "";
		int tot = 0;
		try {
			link += piping;
			URL url = new URL(link);
			URLConnection yc = url.openConnection(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String input = "";
			while ((input = in.readLine()) != null) {
				if(input.contains(date)){
					int score;
					score = Integer.parseInt(input.substring(29));
					tot += score;
				}
			}
			System.out.println(tot);
			in.close();
		} 	catch (MalformedURLException e) {
		} 	catch (IOException e) {
		} finally {
		}
	}
	
//	public static void getFlow_sum(String piping, String date){
//		String link = "http://s.ad-locus.com/detail.php?db=";
//		String flow = "";
//		int tot = 0;
//		try {
//			link += piping;
//			URL url = new URL(link);
//			URLConnection yc = url.openConnection(); 
//			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//			String input = "";
//			while ((input = in.readLine()) != null) {
//				if(input.contains(date)){
//					String data = input.substring(29);
//					String score = data.split(")
//					int score;
//					score = Integer.parseInt(input.substring(29));
//					tot += score;
//				}
//			}
//			System.out.println(date +"：" + tot);
//			in.close();
//		} 	catch (MalformedURLException e) {
//		} 	catch (IOException e) {
//		} finally {
//		}
//	}
}
