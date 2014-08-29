<?php
    require("period_function.php");
    require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

    // declare variable needed
    $date_start = "";
    $date_end = "";
    $option = "";
    $period = "";
    $data = array();
    $data_google = array();
    $data_itunes = array();
    $RailTimeline = array(); //temp
    $SpeedDetectorEvo = array(); //temp
    $Barcode = array(); //temp

    // get requests
    if($_REQUEST["ds"] !== null && $_REQUEST["de"] !== null){
        $date_start = $_REQUEST["ds"];
        $date_end = $_REQUEST["de"];
    }

    if($_REQUEST["option"] !== null){
        $option = $_REQUEST["option"];
    }

    if($_REQUEST["period"] !== null){
        $period = $_REQUEST["period"];
    }
    
    // set google sql of query and execute
    $search_google = "SELECT * FROM `downloads` WHERE (`appname` = 'RailTimeline' AND `date` >= '".$date_start."' AND `date` <= '".$date_end."') OR (`appname` = 'SpeedDetectorEvo' AND `date` >= '".$date_start."' AND `date` <= '".$date_end."') OR (`appname` = 'Barcode' AND `date` >= '".$date_start."' AND `date` <= '".$date_end."') ORDER BY `date` DESC;";
    $db->query($search_google);

    // arrange result
    while($result = $db->fetch_array()){
        switch ($result['appname']) {
            case 'RailTimeline':
                $RailTimeline[$result['date']] = $result['daily_device_installs'];
                break;
            case 'SpeedDetectorEvo':
                $SpeedDetectorEvo[$result['date']] = $result['daily_device_installs'];
                break;
            case 'Barcode':
                $Barcode[$result['date']] = $result['daily_device_installs'];
                break;
            default:
                break;
        }
    }
    $data_google['RailTimeline'] = $RailTimeline;
    $data_google['SpeedDetectorEvo'] = $SpeedDetectorEvo;
    $data_google['Barcode'] = $Barcode;
    unset($RailTimeline);
    unset($SpeedDetectorEvo);
    unset($Barcode);

    // set itunes sql of query and execute
    $search_itunes = "SELECT * FROM `i_downloads` WHERE (`appname` = 'RailTimeline' AND `date` >= '".$date_start."' AND `date` <= '".$date_end."') OR (`appname` = 'SpeedDetectorEvo' AND `date` >= '".$date_start."' AND `date` <= '".$date_end."') OR (`appname` = 'Barcode' AND `date` >= '".$date_start."' AND `date` <= '".$date_end."');";
    $db->query($search_itunes);

    // arrange result
    while($result = $db->fetch_array()){
        switch ($result['appname']) {
            case 'RailTimeline':
                $RailTimeline[$result['date']] = $result['daily_device_installs'];
                break;
            case 'SpeedDetectorEvo':
                $SpeedDetectorEvo[$result['date']] = $result['daily_device_installs'];
                break;
            case 'Barcode':
                $Barcode[$result['date']] = $result['daily_device_installs'];
                break;
            default:
                break;
        }
    }
    $data_itunes['RailTimeline'] = $RailTimeline;
    $data_itunes['SpeedDetectorEvo'] = $SpeedDetectorEvo;
    $data_itunes['Barcode'] = $Barcode;
    unset($RailTimeline);
    unset($SpeedDetectorEvo);
    unset($Barcode);

    // arrange result again to match data{app1:{}, app2:{}..}
    $data['dates'] = array();
    $data['RailTimeline'] = array();
    $data['SpeedDetectorEvo'] = array();
    $data['Barcode'] = array();

    // arrange by os
    if($option == "All"){
        if(sizeof($data_google['RailTimeline']) >= sizeof($data_itunes['RailTimeline'])){
            foreach ($data_google['RailTimeline'] as $key => $d) {
                array_push($data['dates'], $key);
            }
            foreach ($data_google['RailTimeline'] as $key => $d) {
                // array_push($data['RailTimeline'], $data_google['RailTimeline'][$key] + $data_itunes['RailTimeline'][$key]);
                $data['RailTimeline'][$key] = $data_google['RailTimeline'][$key] + $data_itunes['RailTimeline'][$key];
            }
            foreach ($data_google['SpeedDetectorEvo'] as $key => $d) {
                // array_push($data['SpeedDetectorEvo'], $data_google['SpeedDetectorEvo'][$key] + $data_itunes['SpeedDetectorEvo'][$key]);
                $data['SpeedDetectorEvo'][$key] = $data_google['SpeedDetectorEvo'][$key] + $data_itunes['SpeedDetectorEvo'][$key];
            }
            foreach ($data_google['Barcode'] as $key => $d) {
                // array_push($data['Barcode'], $data_google['Barcode'][$key] + $data_itunes['Barcode'][$key]);
                $data['Barcode'][$key] = $data_google['Barcode'][$key] + $data_itunes['Barcode'][$key];
            }    
        }else{
            foreach ($data_itunes['RailTimeline'] as $key => $d) {
                array_push($data['dates'], $key);
            }
            foreach ($data_itunes['RailTimeline'] as $key => $d) {
                // array_push($data['RailTimeline'], $data_google['RailTimeline'][$key] + $data_itunes['RailTimeline'][$key]);
                @$data['RailTimeline'][$key] = $data_google['RailTimeline'][$key] + $data_itunes['RailTimeline'][$key];
            }
            foreach ($data_itunes['SpeedDetectorEvo'] as $key => $d) {
                // array_push($data['SpeedDetectorEvo'], $data_google['SpeedDetectorEvo'][$key] + $data_itunes['SpeedDetectorEvo'][$key]);
                @$data['SpeedDetectorEvo'][$key] = $data_google['SpeedDetectorEvo'][$key] + $data_itunes['SpeedDetectorEvo'][$key];
            }
            foreach ($data_itunes['Barcode'] as $key => $d) {
                // array_push($data['Barcode'], $data_google['Barcode'][$key] + $data_itunes['Barcode'][$key]);
                @$data['Barcode'][$key] = $data_google['Barcode'][$key] + $data_itunes['Barcode'][$key];
            }    
        }
    }
    else if($option == "Android"){
        foreach ($data_google['RailTimeline'] as $key => $d) {
            array_push($data['dates'], $key);
        }
        foreach ($data_google as $key1 => $detail) {
            foreach ($detail as $key2 => $d) {
                $data[$key1][$key2] = (int)$d;
            }
        }
    }
    else if($option == "iOS"){
        foreach ($data_itunes['RailTimeline'] as $key => $d) {
            array_push($data['dates'], $key);
        }
        foreach ($data_itunes as $key1 => $detail) {
            foreach ($detail as $key2 => $d) {
                $data[$key1][$key2] = (int)$d;
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
?>