<?php
/* 
	Declare Main_Class 
	Created By   : Pawan Patil
    Created Date : 18th September 2014
	Copyright@Techila Solutions
*/
class Main_Class {

    private $db;
     
    //put your code here
    // constructor
    function __construct() {
        include_once 'db_connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

     /**
     * Insert All Information Into Database
     */
    public function insertIntoDB($tableName, $data, $emailID, $Password, $Name) {
	    
		$i =0;$keyValue ="";$paramValue ="";
	    foreach($data as $key => $value){
			if($i > 0){
				$c =',';
			}
			else{ $c =""; }
			$keyValue.= $c.$key;
			$paramValue.= $c."'".$value."'";
			$i++;
		}
			
        $ResultQuery ="INSERT INTO ".$tableName."(".$keyValue.") Values(".$paramValue.")";
		$Result = mysql_query($ResultQuery);
		$lastInsertId = mysql_insert_id();
		if(!empty($lastInsertId)){
			
			mysql_query("INSERT INTO Login_Details_T(Stud_id,Stud_Email_Id,Password) VALUES('$lastInsertId','$emailID','$Password')");
			
			$errorCode = "1";
			$errorMsg = "Registration Successfully";
			$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\",\"emailID\":\"".$emailID."\",\"userName\":\"".$Name."\"}}";
			echo $newData;
			
		} else {
		
			$errorCode = "2";
			$errorMsg = "Registration Fail";
			$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}";
			echo $newData;
			
		}
    }
	
	 /**
     * Insert All Score Information Into Database
     */
    public function insertScoreDB($tableName, $studentID, $subjectID, $subTopicID, $correctAnswers,$wrongAnswers, $time) {
	    
		$getQueryResult = mysql_query("SELECT * FROM Score_Details_T WHERE Subject_Id='$subjectID' AND Sub_topic_id='$subTopicID'");
		if(mysql_num_rows($getQueryResult) > 0){
			
			mysql_query("UPDATE Score_Details_T SET Stud_id='$studentID',Subject_Id='$subjectID',Sub_topic_id='$subTopicID',Correct_answers='$correctAnswers',Wrong_answers='$wrongAnswers',Total_timing='$time' WHERE Subject_Id='$subjectID' AND Sub_topic_id='$subTopicID' AND Stud_id='$studentID'");
			
			$errorCode = "1";
			$errorMsg = "Registration Successfully";
			$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}";
			echo $newData;
			
		} else {
		
		$data = array(
						  'Stud_id'=>''.$studentID.'',
						  'Subject_Id'=>''.$subjectID.'',
						  'Sub_topic_id'=>''.$subTopicID.'',
						  'Correct_answers'=>''.$correctAnswers.'',
						  'Wrong_answers'=>''.$wrongAnswers.'',
						  'Total_timing'=>''.$time.''
					); // Table fields name and value
						  
		$i =0;$keyValue ="";$paramValue ="";
	    foreach($data as $key => $value){
			if($i > 0){
				$c =',';
			}
			else{ $c =""; }
			$keyValue.= $c.$key;
			$paramValue.= $c."'".$value."'";
			$i++;
		}
			
        $ResultQuery ="INSERT INTO ".$tableName."(".$keyValue.") Values(".$paramValue.")";
		$Result = mysql_query($ResultQuery);
		$lastInsertId = mysql_insert_id();
		if(!empty($lastInsertId)){
			
			mysql_query("INSERT INTO Login_Details_T(Stud_id,Stud_Email_Id,Password) VALUES('$lastInsertId','$emailID','$Password')");
			
			$errorCode = "1";
			$errorMsg = "Registration Successfully";
			$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}";
			echo $newData;
			
		} else {
		
			$errorCode = "2";
			$errorMsg = "Registration Fail";
			$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}";
			echo $newData;
			
		}
	  }
    }
	
	/* If fields are empty then send field validation error */
	public function fieldsValidation() {
		
		$errorCode = "0";
		$errorMsg = "Please Send All Fields";
		$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}"; //Json Format Response
		echo $newData;
	}
	
	/* If Login Success Then Send Success Response To Device*/
	public function loginSuccess($Stud_id,$emailID,$userName) {
		
		$newData = "{\"data\":{\"Error_Code\":\"1\",\"Error_Msg\":\"Login Successful\",\"studentID\":\"".$Stud_id."\",\"emailID\":\"".$emailID."\",\"userName\":\"".$userName."\"}}";
		echo $newData; 
	    //Login Successful
	}
    
	/* If Login Failure Then Send Success Response To Device*/
	public function loginFail() {
		
		$errorCode = "2";
		$errorMsg = "Login Unsuccessful.Please Try Again";
		$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}"; //Json Format Response
		echo $newData;
	}
	
	/* Send All Question and Answer Details */
	public function getQuestionAnswerDetails($subjectName, $subTopicName) {
		
		$arrayList = array();
		$getQueryData = "SELECT Q1.Subject_Id,Q1.Sub_topic_id,Q1.Question,Q1.Ans1,Q1.Correct_Ans2,Q1.Ans3,Q1.Ans4 FROM Question_Details_T as Q1 INNER JOIN Subject_Details_T as S1 ON Q1.Subject_Id=S1.Subject_Id INNER JOIN Sub_Topics_Details_T as S2 ON Q1.Sub_topic_id=S2.Sub_topic_id WHERE S1.Subject_name='$subjectName' AND S2.Sub_topic_name='$subTopicName'";
		$getQueryResult = mysql_query($getQueryData);
		
			if( mysql_num_rows($getQueryResult) > 0){
				while( $row = mysql_fetch_array($getQueryResult)) {
						$getData['subjectID'] = $row['Subject_Id'];
						$getData['subTopicID'] = $row['Sub_topic_id'];
						$getData['Question'] = $row['Question'];
						$getData['Ans1'] = $row['Ans1'];
						$getData['Correct_Ans2'] = $row['Correct_Ans2'];
						$getData['Ans3'] = $row['Ans3'];
						$getData['Ans4'] = $row['Ans4'];
						array_push($arrayList,$getData); //Add one array into another array
				}
				
				$newData=json_encode(array($arrayList));
				$newData=str_replace('\/', '/', $newData);
				$newData=substr($newData,1,strlen($newData)-2);
			    
				$newData="{\"data\":{\"Error_Code\":\"1\",\"Error_Msg\":\"Success\",\"result\":".$newData."}}";
				echo $newData;
				
			} else {
				
				$errorCode = "2";
				$errorMsg = "Details Not Found";
				$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}"; //Json Format Response
				echo $newData;
			}
	}
}

?>