import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class DailyFlow {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//ex: getFlow("zc-17life", "2014-03-11");
		
		//ex: getFlow_onlyFlow("zc-17life", "2014-03-11");
		
		Scanner piping = new Scanner(System.in);
		System.out.println("Please input the piping you wanted:");
		String s1 = piping.next();
		Scanner date = new Scanner(System.in);
		System.out.println("Please input the date you wanted:");
		String s2 = date.next();
		getFlow(s1, s2);
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
}
