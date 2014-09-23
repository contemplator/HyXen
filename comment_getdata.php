<?php
    //connect database
    require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

    // declare the values
    $apps = array();
    $os = "";
    $date_start = "";
    $date_end = "";
    $option = "";
    $data = array();

    // get the request
    if($_REQUEST["os"] !== ""){
        $os = $_REQUEST["os"];
    }

    if($_REQUEST["app"] !== ""){
        $apps = explode(",", $_REQUEST["app"]);
    }

    if($_REQUEST["option"] !== ""){
        $option = $_REQUEST["option"];
    }

    if($_REQUEST["ds"] !== null && $_REQUEST["de"] !== null){
        $date_start = $_REQUEST["ds"];
        $date_end = $_REQUEST["de"];
    }
    
    if($_REQUEST["option"] !== null){
        $optionion = $_REQUEST["option"];
    }

    // set the sql for query
    if($os == "Android"){
        $sql = "SELECT * FROM `downloads` ";
    }else if($os = "iOS"){
        $sql = "SELECT * FROM `i_downloads` ";
    }
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
                if($option == "daily_installs_difference"){
                    $data[$apps[$check_app]][$result['date']] = array();
                    $data[$apps[$check_app]][$result['date']][$option] = $result['daily_device_installs'] - $result['daily_device_uninstalls'];
                    $data[$apps[$check_app]][$result['date']]['comments'] = $result['comments'];
                }else if($option == "rate_of_survival"){
                    $data[$apps[$check_app]][$result['date']] = array();
                    $data[$apps[$check_app]][$result['date']][$option] = ($result['daily_device_installs'] - $result['daily_device_uninstalls']) / $result['daily_device_installs'];
                    $data[$apps[$check_app]][$result['date']][$option] = number_format($data[$apps[$check_app]][$result['date']][$option], 2);
                    $data[$apps[$check_app]][$result['date']]['comments'] = $result['comments'];
                }else{
                    $data[$apps[$check_app]][$result['date']] = array();
                    $data[$apps[$check_app]][$result['date']][$option] = $result[$option];
                    $data[$apps[$check_app]][$result['date']]['comments'] = $result['comments'];
                }
            }
        }
    }

    // echo $sql;
    echo json_encode($data, JSON_FORCE_OBJECT)."\n\n";
?>