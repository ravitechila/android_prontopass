	<?php
	/* 
		Webservice for User Login.
		Created By   : Pawan Patil
        Created Date : 18th September 2014
		How it works : We simply take email Id and Password from user and matches with our db entry 
		               and give success and error responses accordingly.
			Copyright@Techila Solutions
	*/
	
	include_once 'functions.php';
	function getLoginDetails()
	{	
        $db = new Main_Class();
		$emailID = $_REQUEST['emailID']; 
		$Password = $_REQUEST['Password'];
		
		if( $emailID == "" || $Password == "" ){
			
			$result = $db->fieldsValidation(); //Call field validation function
		    return $result;
		}
		else{
		    
			$loginList = array();
			$dataQueryInfo = "SELECT l1.Stud_id,l1.Stud_Email_Id,r1.Stud_name FROM Login_Details_T as l1 INNER JOIN Register_Details_T as r1 ON l1.Stud_id=r1.Stud_id WHERE l1.Stud_Email_Id = '$emailID' AND l1.Password ='$Password'";
			$dataResultSet = mysql_query($dataQueryInfo);
			if(mysql_num_rows($dataResultSet) > 0) {
				while( $row = mysql_fetch_array($dataResultSet)){
					$Stud_id = $row['Stud_id'];
					$emailID = $row['Stud_Email_Id'];
					$userName = $row['Stud_name'];
				}
				$result = $db->loginSuccess($Stud_id,$emailID,$userName); // Call login success function
		        return $result;
					
		    }
			else {
				
				$result = $db->loginFail(); // Call login fail function
		        return $result;
			}
		}
	}		
	
	echo getLoginDetails();
?>