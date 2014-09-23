<?php

$link = mysql_connect("localhost","phbjhqjx_prontop","prontopass!001");
if($link) {
	if(!mysql_select_db('phbjhqjx_prontopass',$link)){
		echo "<h1>Invalid Database Selected.</h1>";
		die; 
	}
} else { 
	echo "<h1>Database Connection Error.</h1>";
	die;
}
?>
