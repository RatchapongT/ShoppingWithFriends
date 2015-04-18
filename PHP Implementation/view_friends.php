<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

if (!empty($_POST)) {
	
$query = " 
            SELECT 
                FriendID
            FROM friend_list 
            WHERE 
                UserID = :UserID 
        ";
    
    $query_params = array(
		':UserID' => $_POST['UserID']
		
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database. Please Try Again!";
        die(json_encode($response));
        
    }

	//$row = $stmt->fetch();
	$have_data = false;
	//if ($row) {
	//	$have_data = true;
	//}
	$a=array();
	
	while ($row = $stmt->fetch()) {
		$have_data = true;
		array_push($a,$row['FriendID']);
	}

    if ($have_data) {
		$response["success"] = 1;
        $response["message"] = $a;
        die(json_encode($response));
    } else {
		$response["success"] = 0;
        $response["message"] = "No friend found!";
        die(json_encode($response));
	}
	
} else {
?>
	<h1>Register</h1> 
	<form action="view_friends.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="UserID" value="" /> 
	    <br /><br /> 
		
	    <input type="submit" value="View Friends" /> 
	</form>
	<?php
}

?>
