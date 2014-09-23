<?php
  include_once 'functions.php';
  $db = new Main_Class();
  $tableName='insertinfo'; //Table Name
  $p='Rahulrrrrrr';
  $l='patilrrrrr';
  $data=array(
			  'NAME'=>''.$p.'',
			  'LASTNAME'=>''.$l.''
			  );
		 
  $res = $db->insertIntoDB($tableName, $data);
  echo $res;
?>