<?php
    require("period_function.php");
	//connect database
    require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

    // declare the values
    $app = "";
    $option = array();
    $os = "";
    $date_start = "";
    $date_end = "";
    $data = array();
    $period = "";

    // get the request
    if($_REQUEST["os"] !== null){
        $os = $_REQUEST["os"];
    }

    if($_REQUEST["app"] !== null){
        $app = $_REQUEST["app"];
    }

    if($_REQUEST["opt"] !== null){
        $option = explode(",", $_REQUEST["opt"]);
    }

    if($_REQUEST["ds"] !== null && $_REQUEST["de"] !== null){
        $date_start = $_REQUEST["ds"];
        $date_end = $_REQUEST["de"];
    }

    if($_REQUEST["period"] !== null){
        $period = $_REQUEST["period"];
    }

    // set the sql for query and execute
    if($os == "Android"){
        $sql = "SELECT * FROM `downloads` ";
    }else if($os == "iOS"){
        $sql = "SELECT * FROM `i_downloads` ";
    }else{
        echo "Not exist os type.";
        break;
    }
	$sql_opt = "WHERE (`appname` = '".$app."' AND `date` >=  '".$date_start."' AND `date` <= '".$date_end."')";
	$sql .= $sql_opt;
	$sql_order = " ORDER BY `date` DESC;";
	$sql .= $sql_order;
	$db -> query($sql);

    // declare array needed
	$data['dates'] = array();
	foreach ($option as $key => $o) {
		$data[$o] = array();
	}

    // arrange result
	while($result = $db->fetch_array()){
		array_push($data['dates'], $result['date']);
		for($count_opt=0; $count_opt<count($option); $count_opt++){
            if($option[$count_opt] == "daily_installs_difference"){
                $data[$option[$count_opt]][$result['date']] = ((int)$result["daily_device_installs"] - (int)$result["daily_device_uninstalls"]);
            }else if($option[$count_opt] == "rate_of_survival"){
                if((int)$result["daily_device_installs"] == 0){
                    $data[$option[$count_opt]][$result['date']] = "-1.0000";
                }else{
                    $data[$option[$count_opt]][$result['date']] = getPercent((int)$result["daily_device_installs"], (int)$result["daily_device_uninstalls"]);    
                }
            }else{
                $data[$option[$count_opt]][$result['date']] = (int)$result[$option[$count_opt]];    
            }
		}
	}

    // arrange by period
    switch ($period) {
        case 'daily':
            break;
        case 'weekly':
            $data = weekly($data);
            break;
        case 'monthly':
            $data = monthly($data);
            break;
        case 'year':
            $data = year($data);
            break;
    }

	echo json_encode($data, JSON_FORCE_OBJECT)."\n\n";

    function getPercent($installs, $uninstalls){
        $percent = ($installs - $uninstalls) / $installs;
        $percent = number_format($percent, 4);
        return $percent;
    }
?>