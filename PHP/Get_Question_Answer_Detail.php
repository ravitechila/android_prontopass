	<?php
	/* 
		Webservice for Get-Question-Answer.
		Created By   : Pawan Patil
        Created Date : 18th September 2014
		How it works : We simply take subjectName & subTopicName & fetch question and answer using this to value
		               and send responses accordingly.
			Copyright@Techila Solutions
	*/
	
	include_once 'functions.php';
	function getDetails(){			 
		
		$db = new Main_Class();
		date_default_timezone_set('Asia/Calcutta'); 
		//To get User registered Date and Time
		$Created_date = date('Y-m-d H:i:s');
		$subjectName = $_REQUEST['subjectName'];
		$subTopicName = $_REQUEST['subTopicName'];
		
		if( $subjectName == "" || $subTopicName == "" ){
			
			 $result = $db->fieldsValidation(); //Call field validation function
		     return $result;
			 
		} else {
			 
			 $result = $db->getQuestionAnswerDetails($subjectName, $subTopicName); //Call getQuestionAnswerDetails function
			 return $result; // return response
			 
		}
				
    }		
	
	echo getDetails();
?>