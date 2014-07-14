<?php
	$apps = array();
	$opt;
    $date_start;
    $date_end;

	if($_REQUEST["app"] !== ""){
		$apps = explode(",", $_REQUEST["app"]);
	}

	if($_REQUEST["opt"] !== ""){
		$opt = $_REQUEST["opt"];
	}

	if($_REQUEST["ds"] !== "" && $_REQUEST["de"] !== ""){
		$date_start = $_REQUEST["ds"];
		$date_end = $_REQUEST["de"];
	}

	// to grab the data in Mysql by appnames
	require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

	$sql = "SELECT * FROM `downloads` ";
	for($count_app=0; $count_app<count($apps); $count_app++){
		if($count_app == 0){
			$sql_app = "WHERE (`appname` = '".$apps[$count_app]."' AND `date` >=  '".$date_start."' AND `date` <= '".$date_end."')";
			$sql .= $sql_app;
		}else{
			$sql_app = "OR (`appname` = '".$apps[$count_app]."' AND `date` >= '".$date_start."' AND `date` <= '".$date_end."')";
			$sql .= $sql_app;
		}
	}
	$sql_order = " ORDER BY `date` DESC;";
	$sql .= $sql_order;
    $db -> query($sql);

	$data = array(); // two-dimensional: date[], app1[], app2[]...
    $data['dates'] = array();
	foreach ($apps as $key => $app) {
        $data[$app] = array();
    }

    while($result = $db->fetch_array()){
        for($check_app=0; $check_app<count($apps); $check_app++){
            if($result['appname'] == $apps[$check_app]){
                if($check_app == 0){
                    array_push($data['dates'], $result['date']);
                }
                if($opt == "daily_installs_difference"){
                	$data[$apps[$check_app]][$result['date']] = $result['daily_device_installs'] - $result['daily_device_uninstalls'];
                }else if($opt == "rate_of_survival"){
                	$data[$apps[$check_app]][$result['date']] = ($result['daily_device_installs'] - $result['daily_device_uninstalls']) / $result['daily_device_installs'];
                }else{
                	$data[$apps[$check_app]][$result['date']] = $result[$opt];
                }
                
            }
        }
    }

    // echo $sql;
	echo json_encode($data, JSON_FORCE_OBJECT)."\n\n";
?>