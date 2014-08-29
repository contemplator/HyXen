<?php
    require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

    if(!is_null($_POST['submit'])){
    	$data = $_POST['data'];
    }
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html charset=utf-8">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
        <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="js/json2.js"></script>
        <script>
        	var response;
        	$(document).ready(function(){
        		var get = "<?php echo $data; ?>";
        		var xmlhttp=new XMLHttpRequest();
	            xmlhttp.onreadystatechange=function() {
	                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
	                    response = JSON.parse(xmlhttp.responseText);
	                    drawChart();
	                }
	            }
		        xmlhttp.open("GET",get,true);
		        xmlhttp.send();
        	});

            google.load("visualization", "1", {packages:["corechart"]});
            function drawChart() {
            	var columns = [];
            	var dataTable = [];

            	for (var key in response){
            		columns.push(key);
            	}

                dataTable.push(columns);
                len = Object.keys(response['dates']).length
                for(var j=len-1 ; j>=0; j--){
                    var row = [];
                    var rDate = new Date(response['dates'][j]);
                    var dateStr = padStr(rDate.getFullYear()) +"-"+ padStr(rDate.getMonth() + 1) +"-"+ padStr(rDate.getDate());
                    row.push(dateStr);
                    for(var a=1; a<columns.length; a++){
                        row.push(parseFloat(response[columns[a]][response['dates'][j]]));
                    }
                    dataTable.push(row);
                }
                var data = google.visualization.arrayToDataTable(
                    dataTable
                );

                var options = {
                    title: 'Company Performance'
                };

                var chart_div = document.getElementById('chart_div');
                var chart = new google.visualization.LineChart(chart_div);
                var exportPNG = document.getElementById("exportPNG");
                // Wait for the chart to finish drawing before calling the getImageURI() method.
                google.visualization.events.addListener(chart, 'ready', function () {
                    chart_div.innerHTML = '<img src="' + chart.getImageURI() + '">';
                    exportPNG.setAttribute("href", chart.getImageURI());
                    exportPNG.setAttribute("download", "GoogleChart");
                    console.log(chart_div.innerHTML);
                });

                chart.draw(data, options);

                function padStr(i) {
                    return (i < 10) ? "0" + i : "" + i;
                }
            }
            // google.setOnLoadCallback(drawChart);
    	</script>
	</head>
	<body>
		<div id="data"></div>
        <div class="container_12">
            <a id="exportPNG" href="printChart.php">下載圖表</a>
            <div id="chart_div" style="width: 900px; height: 500px;"></div>
        </div>
	</body>
</html>