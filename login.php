<?php
	session_start();
	// unset($_SESSION['account']);
	require("db_config.php");
    require("db_class.php");

    if(isset($_SESSION['account'])){
    	header("location:index.php");
    }

    if(!is_null($_POST['submit'])){
    	$db = new DB();
	    $db->connect_db($_DB['host'], $_DB['username'], $_DB['password'], $_DB['dbname']);

	    $account = $_POST['account'];
	    $password = $_POST['password'];

		$sql = "SELECT * FROM user WHERE account = \"".$account."\" AND password = \"".$password."\";";
		$db->query($sql);

		// search and compare all company
    	if($user = $db->fetch_array()){
			$_SESSION['identity'] = "user";
			$_SESSION['account'] = $user['account'];
			header('Location: http://www.idlefox.idv.tw/hyxen/index.php');
			$error = "已經登入了";
    	}else{
    		$error = "帳號或密碼錯誤";
    	}

    	// judge whether it is author ot not
    	if($_POST['email'] == "root" && $_POST['password'] == "fourteen"){
    		$_SESSION['identity'] = "admin";
    		$_SESSION['account'] = "root";
    		header('Location: http://www.idlefox.idv.tw/hyxen/index.php');
    	}
    }
?>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
	</head>
	<style type="text/css">
		.content-700{
			width: 700px;
			margin: 0px auto;
		}
		.right{
			float: right;
		}
		.error{
			color: red;
		}
	</style>
	<body>
		<div class='panel panel-primary content-700' style="margin-top: 100px;">
			<div class='panel-heading'>Login to HyXen Download Statistic Pages</div>
			<div class='panel-body'>
				
				<form action="login.php" method="post" class="form-horizontal">
					<div class='form-group'>
						<label for="email" class="col-sm-2 control-label">Email</label>
						<div class='col-sm-10'>
							<input type="email" class="form-control" name="account" placeholder="contemplation8213@hyxen.com">
						</div>
					</div>
					<div class='form-group'>
						<label for="password" class="col-sm-2 control-label">Password</label>
						<div class='col-sm-10'>
							<input type="password" class="form-control" name="password">
						</div>
					</div>
					<div class="error right"><?php echo $error; ?></div><br />
					<input type="submit" class="btn btn-default right" value="Login" name="submit">
				</form>
			</div>
		</div>
	</body>
</html>