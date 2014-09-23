<?php
	function weekly_apps($data_pre, $option){
		$count = 0;
		$first_date = $data_pre['dates'][$count];
	    $first_date_year = (int)substr($first_date, 0, 4);
        $first_date_month = (int)substr($first_date, 5, 2);
        $first_date_date = (int)substr($first_date, 8, 2);
        $first_date_day = date('D',mktime(0, 0, 0, $first_date_month, $first_date_date, $first_date_year));

        switch ($first_date_day) {
            case 'Sun':
                $first_date_day = 0;
                break;
            case 'Mon':
                $first_date_day = 1;
                break;
            case 'Tue':
                $first_date_day = 2;
                break;
            case 'Wed':
                $first_date_day = 3;
                break;
            case 'Thu':
                $first_date_day = 4;
                break;
            case 'Fri':
                $first_date_day = 5;
                break;
            case 'Sat':
                $first_date_day = 6;
                break;
            default:
                break;
        }

        $total = array();
        $data = array();
        $data_key = array();
        foreach ($data_pre as $key => $value) {
            $data[$key] = array();
            $total[$key] = 0;
            array_push($data_key, $key);
        }
        $current_day = $first_date_day;
        // $count 是由最新(最後)的日子往回推
        if($option == "total_install" || $option == "current_device_installs"){
            while($count < count($data_pre['dates'])){
                // for($i=1; $i<count($data_key); $i++){
                    if($current_day == 3){ // 周四開始(總結一週)
                        array_push($data['dates'], $data_pre['dates'][$count]);
                        for($i=1; $i<count($data_key); $i++){
                            $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        }
                    }else if($current_day == 4){ // 周三結束(一周開始)
                        for($i=1; $i<count($data_key); $i++){
                            $date = end($data['dates']);
                            $data[$data_key[$i]][$date] = $total[$data_key[$i]];
                            $total[$data_key[$i]] = 0;
                        }
                    }else{

                    }
                    $current_day--;
                    $count++;
                    if($current_day==(-1)){
                        $current_day = 6;
                    }
                // }
            }
        }else if($option == "rate_of_survival"){
            while($count < count($data_pre['dates'])){
                if($current_day == 3){ // 周四開始(總結一週)
                    array_push($data['dates'], $data_pre['dates'][$count]);
                    for($i=1; $i<count($data_key); $i++){
                        $total[$data_key[$i]] += $data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                    }
                }else if($current_day == 4){ // 周三結束(一周開始)
                    $date = end($data['dates']);
                    for($i=1; $i<count($data_key); $i++){
                        $data[$data_key[$i]][$date] = (String)(number_format(($total[$data_key[$i]]/7), 4));
                        $total[$data_key[$i]] = 0;
                    }
                }else{
                    for($i=1; $i<count($data_key); $i++){
                        $total[$data_key[$i]] += $data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                    }
                }
                $current_day--;
                $count++;
                if($current_day==(-1)){
                    $current_day = 6;
                }
            }
        }else{

        }

		return $data;
	}

    function monthly_apps($data_pre, $option){
        $count = 0;
        $last_date = $data_pre['dates'][0];

        $total = array();
        $data = array();
        $data_key = array();
        foreach ($data_pre as $key => $value) {
            $data[$key] = array();
            $total[$key] = 0;
            array_push($data_key, $key);
        }
        
        $current_month = getMonth($last_date);
        array_push($data['dates'], $data_pre['dates'][0]);
        $count_permonth = 0;
        while($count < count($data_pre['dates'])){
            if(getMonth($data_pre['dates'][$count]) == $current_month){ // 同一個月
                $date = array_pop($data['dates']);
                array_push($data['dates'], $date);
                for($i=1; $i<count($data_key); $i++){
                    if($option == "current_device_installs" || $option == "total_install"){
                        if($total[$data_key[$i]] == 0){
                            $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                            $data[$data_key[$i]][$date] = $total[$data_key[$i]];    
                        }else{
                            $data[$data_key[$i]][$date] = $total[$data_key[$i]];
                        }
                    }else if($option == "rate_of_survival"){
                        $total[$data_key[$i]] += (double)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        $data[$data_key[$i]][$date] = number_format($total[$data_key[$i]],4);
                    }else{
                    }
                } 
            }else{ // 不同月                
                $current_month = getMonth($data_pre['dates'][$count]);
                $date = $data_pre['dates'][$count];
                array_push($data['dates'], $data_pre['dates'][$count]);
                for($i=1; $i<count($data_key); $i++){
                    $total[$data_key[$i]] = 0;
                    $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                    $data[$data_key[$i]][$date] = $total[$data_key[$i]];
                }
            }
            $count++;
        }
        
        return $data;
    }

    function year_apps($data_pre, $option){
        $count = 0;
        $last_date = $data_pre['dates'][0];

        $total = array();
        $data = array();
        $data_key = array();
        foreach ($data_pre as $key => $value) {
            $data[$key] = array();
            $total[$key] = 0;
            array_push($data_key, $key);
        }

        $current_year = getYear($last_date);
        array_push($data['dates'], $data_pre['dates'][0]);
        while($count < count($data_pre['dates'])){
            if(getYear($data_pre['dates'][$count]) == $current_year){
                $date = array_pop($data['dates']);
                array_push($data['dates'], $date);
                for($i=1; $i<count($data_key); $i++){
                    if($option == "current_device_installs" || $option == "total_install"){
                        if($total[$data_key[$i]] == 0){
                            $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                            $data[$data_key[$i]][$date] = $total[$data_key[$i]];    
                        }else{
                            $data[$data_key[$i]][$date] = $total[$data_key[$i]];
                        }
                    }else if($option == "rate_of_survival"){
                        $total[$data_key[$i]] += (double)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        $data[$data_key[$i]][$date] = number_format($total[$data_key[$i]],4);
                    }
                }
            }else{
                $current_year = getYear($data_pre['dates'][$count]);
                $date = $data_pre['dates'][$count];
                array_push($data['dates'], $data_pre['dates'][$count]);
                for($i=1; $i<count($data_key); $i++){
                    $total[$data_key[$i]] = 0;
                    $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                    $data[$data_key[$i]][$date] = $total[$data_key[$i]];
                }
            }
            $count++;
        }

        return $data;
    }
?>