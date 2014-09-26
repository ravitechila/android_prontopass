<?php 
/*
	*EditInformation page of Prontopass Admin Panel
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
$message ="";
//Get values from databse.
$qusID = $_GET['qusID'];
$getQueryResult = mysql_query("SELECT s1.Subject_Id,s1.Subject_name,s2.Sub_topic_id,s2.Sub_topic_name,s3.Question_id,s3.Question,s3.Ans1,s3.Ans3,s3.Correct_Ans2,s3.Ans4,s3.Ans_Description FROM Subject_Details_T as s1 INNER JOIN Sub_Topics_Details_T as s2 ON s1.Subject_Id=s2.Subject_Id INNER JOIN Question_Details_T as s3 ON s2.Sub_topic_id=s3.Sub_topic_id WHERE s3.Question_id='$qusID'");
while($row = mysql_fetch_array($getQueryResult)){
	$NSubject_Id = $row['Subject_Id'];
	$NSub_topic_id = $row['Sub_topic_id'];
	$NQuestion_id = $row['Question_id'];
	$NSubject_name = $row['Subject_name'];
	$NSub_topic_name = $row['Sub_topic_name'];
	$NQuestion = $row['Question'];
	$NAns1 = $row['Ans1'];
	$NAns3 = $row['Ans3'];
	$NAns4 = $row['Ans4'];
	$NCorrect_Ans2 = $row['Correct_Ans2'];
	$NAns_Description = $row['Ans_Description'];
}
//Update the inforamtion
if(isset($_POST['Submit'])){
	
	$subjectName = $_POST['subjectName']; //get post value
	$subtopicsName = $_POST['subtopicsName'];
	$question = $_POST['question']; //get post value
	$ans1 = $_POST['ans1'];
	$ans3 = $_POST['ans3']; //get post value
	$ans4 = $_POST['ans4'];
	$correctAns = $_POST['correctAns']; //get post value
	$description = $_POST['description'];
	mysql_query("UPDATE Subject_Details_T SET Subject_name='$subjectName' WHERE Subject_name='$NSubject_name' AND Subject_Id='$NSubject_Id'");
	
	mysql_query("UPDATE Sub_Topics_Details_T SET Sub_topic_name='$subtopicsName' WHERE Sub_topic_name='$NSub_topic_name'  AND Sub_topic_id='$NSub_topic_id'");
	
	mysql_query("UPDATE Question_Details_T SET Question='$question',Ans1='$ans1',Ans3='$ans3',Ans4='$ans4',Correct_Ans2='$correctAns',Ans_Description='$description' WHERE Question_id='$qusID'");
	
	echo "<script type='text/javascript'>
		 		window.location='View-records.php?flag=update';
		</script>";
}

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ProntoPass App</title>
<script type="text/javascript" src="jquery/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript"> 
      $(document).ready( function() {
        $('#show-message').delay(2000).fadeOut();
      });
    </script>
</head>

<body style="background:#f2f2f2">
<!-- Lest side bar -->
<?php include_once('database/leftsidebar.php');?>
<div class="right-sidebar">
	<div id="right-head-body">
    	<label id="lable-id2"><?php echo $_SESSION['userName']?> <img src="images/bullet.gif" width="7" height="7"><a href="ViewInformation.php"> Main Page</a> <img src="images/bullet.gif" width="7" height="7"> <a href="Add-more-records.php"> Add More Records </a> <img src="images/bullet.gif" width="7" height="7"> <a href="View-records.php"> View Records </a> <img src="images/bullet.gif" width="7" height="7"> Edit-information</label>
        <label id="lable-id3"><a href="Logout.php">Logout</a> </label>
    </div>
    <div id="right-sidebar-body">
          <?php if($message !=""){?>
    	  <div id="show-message">
          		<img src="images/dot.gif" title="" alt="" class="icon ic_s_success"> <?php echo $message; ?>
          </div>
          <?php } ?>
          <div id="add-records-body">
          <form method="post">
          
          	<table style="margin-left:2%;margin-top:2%;margin-bottom:2%">
            	<tr>
                	<td colspan="2"><label id="lable-title"><h2>Edit Details</h2></label></td>
                </tr>
            	<tr>
                	<td><label id="lable-id">Subject name :</label></td>
                    <td><input name="subjectName"  autofocus="autofocus" value="<?php echo $NSubject_name; ?>" class="std_textbox" type="text" autocomplete="off" tabindex="1" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Sub topic name :</label></td>
                    <td><input name="subtopicsName"  autofocus="autofocus" value="<?php echo $NSub_topic_name; ?>" class="std_textbox" type="text" autocomplete="off" tabindex="2" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Question :</label></td>
                    <td><input name="question"  autofocus="autofocus" value="<?php echo $NQuestion; ?>" class="std_textbox" type="text" autocomplete="off" tabindex="3" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Answer 1 :</label></td>
                    <td><input name="ans1"  autofocus="autofocus" value="<?php echo $NAns1; ?>" class="std_textbox" type="text" autocomplete="off" tabindex="4" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Answer 2 :</label></td>
                    <td><input name="ans3"  autofocus="autofocus" value="<?php echo $NAns3; ?>" class="std_textbox" type="text" autocomplete="off" tabindex="5" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Answer 3 :</label></td>
                    <td><input name="ans4"  autofocus="autofocus" value="<?php echo $NAns4; ?>" class="std_textbox" type="text" autocomplete="off" tabindex="6" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Correct Answer  :</label></td>
                    <td><input name="correctAns"  autofocus="autofocus" value="<?php echo $NCorrect_Ans2; ?>" class="std_textbox" type="text" autocomplete="off" tabindex="7" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Correct Answer Description  :</label></td>
                    <td>
                    <textarea name="description" rows="4" required="required" autofocus="autofocus" autocomplete="off" tabindex="8" class="stdtextarea"><?php echo $NAns_Description; ?></textarea>
                   </td>
                </tr>
                 <tr>
                	<td>&nbsp;&nbsp;</td>
                    <td> 
                    <button name="Submit" type="submit" id="login_submit" tabindex="8" style="margin-right: 9%;
width: 40%;
float: right;
height: 36px;">Submit</button></td>
                </tr>
            </table>
            </form>
          </div>
    </div>
  
</div><!-- right-sidebar end -->
</body>
</html>