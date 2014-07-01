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

  //   function updateDownloads(){
  //   	global $apps, $db;
  //   	foreach ($apps as $key => $app) {
		// 	$fp = fopen("C:\\Users\\Leo\\Downloads\\20140613\\".$app.".csv", "r");
		// 	$count = 1;	
		// 	while( $row = fgetcsv( $fp, @$CSVfile_size)){
		// 		if($count <= 5){
		// 			$count++;
		// 			continue;
		// 		}else{
		// 			$date = $row[0];
		// 			$date = substr($date, 0, 4)."-".substr($date, 4, 2)."-".substr($date, 6, 2);
		// 			$sql = "INSERT INTO `hyxen`.`downloads` (`id`, `appname`, `date`, `current_active`, `daily_installs`, `daily_uninstalls`, `total_downloads`) VALUES (NULL, '".$key."', '".$date."', '".$row[1]."', '".$row[2]."', '".$row[3]."', '3000');";
		// 			$db -> query($sql);
		// 		}
		// 	}
		// 	fclose($fp);
		// 	unlink("C:\\Users\\Leo\\Downloads\\20140613\\".$app.".csv");
		// }
  //   }

    function unlinkFiles(){
    	global $apps;
    	foreach ($apps as $key => $app) {
			unlink("C:\\Users\\Leo\\Downloads\\20140613\\".$app.".csv");
		}
    }
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html charset=utf-8">
		<link rel="stylesheet" type="text/css" media="all" href="css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" media="all" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" media="all" href="css/text.css" />
        <link rel="stylesheet" type="text/css" media="all" href="css/960.css" />
        <script type="text/javascript" src="js/jquery-2.1.0.min.js"></script>
        <script type="text/javascript">
	    	function updateDownloads(){
	    		document.location.href="http://localhost:8080/HyXen/updateDownloads.php";
	    	}

	    	function unlinkFiles(){
	    		alert("delete");
	    	}
        </script>
	</head>
	<style>
		.show_data{
			width: 100%;
		}
	</style>
	<body>
		<div class="head">

		</div>
		<div class="content container_12">
			<table class="show_data">
				<tr>
					<td>App</td><td>日期</td><td>總安裝量</td><td>當日下載量</td><td>當日移除量</td>
				</tr>
				<?php
					foreach ($apps as $key => $app) {
						$fp = fopen("C:\\Users\\Leo\\Downloads\\20140613\\".$app.".csv", "r");
						$count = 1;	
						while( $row = fgetcsv( $fp, @$CSVfile_size)){
							if($count <= 5){
								$count++;
								continue;
							}
				?>
				<tr>
					<td><?php echo $key; ?></td>
					<td><?php echo $row[0]; ?></td>
					<td><?php echo $row[1]; ?></td>
					<td><?php echo $row[2]; ?></td>
					<td><?php echo $row[3]; ?></td>
				</tr>
				<?php
							$count++;
						}
					}
				?>
			</table>
			<Button name="updateDownloads" class="btn btn-success" onclick="updateDownloads()">確認，更新到資料庫</Button>
			<Button name="unlinkFiles" class="btn btn-danger" onclick="unlinkFiles()">刪除檔案</Button>
		</div>
		<div class="footer">
		</div>
<html>