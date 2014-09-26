<?php 
/*
	*View Records page of Prontopass Admin Panel
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
if($_GET['flag'] =='update'){
	
	$message ="Record Updated Successfully";
} else if($_GET['flag'] =='Delete'){
	$message ="Record Deleted Successfully";
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
		<?php echo $_SESSION['userName']?> 	<img src="images/bullet.gif" width="7" height="7"> <a href="ViewInformation.php">Main Page</a> <img src="images/bullet.gif" width="7" height="7"> <a href="Add-more-records.php">Add More Records </a>	<img src="images/bullet.gif" width="7" height="7"> View Records</label>
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
          	<table style="margin-left:1%;margin-top:2%;margin-bottom:2%">
            	<tr>
                	<td colspan="8"><label id="lable-title"><h2>Submit Details</h2></label></td>
                </tr>
            	<tr>
                	<td class="table-title">Subject Name</td>
                    <td class="table-title">Topic Name</td>
                    <td class="table-title">Question</td>
                    <td class="table-title">Answer1</td>
                    <td class="table-title">Answer2</td>
                    <td class="table-title">Answer3</td>
                    <td class="table-title">Correct-Ans</td>
                    <td class="table-title">Description</td>
                    <td class="table-title">Action</td>
                </tr>
                
                  <?php
				  /* Pagination Code */
			   $per_page = 7;
			   $listing_type = $_GET['requestID'];
			   $query = "SELECT count(Question_id) FROM Question_Details_T";
			   $page_query = mysql_query($query);
			   $pages = ceil(mysql_result($page_query, 0)/$per_page);     //No of pages
			   $page = (isset($_GET['page'])) ? (int)$_GET['page'] : 	1;
			   $start=($page-1) * $per_page;
			   
				  	$getQueryResult = mysql_query("SELECT s1.Subject_Id,s1.Subject_name,s2.Sub_topic_id,s2.Sub_topic_name,s3.Question_id,s3.Question,s3.Ans1,s3.Ans3,s3.Correct_Ans2,s3.Ans4,s3.Ans_Description FROM Subject_Details_T as s1 INNER JOIN Sub_Topics_Details_T as s2 ON s1.Subject_Id=s2.Subject_Id INNER JOIN Question_Details_T as s3 ON s2.Sub_topic_id=s3.Sub_topic_id ORDER BY s3.Question_id ASC LIMIT $start,$per_page");
					if(mysql_num_rows($getQueryResult) > 0){
						while($getData = mysql_fetch_array($getQueryResult)){
						
				  ?>
                  <tr>
                	<td class="table-title-result"><?php echo $getData['Subject_name']; ?></td>
                    <td class="table-title-result"><?php echo $getData['Sub_topic_name']; ?></td>
                    <td class="table-title-result"><?php echo $getData['Question']; ?></td>
                    <td class="table-title-result"><?php echo $getData['Ans1']; ?></td>
                    <td class="table-title-result"><?php echo $getData['Ans3']; ?></td>
                    <td class="table-title-result"><?php echo $getData['Ans4']; ?></td>
                    <td class="table-title-result"><?php echo $getData['Correct_Ans2']; ?></td>
                    <td class="table-title-result"><?php echo $getData['Ans_Description']; ?></td>
                    <td class="table-title-result">
                    <a href="Edit-information.php?qusID=<?php echo $getData['Question_id']; ?>"><img src="images/edit.png" title="" alt="edit" ></a>&nbsp;&nbsp;
                   <a href="DeleteInfo.php?qusID=<?php echo $getData['Question_id']; ?>" onClick="return confirm('Are you sure, You Want to Delete Record?');"> <img src="images/delete.png" title="" alt="delete" ></a></td>
                    </tr>
                    <?php } }else{?>
                    <tr>
                    <td colspan="9" style="text-align:center">Result Not Found</td>
                    </tr>
                    <?php } ?>
                    <tr>
							<td colspan="9">
								<div style="height:20px;margin-left:0px;float:left;margin-top:20px">  
									<?php  
                                        /* Code For Next & Previous button for pagination */									
										if($pages>=1 &&  $page <= $pages){ 
											if($page <=1){
												echo "<span id='page_links' style='font-weight:bold;'>First << </span>";
												echo "<span id='page_links' style='font-weight:bold;'>Prev < </span>";
											}
											else{
												$j = $page - 1;
												echo '<a style="margin-left: 10px;" href="?page=1'.'&pg=P">'.'First <<'.'</a>';
												echo '<a style="margin-left: 10px;" href="?page='.$j.'&pg=P2">'.'< Prev</a>';
											}
											for($x=1; $x<=$pages; $x++){
												echo '<a style="margin-left: 10px;" href="?page='.$x.'&pg=P3" id="p1">'.$x.'</a>';
											}
											if($page == $pages ){
												echo "<span id='page_links' style='font-weight:bold;'>  Next ></span>";
												echo "<span id='page_links' style='font-weight:bold;'>  Last >></span>";
											}
											else{
												$k = $page + 1;
												echo '<a style="margin-left: 10px;" href="?page='.$k.'&pg=P4">'.'Next >'.'</a>';
												echo '<a style="margin-left: 10px;" href="?page='.$pages.'&pg=P5">'.'Last >>'.'</a>';
											}
										}
									?> 
											</div>
										</td>
									</tr>
            </table>
            </form>
          </div>
    </div>
  
</div><!-- right-sidebar end -->
</body>
</html>