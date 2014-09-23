<?php 
	require("db_config.php");
    require("db_class.php");
    $db = new DB();
    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

    $os = $_REQUEST["os"];
    $appname = $_REQUEST["appname"];
    $date = $_REQUEST["date"];
    $comment = $_REQUEST["comment"];
    $table = "";
    
    if($os == "Android"){
    	$table = "downloads";
    }else{
    	$table = "i_downloads";
    }

    $sql = "UPDATE ".$table." SET comments = \"".$comment."\" where appname = \"".$appname."\" and date = \"".$date."\"";
    // echo $sql;	
    $db->query($sql);
    
?>