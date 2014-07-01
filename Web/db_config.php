<?php
	//1. start session
	//2. set up the configuration of database
	session_start();
	global $_DB;
    $_DB['host'] = "localhost";
    $_DB['username'] = "root";
    $_DB['password'] = "";
    $_DB['dbname'] = "hyxen";
?>