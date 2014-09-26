<?php
/*
	*Delete Information page of Prontopass Admin Panel
	*Copyright@Techila Solutions
*/
session_start();
//Database Connection
include_once('database/DBConnect.php');
//Check session is set or not
if(!isset($_SESSION['userID'])){
	
	echo "<script type='text/javascript'>
		 		window.location='login.php';
			  </script>";
}
if(isset($_GET['qusID'])){               
	
	$qusID = $_GET['qusID'];
	$getQuery = "DELETE FROM Question_Details_T WHERE Question_id='$qusID'";
	mysql_query($getQuery);
	echo "<script type='text/javascript'>
			window.location='View-records.php?flag=Delete';
		</script>";
}
?>