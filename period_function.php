<?php
	function weekly($data_pre){
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
        while($count < count($data_pre['dates'])){
        	//if($current_day == 6){ // 周日開始，周六結束
            if($current_day == 3){ // 周四開始，周三結束(一周開始)
        		array_push($data['dates'], $data_pre['dates'][$count]);
        		for($i=1; $i<count($data_key); $i++){
                    if($data_key[$i] == "total_install" || $data_key[$i] == "current_device_installs"){
                        $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        continue;
                    }
                    if($data_key[$i] == "rate_of_survival"){
                        $total[$data_key[$i]] += (double)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        continue;
                    }
        			$total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
        		}
        		$current_day--;
            // }else if($current_day == 0){ // 周日開始，周六結束
        	}else if($current_day == 4){ // 周四開始，周三結束(總結一周)
        		for($i=1; $i<count($data_key); $i++){
                    $date = end($data['dates']);
                    if($data_key[$i] == "total_install" || $data_key[$i] == "current_device_installs"){
                        $data[$data_key[$i]][$date] = $total[$data_key[$i]];
                        $total[$data_key[$i]] = 0;
                        continue;
                    }
                    if($data_key[$i] == "rate_of_survival"){
                        $total[$data_key[$i]] += (double)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        $data[$data_key[$i]][$date] = (String)(number_format(($total[$data_key[$i]] / 7), 4));
                        $total[$data_key[$i]] = 0;
                        continue;
                    }
        			$total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
        			$data[$data_key[$i]][$date] = $total[$data_key[$i]];
        			$total[$data_key[$i]] = 0;
        		}
        		$current_day--;
        	}else{
        		for($i=1; $i<count($data_key); $i++){
                    if($data_key[$i] == "total_install" || $data_key[$i] == "current_device_installs"){
                        continue;
                    }
                    if($data_key[$i] == "rate_of_survival"){
                        $total[$data_key[$i]] += (double)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        continue;
                    }
        			$total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
        		}
        		$current_day--;
        	}
        	if($current_day==(-1)){
        		$current_day = 6;
        	}
        	$count++;
        }

		return $data;
	}

    function monthly($data_pre){
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
                    if($data_key[$i] == "current_device_installs" || $data_key[$i] == "total_install"){
                        if($total[$data_key[$i]] == 0){
                            $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                            $data[$data_key[$i]][$date] = $total[$data_key[$i]];    
                        }
                    }else if($data_key[$i] == "rate_of_survival"){
                        $total[$data_key[$i]] += (double)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        $data[$data_key[$i]][$date] = number_format($total[$data_key[$i]],4);
                    }else{
                        $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                        $data[$data_key[$i]][$date] = $total[$data_key[$i]];
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

    function year($data_pre){
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
                    $total[$data_key[$i]] += (int)$data_pre[$data_key[$i]][$data_pre['dates'][$count]];
                    $data[$data_key[$i]][$date] = $total[$data_key[$i]];
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

    function getYear($date){
        $year = "";
        $year = substr($date, 0, 4);
        return $year;
    }

    function getMonth($date){
        $month = "";
        $month = substr($date, 5, 2);
        return $month;
    }
?>