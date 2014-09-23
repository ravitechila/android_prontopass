	<?php
	/* 
		Webservice for Get-Subject-Details.
		Created By   : Pawan Patil
        Created Date : 22th September 2014
		How it works : We simply take subjectName & subTopicName & fetch question and answer from database
		               and send responses accordingly.
			Copyright@Techila Solutions
	*/
	
	include_once 'functions.php';
	function getDetails(){			 
		
		$db = new Main_Class();
	    $getResultList = array();$getResultList3 = array();
		$arrayList = array();
		$test =array();
		//Get Subject details from database
        $getQuerySet1 = "SELECT * FROM Subject_Details_T";		
	    
        $getQueryResult1 = mysql_query($getQuerySet1);		
		if(mysql_num_rows($getQueryResult1) > 0) {
			
			while($row2 = mysql_fetch_array($getQueryResult1)){
				$getData['subID'] = $row2['Subject_Id'];
				$getData['subjectName'] = $row2['Subject_name'];
				array_push($arrayList,$getData);
			}
			$newData01 =json_encode(array($arrayList));
			$newData01 =str_replace('\/', '/', $newData01);
			$newData01 =substr($newData01,1,strlen($newData01)-2);
			
		}else {
			
			$errorCode = "2";
			$errorMsg = "Registration Fail";
			$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}";
			return $newData;
		}
		
        //Get Subject details from database
        $getQuerySet = "SELECT s1.Subject_name,s1.Subject_Id,s2.Sub_topic_id,s2.Sub_topic_name FROM Subject_Details_T as s1 INNER JOIN Sub_Topics_Details_T as s2 ON s1.Subject_Id=s2.Subject_Id";		
	    
        $getQueryResult = mysql_query($getQuerySet);		
		if(mysql_num_rows($getQueryResult) > 0) {
			
			while($row = mysql_fetch_array($getQueryResult)){
				$getData1['subjectID'] = $row['Subject_Id'];
				$getData1['subTopicID'] = $row['Sub_topic_id'];
				$getData1['subTopicName'] = $row['Sub_topic_name'];
				array_push($getResultList,$getData1);
			}
			
			$countFirst = sizeof($arrayList);
			$countFirst2 = sizeof($getResultList);
			for($i=0;$i<$countFirst;$i++) {
				for($j=0;$j<$countFirst2;$j++){
					if($arrayList[$i]['subID'] == $getResultList[$j]['subjectID']){
						//$arrayList[$i]['subID'] = $getResultList[$j]['subjectID'];
						
						$subTopicName = $getResultList[$j]['subTopicName'];
						$GetDataResult['subTopicID'] = $getResultList[$j]['subTopicID'];
						$GetDataResult['SubName'] = $subTopicName;
						array_push($test,$GetDataResult);
					 }
				}
				$newData=json_encode(array($test));
			    $newData=str_replace('\/', '/', $newData);
			    $newData=substr($newData,1,strlen($newData)-2);
				if($i+1 == $countFirst){
				$g .='"'.$arrayList[$i]["subjectName"].'":'.$newData.'';
				} else {
				$g .='"'.$arrayList[$i]["subjectName"].'":'.$newData.',';
				}
				
				for($p=0;$p<$countFirst2;$p++){
				array_pop($test);
				}
			}
			    
			//$newData="{\"result\":[{".$g."}]}";
			//return $newData;
			
			$newData="{\"data\":{\"Error_Code\":\"1\",\"Error_Msg\":\"Success\",\"result\":[{".$g."}],\"subList\":".$newData01."}}";
			return $newData;
			
		} else {
			
			$errorCode = "2";
			$errorMsg = "Registration Fail";
			$newData = "{\"data\":{\"Error_Code\":\"".$errorCode."\",\"Error_Msg\":\"".$errorMsg."\"}}";
			return $newData;
		}
    }		
	
	echo getDetails();
?>