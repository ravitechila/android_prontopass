<?php 
/*
	*ViewInformation page of Prontopass Admin Panel
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
//Add SubTopic Records into database
if(isset($_POST['Submit_subtopic'])){
	
	$subjectName = $_POST['subjectName']; //get post value
	$subtopicsName = $_POST['subtopicsName'];
	//select subject ID
	$getQueryResult = mysql_query("SELECT Subject_Id FROM Subject_Details_T WHERE Subject_name='$subjectName'");
	while($getID = mysql_fetch_array($getQueryResult)){
		$Subject_Id = $getID['Subject_Id'];
	}
	
    mysql_query("INSERT INTO Sub_Topics_Details_T(Subject_Id,Sub_topic_name) VALUES('$Subject_Id','$subtopicsName')");
    $lastInsertId = mysql_insert_id(); //insert question and answer into database
	
	if(!empty($lastInsertId)){
		$message ="Records added successfully.";
	} else {
		$message ="Records not added.";
	}
	
}
//Add Question & Answer Records into database
if(isset($_POST['Submit_QAns'])){
	
	$subjectName = $_POST['subjectName2']; //get post value
	$subtopicsName = $_POST['subtopicsName2'];
	$question = $_POST['question']; //get post value
	$ans1 = $_POST['ans1'];
	$ans2 = $_POST['ans2']; //get post value
	$ans3 = $_POST['ans3'];
	$correctAns = $_POST['correctAns']; //get post value
	$description = $_POST['description'];
	//select subject ID
	$getQueryResult = mysql_query("SELECT Subject_Id FROM Subject_Details_T WHERE Subject_name='$subjectName'");
	while($getID = mysql_fetch_array($getQueryResult)){
		$Subject_Id = $getID['Subject_Id'];
	}
	//select Sub topic ID
	$getSubQueryResult = mysql_query("SELECT Sub_topic_id FROM Sub_Topics_Details_T WHERE Subject_Id='$Subject_Id' AND Sub_topic_name='$subtopicsName'");
	while($getSubID = mysql_fetch_array($getSubQueryResult)){
		$Sub_topic_id = $getSubID['Sub_topic_id'];
	}
   mysql_query("INSERT INTO 
					Question_Details_T(Subject_Id,Sub_topic_id,Question,Ans1,Correct_Ans2,Ans3,Ans4,Ans_Description) 							VALUES('$Subject_Id','$Sub_topic_id','$question','$ans1','$correctAns','$ans2','$ans3','$description')");
	$lastInsertId = mysql_insert_id(); //insert question and answer into database
	
	if(!empty($lastInsertId)){
		$message ="Records added successfully.";
	} else {
		$message ="Records not added.";
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
    	<label id="lable-id2">
			<?php echo $_SESSION['userName']?> <img src="images/bullet.gif" width="7" height="7"> <a href="ViewInformation.php">Main Page</a> <img src="images/bullet.gif" width="7" height="7"> Add More Records 
        </label>
        <label id="lable-id3"><a href="Logout.php">Logout</a> </label>
    </div>
    <div id="right-sidebar-body" style="height:830px">
          <?php if($message !=""){?>
    	  <div id="show-message">
          		<img src="images/dot.gif" title="" alt="" class="icon ic_s_success"> <?php echo $message; ?>
          </div>
          <?php } ?>
          <div id="add-records-body">
          <form method="post">
          	<table style="margin-left:2%;margin-top:2%;margin-bottom:2%;float:left">
            	<tr>
                	<td colspan="2"><label id="lable-title"><h2>Add Subtopic</h2></label></td>
                </tr>
            	<tr>
                	<td><label id="lable-id">Select Subject Name :</label></td>
                    <td>
                    <select name="subjectName" autofocus="autofocus" autocomplete="off"  required="required" class="std_textbox"> 
                    	<option value="">Select Subject Name</option>
                    	<?php
						$getSubjectQuery = mysql_query("SELECT Subject_name FROM Subject_Details_T");
						if(mysql_num_rows($getSubjectQuery) > 0){
							while($row = mysql_fetch_array($getSubjectQuery)){
						?>
                    	<option value="<?php echo $row['Subject_name'];?>"><?php echo $row['Subject_name'];?></option>
						<?php } }else{ ?>
                    	<option>No Subject Available</option>
                    	<?php }?>
                    </select>
                    </td>
                </tr>
                <tr>
                	<td><label id="lable-id">Sub topic name :</label></td>
                    <td><input name="subtopicsName"  autofocus="autofocus" value="" placeholder=" Enter Sub topic name." class="std_textbox" type="text" autocomplete="off" required="required"></td>
                </tr>
                <tr>
                	<td>&nbsp;&nbsp;</td>
                    <td> 
                    <button name="Submit_subtopic" type="submit" id="login_submit" tabindex="8" style="margin-right: 9%;
width: 40%;
float: right;
height: 36px;">Submit</button></td>
                </tr>
            </table>
            </form>
            <!-- Add Question and Answer Details -->
            <form method="post">
          	<table style="margin-left:2%;margin-top:2%;margin-bottom:2%;float:left">
            	<tr>
                	<td colspan="2"><label id="lable-title"><h2>Add Question & Answer</h2></label></td>
                </tr>
            	<tr>
                	<td><label id="lable-id">Select Subject Name :</label></td>
                    <td>
                    <select name="subjectName2" autofocus="autofocus" autocomplete="off"  required="required" class="std_textbox">
                    	<option value="">Select Subject Name</option>
                    	<?php
						//Select All Subject Name
						$getSubjectQuery = mysql_query("SELECT Subject_name FROM Subject_Details_T");
						if(mysql_num_rows($getSubjectQuery) > 0){
							while($row = mysql_fetch_array($getSubjectQuery)){
						?>
                    	<option value="<?php echo $row['Subject_name'];?>"><?php echo $row['Subject_name'];?></option>
						<?php } }else{ ?>
                    	<option>No Subject Available</option>
                    	<?php }?>
                    </select>
                    </td>
                </tr>
                <tr>
                	<td><label id="lable-id">Select Sub Topic Name :</label></td>
                    <td>
                    <select name="subtopicsName2" autofocus="autofocus" autocomplete="off"  required="required" class="std_textbox">
                    	<option value="">Select Sub Topic Name</option>
                    	<?php
						//Select All Subtopic Name
						$getSubTopicQuery = mysql_query("SELECT Sub_topic_name FROM Sub_Topics_Details_T");
						if(mysql_num_rows($getSubTopicQuery) > 0){
							while($row1 = mysql_fetch_array($getSubTopicQuery)){
						?>
                    	<option value="<?php echo $row1['Sub_topic_name'];?>"><?php echo $row1['Sub_topic_name'];?></option>
						<?php } }else{ ?>
                    	<option>No Sub Topic Not Available</option>
                    	<?php }?>
                    </select>
                    </td>
                </tr>
                <tr>
                	<td><label id="lable-id">Sub topic name :</label></td>
                    <td><input name="subtopicsName"  autofocus="autofocus" value="" placeholder=" Enter Sub topic name." class="std_textbox" type="text" autocomplete="off" required="required"></td>
                </tr>
                 <tr>
                	<td><label id="lable-id">Question :</label></td>
                    <td><input name="question"  autofocus="autofocus" value="" placeholder=" Enter Question." class="std_textbox" type="text" autocomplete="off" tabindex="3" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Answer 1 :</label></td>
                    <td><input name="ans1"  autofocus="autofocus" value="" placeholder=" Enter Answer 1." class="std_textbox" type="text" autocomplete="off" tabindex="4" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Answer 2 :</label></td>
                    <td><input name="ans2"  autofocus="autofocus" value="" placeholder=" Enter Answer 2." class="std_textbox" type="text" autocomplete="off" tabindex="5" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Answer 3 :</label></td>
                    <td><input name="ans3"  autofocus="autofocus" value="" placeholder=" Enter Answer 3." class="std_textbox" type="text" autocomplete="off" tabindex="6" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Correct Answer  :</label></td>
                    <td><input name="correctAns"  autofocus="autofocus" value="" placeholder=" Enter Correct Answer." class="std_textbox" type="text" autocomplete="off" tabindex="7" required="required"></td>
                </tr>
                <tr>
                	<td><label id="lable-id">Correct Answer Description  :</label></td>
                    <td>
                    <textarea name="description" rows="4" required="required" autofocus="autofocus" autocomplete="off" tabindex="8" class="stdtextarea"></textarea>
                   </td>
                </tr>
                <tr>
                	<td>&nbsp;&nbsp;</td>
                    <td> 
                    <button name="Submit_QAns" type="submit" id="login_submit" tabindex="8" style="margin-right: 9%;
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