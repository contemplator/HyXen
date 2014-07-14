<?php
    require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html charset=utf-8">
		<link rel="stylesheet" type="text/css" media="all" href="css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" media="all" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" media="all" href="css/text.css" />
        <link rel="stylesheet" type="text/css" media="all" href="css/960.css" />
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
        <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="js/json2.js"></script>
        <script>
            var response; //json
            var apps = ["RailTimeline", "Barcode", "SpeedDetectorEvo"];
            var option = "daily_device_installs";

            $(function() {
                $( "#date_start" ).datepicker();
                $( "#date_end" ).datepicker();
            });

        	$(document).ready(function(){
        		var checkboxes = document.getElementsByTagName("input");
                var d = new Date();
                var date_end = d.getFullYear() + "-" +dateAddZero(d.getMonth()+1) + "-" + dateAddZero(d.getDate());
                var date_start = d.getFullYear() + "-" +dateAddZero(d.getMonth()) + "-" + dateAddZero(d.getDate());
                var opt = "";
                document.getElementById("date_end").value = date_end.substring(5,7)+"/"+date_end.substring(8,10)+"/"+date_end.substring(0,4);
                document.getElementById("date_start").value = date_start.substring(5,7)+"/"+date_start.substring(8,10)+"/"+date_start.substring(0,4);
        		for(var i=0; i<checkboxes.length; i++){
        			if(checkboxes[i].value == "Barcode"){
        				checkboxes[i].setAttribute("checked",true);
        			}else if(checkboxes[i].value == "RailTimeline"){
        				checkboxes[i].setAttribute("checked",true);
        			}else if(checkboxes[i].value == "SpeedDetectorEvo"){
        				checkboxes[i].setAttribute("checked",true);
        			}
        		}

                var radioes = document.getElementsByTagName("input");
                for(var i=0; i<radioes.length; i++){
                    if(radioes[i].value == "daily_device_installs"){
                        opt = "daily_device_installs";
                        radioes[i].setAttribute("checked", true);
                    }
                }

                var get = "oneItem.php?app=" + apps + "&opt=" + option + "&ds=" + date_start + "&de=" + date_end;
                document.getElementById("choice").innerHTML = get;

                if(apps.length == 0){
                    return;
                }else{
                    var xmlhttp=new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function() {
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                            response = JSON.parse(xmlhttp.responseText);
                            document.getElementById("data").innerHTML = JSON.stringify(response);
                        }
                    }
                    xmlhttp.open("GET",get,true);
                    xmlhttp.send();
                }

                function dateAddZero(s){
                    if(s < 10){
                        return "0" + s.toString();
                    }else{
                        return s;
                    }
                }
        	});

        	function updateApps(){
                var choice = document.getElementById("choice");
                var date_start;
                var date_end;
                if (document.getElementById("date_start").value) {
                    var d = document.getElementById("date_start").value;
                    date_start = d.substring(6,10) + "-" + d.substring(0,2) + "-" + d.substring(3,5);
                };
                if (document.getElementById("date_end").value) {
                    var d = document.getElementById("date_end").value;
                    date_end = d.substring(6,10) + "-" + d.substring(0,2) + "-" + d.substring(3,5);
                }
                if((new Date(date_end)) <= (new Date(date_start))){
                    alert("起始日不可以比結束日大!");
                    return;
                }

                apps = [];

        		var inputs = document.getElementsByTagName("input");
                for (var i=0; i < inputs.length; i++) {
        			if(inputs[i].type == "checkbox"){
        				if(inputs[i].checked){
        					apps.push(inputs[i].value);
        				}
        			}
                    else if(inputs[i].type == "radio"){
                        if(inputs[i].checked){
                            option = inputs[i].value;
                        }
                    }
        		}

                var get = "oneItem.php?app=" + apps + "&opt=" + option + "&ds=" + date_start + "&de=" + date_end;
                choice.innerHTML = get;

        		var xmlhttp=new XMLHttpRequest();
			  	xmlhttp.onreadystatechange=function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        response = JSON.parse(xmlhttp.responseText);
                        document.getElementById("data").innerHTML=JSON.stringify(response);
                        drawVisualization();
				    }
				}
				xmlhttp.open("GET",get,true);
				xmlhttp.send();
                
        	}
            
            google.load('visualization', '1', {packages: ['annotatedtimeline']});
            function drawVisualization() {
                var data = new google.visualization.DataTable();
                var displayer = document.getElementById("data");
                data.addColumn('date', 'Date');
                for(var i=0; i<apps.length; i++){
                    data.addColumn('number', apps[i]);
                }

                for(var j=0; j<Object.keys(response['dates']).length; j++){
                    var row = [];
                    row.push(new Date(response['dates'][j]));
                    for(var a=0; a<apps.length; a++){
                        row.push(parseFloat(response[apps[a]][response['dates'][j]]));
                    }
                    data.addRows([row]);
                }

                var annotatedtimeline = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
                annotatedtimeline.draw(data, {'displayAnnotations': true});
            }
            google.setOnLoadCallback(drawVisualization);
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
							echo '<nav><input type="checkbox" name="app" value='.$app['googleName'].' onchange="updateApps()">'.$app['app_name'].'</nav>';
						}
					?>
				</td>
			</tr>
			<tr>
				<td>資料</td>
				<td><?php 
                        $search_option = "SELECT * FROM `option_list`";
                        $db->query($search_option);
                        while($option = $db->fetch_array()){
                            echo "<nav><input type='radio' name='option' value=".$option['name_google']." onchange='updateApps()'>".$option['name']."</nav>";
                        }
                    ?>
                </td>
			</tr>
            <tr>
                <td>起始日</td>
                <td><input type="text" id="date_start" name="date_start" value="" onchange="updateApps()"></td>
            </tr>
            <tr>
                <td>結束日</td>
                <td><input type="text" id="date_end" name="date_end" value="" onchange="updateApps()"></td>
            </tr>
		</table>
        <div class="container_12">
            <a href="printChart.php">一般圖表</a>
        </div>
        <div id="chart_div" class="container_12" style="width: 900px; height: 500px;"></div>
        <table id="displayTable">

        </table>
        <p id="data">no data</p>
        <p id="choice">Your choice</p>
	</body>
    <script type="text/javascript">
        var displayTable = document.getElementById("displayTable");
        var column = "<tr><td>Date</td>"
        for(var i=0; i<apps.length; i++){
            column += "<td>"+ apps[i] +"</td>";
        }
        column += "</tr>";
        displayTable.innerHTML += column;

        for(var j=0; j<Object.keys(response["dates"]).length; j++){
        //     var row = "<tr>";
        //     row += "<td>" + (new Date(response['dates'][j])) + "</td>";
        //     for(var a=0; a<apps.length; a++){
        //         row += "<td>" + response[apps[a]][response['dates'][j]] + "</td>";
        //     }
        //     row += "</tr>";
        //     displayTable.innerHTML += row;
        }
    </script>
</html>