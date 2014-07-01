<?php
	require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

    $apps = array("RailTimeline", "Barcode", "SpeedDetectorEVO");
    $app = "RailTimeLine";
    $item = "current_device_installs";
    $date_start;
    $date_end;

    // $sql = "SELECT `id`,`appname`,`date`,`current_device_installs` FROM `downloads` WHERE (`appname` = 'RailTimeLine' AND `date` >=  '2014-01-01') OR (`appname` = 'SpeedDetectorEVO' AND `date` >=  '2014-01-01') OR (`appname` = 'Barcode' AND `date` >= '2014-01-01') ORDER BY `date` DESC;";
    $date = new Datetime(date('Y-m-d'));
    $date_end = $date->format('Y-m-d');
    // $date->modify('-3 month');
	$date->modify('-30 day');
	$date = $date->format('Y-m-d');
	$date_start = $date;

	// echo $date_start.",".$date_end;
	$sql = "SELECT `appname`, `date`, ".$item." FROM `downloads` ";
	for($count_app=0; $count_app<count($apps); $count_app++){
		if($count_app == 0){
			$sql_app = "WHERE (`appname` = '".$apps[$count_app]."' AND `date` >=  '".$date."')";
			$sql .= $sql_app;
		}else{
			$sql_app = "OR (`appname` = '".$apps[$count_app]."' AND `date` >= '".$date_start."')";
			$sql .= $sql_app;
		}
	}
	$sql_order = " ORDER BY `date` ASC;";
	$sql .= $sql_order;
    echo $sql;
    $db -> query($sql);

    $data = array(); // two-dimensional: date[], app1[], app2[]...
    $dates = array();
    $rail = array();
    $evo = array();
    $zerocard = array();
    while($result = $db->fetch_array()){
    	// print_r($result);
    	// if($result['appname'] == 'RailTimeline'){
    	// 	print_r($result);
    	// }
    	switch ($result['appname']) {
    		case 'RailTimeline':
    			// print_r($result);
    			array_push($dates, "2014-06-01");
    			array_push($rail, $result[$item]);
    			break;
    		case "SpeedDetectorEvo":
    			array_push($evo, $result[$item]);
    			break;
    		case 'Barcode':
    			array_push($zerocard, $result[$item]);
    			break;
    		default:
    			break;
    	}
    }
    // print_r($dates);
    // print_r($rail);
    $data['dates'] = $dates;
    $data['rail'] = $rail;
    $data['evo'] = $evo;
    $data['zerocard'] = $zerocard;
    // print_r($date);
    // print_r($data);

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
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script>
        	google.load("visualization", "1", {packages:["corechart"]});
      		google.setOnLoadCallback(drawChart);
      		function drawChart() {
				var data = google.visualization.arrayToDataTable([
	          		['Year', 'current_device_installs'],
	          		<?php
	          			$total = sizeof($data[0]);
	          			$count = 0;
	          			for($i=0; $i<$total; $i++){
	          				if($count == $total){
	          					echo "['".$data['dates'][$i]."',".$data['RailTimeline'][$i]."]";
	          				}else{
	          					echo "['".$data['dates'][$i]."',".$data['RailTimeline'][$i]."],";
	          				}
	          				$count++;
	          			}
	          		?>
	        	]);

	        	var options = {
	          		title: '<?php echo $item; ?>'
	        	};
	        	var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        		chart.draw(data, options);
	        }
    	</script>
    	<style type="text/css">
    		nav{
    			float: left;
    		}
    	</style>
	</head>
	<body>
		<table class="container_12">
			<tr>
				<td>APP</td>
				<td><?php 
						$search_app = "SELECT * FROM `app_list`";
						$db->query($search_app);
						while($app = $db->fetch_array()){
							echo '<nav><input type="checkbox" name="app" value='.$app['app_name'].'">'.$app['app_name'].'</nav>';
						}
					?>
				</td>
			</tr>
			<tr>
				<td>資料</td>
				<td><input type="radio" name="item" value="rail" checked=true>總安裝量
					<input type="radio" name="item" value="zerocard">當日下載量
					<input type="radio" name="item" value="evo">當日卸載量</td>
			</tr>
		</table>
		<div id="chart_div" class="container_12" style="width: 900px; height: 500px;"></div>
		<table>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</body>
</html>