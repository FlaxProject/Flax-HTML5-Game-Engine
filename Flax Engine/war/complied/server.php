<?php

/*
 * This script allows write acess to .json files inside the WAR folder of the game.
 * To make sure this script only is run by the client code of the game I have included 
 * an auth_key which most match the calling clients key. 
 * 
 * @author Ciarán McCann
 */ 
 
 //This file most have write permissions on the server to work

/*********BELOW IS THE AUTH_KEY WHICH MOST BE MODIFED TO MAKE THE CLIENT CODE***********/
	define("AUTHORIZATION_KEY", 0);
/*********ABOVE IS THE AUTH_KEY WHICH MOST BE MODIFED TO MAKE THE CLIENT CODE***********/

	//DOTO AS EXTRA SEACUREITY ADD ONLY WRITES TO JSON FILES IN WAR DIR
	if(AUTHORIZATION_KEY == htmlentities($_POST['uid']))
	{
	
		//Secure $_POST input
		$fileName = htmlentities($_POST['fileName']);				
		$fileContents = htmlentities($_POST['fileContents']);


		$file = fopen($fileName, 'w') or die("File cant be opened");		//Opens file
		fwrite($file, $fileContents) or die("File cannot be written");				//Writes to file
		fclose($file);							//Close file
		
	}else{
		
		die("AUTHORIZATION_KEY did not match");
	}
		
		
		
?>
