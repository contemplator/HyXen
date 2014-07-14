<?php
	require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

    $apps = array("RailTimeline", "Barcode", "SpeedDetectorEvo");
    $item = "daily_device_installs";

	$sql = "SELECT `appname`, `date`, ".$item." FROM `downloads` ";
	for($count_app=0; $count_app<count($apps); $count_app++){
		if($count_app == 0){
			$sql_app = "WHERE `appname` = '".$apps[$count_app]."'";
			$sql .= $sql_app;
		}else{
			$sql_app = " OR `appname` = '".$apps[$count_app]."'";
			$sql .= $sql_app;
		}
	}
    
	$sql_order = " ORDER BY `date` DESC limit 0,".count($apps)*30;
	$sql .= $sql_order;
    // echo $sql;
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
                $data[$apps[$check_app]][$result['date']] = $result[$item];
                // array_push($data[$apps[$check_app]], $result[$item]);
            }
        }
    }

    echo json_encode($data, JSON_FORCE_OBJECT)."\n\n";
?>