<?php
    require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);
    
    $apps = array();
    $search_app = "SELECT * FROM `app_list_google`";
    $db->query($search_app);
    while($app = $db->fetch_array()){
        $apps[$app['google_name']] = $app['name'];
    }

    $options = array();
    $search_option = "SELECT * FROM `option_list`";
    $db->query($search_option);
    while($option = $db->fetch_array()){
        $options[$option['name_google']] = $option['name'];
    }
?>
<!DOCTYPE html>
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
    <script>
        // declare variable
        var response;
        var app = [];
        var option = "";
        var date_start = "";
        var date_end = "";
        var period = "";
        var os = "Android";

        // inital datepicker
        $(function() {
            $( "#date_start" ).datepicker();
            $( "#date_end" ).datepicker();
        });

        // init the variable and view
        $(document).ready(function(){
            // init date
            var d = new Date();
            var date_end = d.getFullYear() + "-" +dateAddZero(d.getMonth()+1) + "-" + dateAddZero(d.getDate());
            var date_start = d.getFullYear() + "-" +dateAddZero(d.getMonth()) + "-" + dateAddZero(d.getDate());
            var opt = "";
            document.getElementById("date_end").value = date_end.substring(5,7)+"/"+date_end.substring(8,10)+"/"+date_end.substring(0,4);
            document.getElementById("date_start").value = date_start.substring(5,7)+"/"+date_start.substring(8,10)+"/"+date_start.substring(0,4);

            // init app
            app = ["RailTimeline", "SpeedDetectorEvo", "Barcode"];
            var apps = document.getElementsByName("app");
            for(var i=0; i<apps.length; i++){
                if(apps[i].value == "Barcode"){
                    apps[i].setAttribute("checked",true);
                }else if(apps[i].value == "RailTimeline"){
                    apps[i].setAttribute("checked",true);
                }else if(apps[i].value == "SpeedDetectorEvo"){
                    apps[i].setAttribute("checked",true);
                }
            }

            // init option
            var options = document.getElementsByName("option");
            option = "daily_device_installs";
            for(var i=0; i<options.length; i++){
                if(options[i].value == "daily_device_installs"){
                    options[i].setAttribute("checked", true);
                }
            }

            // init period
            var periods = document.getElementsByName("period");
            period = "daily";
            for(var i=0; i<periods.length; i++){
                if(periods[i].value == "daily"){
                    periods[i].setAttribute("checked", true);
                }
            }

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
            // get date
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

            // get app
            app = [];
            var apps = document.getElementsByName("app");
            for (var i=0; i < apps.length; i++) {
                if(apps[i].checked){
                    app.push(apps[i].value);
                }
            }
            // get option
            var options = document.getElementsByName("option");
            for (var i=0; i < options.length; i++) {
                if(options[i].checked){
                    option = options[i].value;
                }
            }

            // get period
            var periods = document.getElementsByName("period");
            for (var i=0; i < periods.length; i++) {
                if(periods[i].checked){
                    period = periods[i].value;
                }
            }

            //edit title
            var title_os = document.getElementById("title-os");
            title_os.innerHTML = os;
            var title_duration = document.getElementById("title-duration");
            title_duration.innerHTML = date_start + " ~ " + date_end;

            //ajax
            var get = "comment_getdata.php?os="+ os +"&app=" + app + "&option=" + option + "&ds=" + date_start + "&de=" + date_end + "&period=" + period;
            var xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange=function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    // prompt("Please enter your name", xmlhttp.responseText);
                    response = JSON.parse(xmlhttp.responseText);
                    document.getElementById("exportPng").value = get;
                    drawVisualization();
                    updateTable();
                }
            }
            xmlhttp.open("GET",get,true);
            xmlhttp.send();
            
        }
        
        // google chart
        google.load('visualization', '1', {packages: ['annotatedtimeline']});
        function drawVisualization() {
            var data = new google.visualization.DataTable();
            var displayer = document.getElementById("data");
            data.addColumn('date', 'Date');
            for(var i=0; i<app.length; i++){
                data.addColumn('number', app[i]);
                data.addColumn('string', '備註');
            }

            for(var j=0; j<Object.keys(response['dates']).length; j++){
                var row = [];
                row.push(new Date(response['dates'][j]));
                for(var a=0; a<app.length; a++){
                    row.push(parseFloat(response[app[a]][response['dates'][j]][option]));
                    if(response[app[a]][response['dates'][j]]['comments']){
                        row.push(response[app[a]][response['dates'][j]]['comments']);    
                    }else{
                        row.push(undefined);
                    }
                }
                data.addRows([row]);
            }

            var annotatedtimeline = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
            var chart_option = {
                'displayAnnotations': true,
                "showRowNumber" : true,
                "displayZoomButtons" : false,
                "legendPosition" : "newRow",
                "displayExactValues" : false,
                "dateFormat" : "yyyy-MM-dd",
                // "displayLegendDots" : false
                // "displayDateBarSeparator" : true
            }

            var view = new google.visualization.DataView(data);
            view.setColumns([0]);
            
            annotatedtimeline.draw(data, chart_option);
        }
        // google.setOnLoadCallback(drawVisualization);

        function updateTable(){
            var data_div = document.getElementById("data_div");
            var innerData = "";
            var table_title = "<tr><th>日期</th>";
            for(var i=0; i<app.length; i++){
                table_title += "<th>" + app[i] + "</th><th>備註</th>";
            }
            table_title += "</tr>";
            innerData +=  table_title;
            for(var i=0; i<Object.keys(response['dates']).length; i++){
                var table_data = "<tr><td>" + response['dates'][i] + "</td>";
                for(var j=0; j<app.length; j++){
                    var comment = response[app[j]][response['dates'][i]]['comments'];
                    if(comment == ""){
                        comment = "更新紀錄";
                    }
                    var appname = app[j];
                    var date = response['dates'][i];
                    var variable = appname + "," + date;
                    table_data = table_data + "<td>" + response[app[j]][response['dates'][i]][option] + "</td><td><div onclick='promptComment(\""+appname+"\", \""+date+"\")'>" + comment + "</div></td>";
                }
                table_data += "</tr>";
                innerData += table_data;
            }
            data_div.innerHTML = innerData;
            document.getElementById("exportCsv").value = innerData;
        }

        function chooseAll(appOrOption){
            var status = "";
            var bool = "";
            if (appOrOption == "app") {
                bool = "app";
            }else if(appOrOption == "option"){
                bool = "option";
            }else{
                alert("error: not exist value");
            };
            status = document.getElementById("allapp").checked;
            if(status == true){
                var apps = document.getElementsByName("app");
                for(var i=0; i<apps.length; i++){
                    apps[i].setAttribute("checked",true);
                }
            }else{
                var apps = document.getElementsByName("app");
                for(var i=0; i<apps.length; i++){
                    apps[i].removeAttribute("checked");
                }
            }
            updateData();
        }

        function removeChart(){
            var chart = document.getElementById("chart_div");
            if (chart.className == "container_12 chart_div") {
                chart.className = "container_12 chart_div_remove";
            }else{
                chart.className = "container_12 chart_div";
            };
        }

        function updateComment(appname, date, comment){
            // alert(appname + "," + date + "," + comment);
            var get = "update_comment.php?os="+ os +"&appname=" + appname + "&date=" + date + "&comment=" + comment;
            // alert(get);
            var xmlhttp=new XMLHttpRequest();
            xmlhttp.onreadystatechange=function() {
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    // alert(xmlhttp.responseText);
                    updateData();
                }
            }
            xmlhttp.open("GET",get,true);
            xmlhttp.send();
        }

        function promptComment(appname, date){
            var comment = prompt("請輸入要紀錄的事件", "ex:排名、Bug、APP更新...");
            if (comment != "") {
                updateComment(appname,date,comment);
            };
        }

    </script>
    <style type="text/css">
        .inline-float{
            float: left;
        }
        #removeChart{
            margin: 10px;
            margin-top: -15px;
            float: right;
        }
        .chart_div_remove{
            margin-bottom: 30px;
            position:absolute;
            left:-9999px;
            width: 900px;
            height: 400px;
        }
        .chart_div{
            margin-bottom: 30px;
            width: 900px;
            height: 400px;
        }
    </style>
    <body>
        <div id="navgation" class="navbar navbar-default">
            <div class="container-fluid container_12">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.php">HyXen</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="index.php">Home</a></li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Android&nbsp<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li class="active"><a href="android_apps.php">多支APP比較</a></li>
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
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">下載量波動紀錄&nbsp<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li class="active"><a href="comment_android.php">Android</a></li>
                                <li><a href="comment_iOS.php">iOS</a></li>
                            </ul>
                        </li>
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
                            <td class="grid_2">APP</td>
                            <td><nav><input type="checkbox" id="allapp" value="all" onchange="chooseAll('app')">全部&nbsp&nbsp</nav>
                                <?php
                                foreach ($apps as $key => $app) { 
                                    echo '<nav class="inline-float"><input type="checkbox" name="app" value='.$key.' onchange="updateData()">'.$app.'&nbsp&nbsp</nav>';
                                }
                            ?></td>
                        </tr>
                        <tr>
                            <td class="grid_2">資料</td>
                            <td><?php
                                foreach ($options as $key => $option) {
                                    echo "<nav class='inline-float'><input type='radio' name='option' value=".$key." onchange='updateData()'>".$option."</nav>";
                                }
                            ?></td>
                        </tr>
                        <tr>
                            <td class="grid_2">開始日期</td>
                            <td><input type="text" id="date_start" name="date_start" value="" onchange="updateData()"></td>
                        </tr>
                        <tr>
                            <td class="grid_2">結束日期</td>
                            <td><input type="text" id="date_end" name="date_end" value="" onchange="updateData()"></td>
                        </tr>
                    </table>

                    <Button class="btn btn-info" id="removeChart" onclick="removeChart()">隱藏圖表</Button>
                    <form action="exportPng.php" method="POST" target="new">
                        <input type="text" name="data" value="" hidden=true id="exportPng">
                        <input type="submit" name="submit" value="匯出圖片" class="btn btn-warning" id="exportPng">
                    </form>
                    <form action="exportCsv.php" method="POST" target="new">
                        <input type="text" name="data" value="" hidden=true id="exportCsv">
                        <input type="submit" name="submit" value="匯出表格" class="btn btn-warning" id="exportCsv">
                    </form>

                    <div id="chart_div" class="container_12 chart_div"></div><br>

                    <table id="data_div" class ="table table-hover" style="width: 900px;">
                        <!-- data from updateTable()-->
                    </table>
                </div>
                <div class="panel-footer" style="text-align: center;">@Copyright HyXen</div>
            </div>
        </div>
            
    </body>
</html>