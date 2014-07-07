
import java.awt.Desktop;
import java.io.IOException;
import java.net.*;
public class ClickWeb {
	 
    private Proxy proxy;
    private String fileName;
    private String filePath;
	//優惠折扣通
	//https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.ProMeApp&sd=20140501&ed=20140531&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=false&dev_acc=05129407701782775346
	public static void main(String[] args) throws URISyntaxException{
//		connectWeb();
		String url = "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.ProMeApp&sd=20140501&ed=20140531&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=false&dev_acc=05129407701782775346";
		if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
	}
	
	public static void connectWeb(){
		String link = "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.ProMeApp&sd=20140101&ed=20140430&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=false&dev_acc=05129407701782775346";
		try{
			URL url = new URL(link);
			URLConnection yc = url.openConnection();
			System.out.print("done");
		} catch (Exception e) {
		} finally {
		}
	}
}
