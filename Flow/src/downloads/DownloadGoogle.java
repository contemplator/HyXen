package downloads;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DownloadGoogle {
	
	public static void main (String [] argv) throws InterruptedException, IOException, URISyntaxException{
		
		HashMap<String, String> appsLink = setLinks();
		String startdate = "20140601";
		String enddate = null;
//		enddate = getDateTime();
		enddate = "20140630";
		for( Object key : appsLink.keySet()){
			String link = appsLink.get(key).toString();
			if(link.contains("20100101") && link.contains("20100102")){
				String s1 = link.replace("20100101", startdate);
				String s2 = s1.replace("20100102", enddate);
				
				if(Desktop.isDesktopSupported()){
		            Desktop desktop = Desktop.getDesktop();
		            desktop.browse(new URI(s2));
		        }else{
		            Runtime runtime = Runtime.getRuntime();
		            runtime.exec("xdg-open " + s2);
		        }
			}else{
				System.out.println("error: not contains 20100101 & 20100102");
			}
		}
		
//		MysqlTest test = new MysqlTest();
//  		System.out.println("connect!"); 
//  		test.insertTable("yku2", "7890"); 
//  		test.SelectTable();
		
	}

	private static HashMap<String, String> setLinks() {
		HashMap<String, String> appsLink = new HashMap<String, String>();
		appsLink.put("AtomicEnergy", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.AtomicEnergy&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("Barcode", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.Barcode&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("bikechallenger", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.bikechallenger&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("DrivingLogger", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.DrivingLogger&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("GeoMe", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.GeoMe&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("icare", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.icare&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("krt", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.krt&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("love", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.love&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("MobileLocusMap", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.MobileLocusMap&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("ProMeApp", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.ProMeApp&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("SpeedDetectorEvo", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.SpeedDetectorEvo&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("Taiwan368", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.Taiwan368&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("WifiMap", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.WifiMap&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("HotSpring", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.sled.iset.HotSpring&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("Women30", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.sled.iset.Women30&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("taximeter", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.taximeter.app&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("RailTimeline", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.RailTimeline&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		
		return appsLink;
	}
	
	public static String getDateTime(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		return strDate;
	}
}
