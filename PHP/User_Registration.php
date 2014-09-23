	<?php
	/* 
		Webservice for User Registration.
		Created By   : Pawan Patil
        Created Date : 18th September 2014
		How it works : We simply take all information from client and insert it in user_registration table
		               and send responses accordingly.
			Copyright@Techila Solutions
	*/
	
	include_once 'functions.php';
	function getUserRegistartion(){			 
		
		$db = new Main_Class();
		date_default_timezone_set('Asia/Calcutta'); 
		//To get User registered Date and Time
		$Created_date = date('Y-m-d H:i:s');
		$Name = $_REQUEST['Name'];
		$Qualification = $_REQUEST['Qualification'];
		$emailID = $_REQUEST['emailID'];
		$DOB = $_REQUEST['DOB'];
		$contactNO = $_REQUEST['contactNO'];
		$Address = $_REQUEST['Address'];
		$Password = $_REQUEST['Password'];
		
		if( $Name == "" || $Qualification == "" || $emailID == "" || $DOB == "" || $contactNO == "" || $Address == "" || $Password == "" ){
			
			 $result = $db->fieldsValidation(); //Call field validation function
		     return $result;
			 
		} else {
			 
			 $tableName = 'Register_Details_T'; //Table Name
			 $data = array(
						  'Stud_name'=>''.$Name.'',
						  'Stud_Qualification'=>''.$Qualification.'',
						  'Stud_Email_Id'=>''.$emailID.'',
						  'Stud_DOB'=>''.$DOB.'',
						  'Stud_ContactNo'=>''.$contactNO.'',
						  'Stud_Address'=>''.$Address.'',
						  'Stud_Password'=>''.$Password.'',
						  'Created_Date'=>''.$Created_date.''
						  ); // Table fields name and value
						  
			 $result = $db->insertIntoDB($tableName, $data, $emailID, $Password,$Name); //Call database insert function
			 return $result; // return response
			 
		}
				
    }		
	
	echo getUserRegistartion();
?>