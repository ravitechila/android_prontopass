<?php
ob_start();
session_start();
if(isset($_SESSION['userID'])){
	
	unset($_SESSION['userID']);
	unset($_SESSION['userName']);
	echo "<script type='text/javascript'>
		 		window.location='login.php';
		  </script>";
		  
}
?>