<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html charset=utf-8">
        <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">

        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <link rel="stylesheet" type="text/css" media="all" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" media="all" href="css/text.css" />
        <link rel="stylesheet" type="text/css" media="all" href="css/960.css" />

        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript" src="js/json2.js"></script>
        <script type="text/javascript" src="js/period.js"></script>
        <link rel="stylesheet" type="text/css" href="css/index.css" />
	</head>
	<script type="text/javascript">
        // declare variable
        var response;
        var app = [];
        var option = "";
        var date_start = "";
        var date_end = "";
        var os = "";
        var period = "";

        // inital datepicker
        $(function() {        
            $( "#date_start" ).datepicker({
                changeMonth: true,
                changeYear: true
            });
            $( "#date_end" ).datepicker({
                changeMonth: true,
                changeYear: true
            });
        });

        // init the variable and view
        $(document).ready(function(){
            // init date
            var d = new Date();
            date_end = d.getFullYear() + "-" +dateAddZero(d.getMonth()+1) + "-" + dateAddZero(d.getDate());
            date_start = d.getFullYear() + "-" +dateAddZero(d.getMonth()) + "-" + dateAddZero(d.getDate());
            document.getElementById("date_end").value = date_end.substring(5,7)+"/"+date_end.substring(8,10)+"/"+date_end.substring(0,4);
            document.getElementById("date_start").value = date_start.substring(5,7)+"/"+date_start.substring(8,10)+"/"+date_start.substring(0,4);

            // init option
            var options = document.getElementsByName("option");
            option = "All";
            for(var i=0; i<options.length; i++){
                if (options[i].value == option) {
                    options[i].setAttribute("checked", true);
                };
            }

            // init period
            var periods = document.getElementsByName("period");
            period = "daily";
            for(var i=0; i<periods.length; i++){
                if(periods[i].value == period) {
                    periods[i].setAttribute("checked", true);
                }
            }

            // init app
            app = ["RailTimeline", "SpeedDetectorEvo", "Barcode"];

            // init os
            os = "All";

            // init title
            var title_os = document.getElementById("title-os");
            title_os.innerHTML = os;
            var title_duration = document.getElementById("title-duration");
            title_duration.innerHTML = date_start + " - " + date_end;

            function dateAddZero(s){
                if(s < 10){
                    return "0" + s.toString();
                }else{
                    return s;
                }
            }
            updateData();
        });

        function updateData(){
            // get date_start and date_end
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

            // get option
            var options = document.getElementsByName("option");
            var option = "";
            for(var i=0; i<options.length; i++){
                if (options[i].checked) {
                    option = options[i].value;
                    os = options[i].value;
                };
            }

            // get period
            var periods = document.getElementsByName("period");
            var period = "daily";
            for(var i=0; i<periods.length; i++){
                if(periods[i].checked) {
                    period = periods[i].value;
                }
            }
            
            //edit title
            var title_os = document.getElementById("title-os");
            title_os.innerHTML = os;
            var title_duration = document.getElementById("title-duration");
            title_duration.innerHTML = date_start + " ~ " + date_end;

            // ajax
            var get = "index_getdata.php?ds=" + date_start + "&de=" + date_end + "&option=" + option + "&period=" + period;
            var xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange=function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    // alert(xmlhttp.responseText);
                    response = JSON.parse(xmlhttp.responseText);
                    document.getElementById("exportPng").value = get;
                    drawVisualization();
                    updateTable();
                }
            }
            xmlhttp.open("GET",get,true);
            xmlhttp.send();
        }

        // google chart : https://developers.google.com/chart/interactive/docs/gallery/annotatedtimeline?hl=zh-TW
        google.load('visualization', '1', {packages: ['annotatedtimeline']});
        function drawVisualization() {
            var data = new google.visualization.DataTable();
            var displayer = document.getElementById("data");
            data.addColumn('date', 'Date');
            data.addColumn('number', 'RailTimeline');
            // data.addColumn('String', 'RailTimeline_comment');
            data.addColumn('number', 'SpeedDetectorEvo');
            // data.addColumn('String', 'SpeedDetector_comment');
            data.addColumn('number', 'Barcode');
            // data.addColumn('String', 'Barcode_comment');

            for(var j=0; j<Object.keys(response["dates"]).length; j++){
                var row = [];
                row.push(new Date(response['dates'][j]));
                row.push(response['RailTimeline'][response['dates'][j]]);
                row.push(response['SpeedDetectorEvo'][response['dates'][j]]);
                row.push(response['Barcode'][response['dates'][j]]);
                data.addRows([row]);
            }

            var annotatedtimeline = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
            annotatedtimeline.draw(data, {'displayAnnotations': true});
        }

        function updateTable(){
            var data_div = document.getElementById("data_div");
            var innerData = "";
            var table_title = "<tr><th>日期</th>";
            for(var i=0; i<app.length; i++){
                table_title += "<th>" + app[i] + "</th>";
            }
            table_title += "</tr>";
            innerData +=  table_title;
            for(var i=0; i<Object.keys(response['dates']).length; i++){
                var table_data = "<tr><td>" + response['dates'][i] + "</td>";
                for(var j=0; j<app.length; j++){
                    table_data = table_data + "<td>" + response[app[j]][response['dates'][i]] + "</td>";
                }
                table_data += "</tr>";
                innerData += table_data;
            }
            data_div.innerHTML = innerData;
            document.getElementById("exportCsv").value = innerData;
        }
    </script>
	<body>
		<div id="navgation" class="navbar navbar-default">
            <div class="container-fluid container_12">
                <div class="navbar-header">
	                <a class="navbar-brand" href="index.php">HyXen</a>
            	</div>
            	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            		<ul class="nav navbar-nav">
                        <li class="active"><a href="index.php">Home</a></li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Android&nbsp<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="android_apps.php">多支APP比較</a></li>
                                <li><a href="android_options.php">單支APP比較</a></li>
                            </ul>
                        </li>
            			<li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">iOS&nbsp<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="iOS_apps.php">多支APP比較</a></li>
                                <li><a href="iOS_options.php">單支APP比較</a></li>
                            </ul>
                        </li>
                        <li><a href="comment.php">下載量波動記錄</a></li>
            		</ul>
            	</div>
            </div>
        </div>

        <div class="container_12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <nav class="title">
                        <span>三大APP下載量 - </span>
                        <span id="title-os"> os </span>
                        <span> - </span>
                        <span id="title-duration"> duration </span>
                    </nav>
                </div>
                <div class="panel-body">
                    <table class="table table-striped">
                        <tr>
                            <td class="grid_2">OS</td>
                            <td>
                                <input type="radio" name="option" value="All" onchange="updateData()">All&nbsp
                                <input type="radio" name="option" value="Android" onchange="updateData()">Android&nbsp
                                <input type="radio" name="option" value="iOS" onchange="updateData()">iOS&nbsp
                            </td>
                        </tr>
                        <tr>
                            <td class="grid_2">開始日期</td>
                            <td><input type="text" id="date_start" name="date_start" value="" onchange="updateData()"></td>
                        </tr>
                        <tr>
                            <td class="grid_2">結束日期</td>
                            <td><input type="text" id="date_end" name="date_end" value="" onchange="updateData()"></td>
                        </tr>
                        <tr>
                            <td class="grid_2">週期</td>
                            <td>
                                <input type="radio" name="period" value="daily" onchange="updatePeriod('daily', os)">日&nbsp
                                <input type="radio" name="period" value="weekly" onchange="updatePeriod('weekly', os)">週&nbsp
                                <input type="radio" name="period" value="monthly" onchange="updatePeriod('monthly', os)">月&nbsp
                                <input type="radio" name="period" value="year" onchange="updatePeriod('year', os)">年&nbsp
                            </td>
                        </tr>
                    </table>

                    <form action="exportPng.php" method="POST" target="new">
                        <input type="text" name="data" value="" hidden=true id="exportPng">
                        <input type="submit" name="submit" value="匯出圖片" class="btn btn-warning" id="exportPng">
                    </form>
                    <form action="exportCsv.php" method="POST" target="new">
                        <input type="text" name="data" value="" hidden=true id="exportCsv">
                        <input type="submit" name="submit" value="匯出表格" class="btn btn-warning" id="exportCsv">
                    </form>
                    <!-- <a href="exportPng.php" target="_blank" class="btn btn-warning" id="exportPng">匯出圖片</a> -->
                    <!-- <a href="exportCsv.php" target="_blank" class="btn btn-warning" id="exportCsv">匯出表格</a> -->

                    <div id="chart_div" class="container_12" style="width: 900px; height: 500px;"></div><br>

                    <div>
                        <table id="data_div" class ="table table-hover" style="width: 100%">
                            <!-- data from updateTable()-->
                        </table>
                    </div>
                </div>

                <div class="panel-footer" style="text-align: center;">@Copyright HyXen</div>
            </div>
        </div>
	</body>
</html>