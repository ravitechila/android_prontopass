<?php 
/*
	*Login page of Prontopass Admin Panel
	*Navigation of all pages 
	*Copyright@Techila Solutions
*/
session_start();
//Database Connection
include_once('database/DBConnect.php');
$message = "";
//Check user and password valid or not
if(isset($_POST['login'])){
	
	$adminName = $_POST['user']; //get post value
	$adminPassword = $_POST['password'];
	$getQuery = "SELECT * FROM Admin_Login_T WHERE adminName='$adminName' AND adminPassword='$adminPassword'";
	$getQueryResult = mysql_query($getQuery);
	
	if(mysql_num_rows($getQueryResult) > 0){
		while($row = mysql_fetch_array($getQueryResult)) {
			$ID = $row['Id'];
			$adminName = $row['adminName'];
		}
		$_SESSION['userID'] = $ID;
		$_SESSION['userName'] = $adminName;
		echo "<script type='text/javascript'>
		 		window.location='ViewInformation.php';
			  </script>";
	} 
	else {
		$message = 'Username and Password is invalid';
	}
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ProntoPass App</title>
<script type="text/javascript" src="jquery/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>
<img class="background-image" src="images/background.png">
<div class="login-body">
	<div id="sub-login-header">
    	<h3><i>ProntoPass</i><h3>
	</div>
    <div id="login-sub-body">
    <form method="post" name="Login-Form">
    	<table>
          <tr>
            <td><label id="lable-id">Username</label></td>
          </tr>
          <tr>
            <td>
            	<div class="input-field-login icon1 username-container">
                      <input name="user" id="user" autofocus="autofocus" value="" placeholder="Enter your username." class="std_textbox" type="text" autocomplete="off" tabindex="1" required="required">
                </div>
            </td>
          </tr>
           <tr>
            <td><label id="lable-id">Password</label></td>
          </tr>
          <tr>
            <td>
            	<div class="input-field-login icon1 password-container">
                      <input name="password" id="user" autofocus="autofocus" value="" placeholder="Enter your account password." class="std_textbox" type="password" autocomplete="off" tabindex="2" required="required">
                </div>
            </td>
          </tr>
          <tr>
            <td>
            	<div class="login-btn">
                     <button name="login" type="submit" id="login_submit" tabindex="3">Log in</button>
                </div>
            </td>
          </tr>
          <tr>
            <td>
            	<div id="error-msg">
                     <?php echo $message; ?>
                </div>
            </td>
          </tr>
        </table>
       </form>
    </div>
</div>
</body>
</html>