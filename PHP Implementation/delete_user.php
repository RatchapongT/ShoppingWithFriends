<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

//if posted data is not empty
if (!empty($_POST)) {


    $query        = "DELETE FROM users WHERE username = :user";
    //now lets update what :user should be
    $query_params = array(
        ':user' => $_POST['username']
    );
    
    //Now let's make run the query:
    try {
        // These two statements run the query against your database table. 
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = $ex;
        die(json_encode($response));
    }
    
    //fetch is an array of returned data.  If any data is returned,
    //we know that the username is already in use, so we murder our
    //page
    $row = $stmt->fetch();
    if ($row) {
        $response["success"] = 0;
        $response["message"] = "No user";
        die(json_encode($response));
    }
    
    $response["success"] = 1;
    $response["message"] = "Delete";
    die(json_encode($response));
    
} else {
?>
	<h1>Register</h1> 
	<form action="delete_user.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 

	    <input type="submit" value="Delete" /> 
		</form> 
	<?php
}

?>