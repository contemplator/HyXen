import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.plaf.SliderUI;


public class CollectDownloads extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	TextField txt_startdate = new TextField(" ", 50);
	TextField txt_enddate = new TextField(" ", 50);
	Button submit = new Button("submit");
	TextArea output = new TextArea();
	private Boolean isDone = false;
	
	public CollectDownloads(){
		frame = new JFrame();
	}
	
	public void run(){
		frame.setSize(600, 400);
		frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Panel p1 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p1.setSize(100, 50);
        Label label_startdate = new Label("Start Date");
        p1.add(label_startdate);
        p1.add(txt_startdate);
        
        Panel p2 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p1.setSize(100, 50);
        Label label_enddate = new Label("End Date");
        p2.add(label_enddate);
        p2.add(txt_enddate);
        
        Panel p3 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p3.setSize(100, 50);
        submit.addActionListener(this);
        p3.add(submit);
        
        Panel p4 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p4.setSize(300, 200);
        p4.add(output);
        
        frame.add(p1);
        frame.add(p2);
        frame.add(p3);
        frame.add(p4);
        frame.setVisible(true);
	}
	
	public static void main (String [] argv) throws FileNotFoundException, InterruptedException{
		
        
//        String s = "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.MobileLocusMap&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346";
//        System.out.print(s.replace("20100101", "20140501"));
		
//		String futurePath = "C:\\Users\\Leo\\Downloads\\20140606";
//		File toFolder = new File(futurePath);
//		if (!toFolder.exists()) {
//			toFolder.mkdirs();
//		}
//		File file = new File("C:\\Users\\Leo\\Downloads\\com.hyxen.app.AtomicEnergy_overall_installs.csv");
//		File toFile = new File("C:\\Users\\Leo\\Downloads\\20140606\\" + file.getName()); 
//		file.renameTo(toFile);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		HashMap<String, String> appsLink = setLinks();
		
		String startdate = null;
		String enddate = null;
		
		if (e.getSource() == submit){
			if(!txt_startdate.getText().trim().equals("")){
				startdate = txt_startdate.getText().trim();
				System.out.println("get start");
			}else{
				startdate = "20100101";
			}
			
			if(!txt_enddate.getText().trim().equals("")){
				enddate = txt_enddate.getText().trim();
				System.out.println("get end");
			}else{
				enddate = getDateTime();
				System.out.println(enddate);
			}
		}
		try{	
			output.setText("");
			for( Object key : appsLink.keySet()){
				String link = appsLink.get(key).toString();
				if(link.contains("20100101") && link.contains("20100102")){
					String s1 = link.replace("20100101", startdate);
					String s2 = s1.replace("20100102", enddate);
					output.append("Done" + key + ":" + s2 + "\n");
					
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
		}catch (Exception ex){
			ex.printStackTrace();
		}
		isDone = true;
		movefile();
	}
	
	public static void movefile() {
		String[] filelist = {
			"com.hyxen.app.AtomicEnergy_overall_installs.csv", 
			"com.hyxen.app.Barcode_overall_installs.csv",
			"com.hyxen.app.bikechallenger_overall_installs.csv",
			"com.hyxen.app.DrivingLogger_overall_installs.csv",
			"com.hyxen.app.GeoMe_overall_installs.csv",
			"com.hyxen.app.icare_overall_installs.csv",
			"com.hyxen.app.krt_overall_installs.csv",
			"com.hyxen.app.love_overall_installs.csv",
			"com.hyxen.app.MobileLocusMap_overall_installs.csv",
			"com.hyxen.app.ProMeApp_overall_installs.csv",
			"com.hyxen.app.RailTimeline_overall_installs.csv",
			"com.hyxen.app.SpeedDetectorEvo_overall_installs.csv",
			"com.hyxen.app.Taiwan368_overall_installs.csv",
			"com.hyxen.app.WifiMap_overall_installs.csv",
			"com.hyxen.sled.iset.HotSpring_overall_installs.csv",
			"com.hyxen.sled.iset.Women30_overall_installs.csv",
			"com.hyxen.taximeter.app_overall_installs.csv"
		};
		String[] frompath = new String[filelist.length];
//		String[] topath = new String[filelist.length];
		String currentDate = getDateTime();
		String originPath = "C:\\Users\\Leo\\Downloads";
		String futurePath = "C:\\Users\\Leo\\Downloads\\" + currentDate;
		File fromFolder = new File(originPath);
		File toFolder = new File(futurePath);
		if (!toFolder.exists()) {
			toFolder.mkdirs();
		}
		
		for(int i=0; i<filelist.length; i++){
			String path = originPath + "\\" + filelist[i];
			File file = new File(path);
			if (file.isDirectory()) {
//				moveFile(file.getPath(), futurePath + "\\" + file.getName());
			}
			if(file.isFile()){
				File toFile = new File(futurePath + "\\" + file.getName());
				file.renameTo(toFile);
			}
		}
	}

	private HashMap<String, String> setLinks() {
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
