<?php 
	require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);
    $getApp = "SELECT `app_name`, `app_package` FROM `app_list` WHERE 1;";
    $db -> query($getApp);
    $apps = array();
    
    while($result = $db -> fetch_array()){
    	$apps[$result['app_name']] = $result['app_package'];
    }
	
	foreach ($apps as $key => $app) {
		$fp = fopen("C:\\Users\\Leo\\Downloads\\20140613\\".$app.".csv", "r");
		$count = 1;	
		while( $row = fgetcsv( $fp, @$CSVfile_size)){
			if($count <= 5){
				$count++;
				continue;
			}else{
				$date = $row[0];
				$date = substr($date, 0, 4)."-".substr($date, 4, 2)."-".substr($date, 6, 2);
				$sql = "INSERT INTO `hyxen`.`downloads` (`id`, `appname`, `date`, `current_active`, `daily_installs`, `daily_uninstalls`, `total_downloads`) VALUES (NULL, '".$key."', '".$date."', '".$row[1]."', '".$row[2]."', '".$row[3]."', '3000');";
				$db -> query($sql);
			}
		}
		fclose($fp);
		unlink("C:\\Users\\Leo\\Downloads\\20140613\\".$app.".csv");
	}
    header('Location: http://localhost:8080/HyXen/update_test.php');
?>