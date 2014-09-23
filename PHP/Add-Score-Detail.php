	<?php
	/* 
		Webservice for Add Score Details.
		Created By   : Pawan Patil
        Created Date : 18th September 2014
		How it works : We simply take all information from client and insert it in Score Detail table
		               and send responses accordingly.
			Copyright@Techila Solutions
	*/
	
	include_once 'functions.php';
	function getScroeData(){			 
		
		$db = new Main_Class();
		
		$correctAnswers = $_REQUEST['correctAnswers'];
		$wrongAnswers = $_REQUEST['wrongAnswers'];
		$studentID = $_REQUEST['studentID'];
		$subjectID = $_REQUEST['subjectID'];
		$subTopicID = $_REQUEST['subTopicID'];
		$time = "NA";
		
		if( $correctAnswers == "" || $wrongAnswers == "" || $studentID == "" || $subjectID == "" || $subTopicID == "" || $time == "" ){
			
			 $result = $db->fieldsValidation(); //Call field validation function
		     return $result;
			 
		} else {
			
			 $tableName = 'Score_Details_T'; //Table Name
			 
			 $result = $db->insertScoreDB($tableName, $studentID, $subjectID, $subTopicID, $correctAnswers,$wrongAnswers, $time); //Call database insert function
			 return $result; // return response
			 
		}
				
    }		
	
	echo getScroeData();
?>