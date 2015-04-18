<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
    //gets user's info based off of a username.
    $query = " 
            SELECT 
                username, 
                password
            FROM users 
            WHERE 
                username = :username 
        ";
    
    $query_params = array(
		':username' => $_POST['FriendID']
		
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
    
    //This will be the variable to determine whether or not the user's information is correct.
    //we initialize it as false.
    $validated_info = false;
	$login_ok = false;
	$add_ok = false;
    
    //fetching all the rows from the query
    $row = $stmt->fetch();
	
	
    if ($row) {
        //if we encrypted the password, we would unencrypt it here, but in our case we just
        //compare the two passwords
        if (strtolower($_POST['FriendID']) === strtolower($row['username'])) {
            $login_ok = true;
        }
    }
    
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
    if ($login_ok) {
        $query = " 
            SELECT 
                *
            FROM friend_list 
            WHERE 
                UserID = :UserID AND FriendID = :username
        ";
    
		$query_params = array(
			':UserID' => $_POST['UserID'],
			':username' => $_POST['FriendID']
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
			$response["message"] = "Database Error1. Please Try Again!";
			die(json_encode($response));
			
		}
		$row = $stmt->fetch();
		if (!$row) {
			$add_ok = true;
		}
		if ($add_ok) {
			 
			 $query = "INSERT INTO friend_list ( UserID, FriendID ) VALUES ( :username, :UserID ) ";
			 $query_params = array(
			':UserID' => $_POST['UserID'],
			':username' => $_POST['FriendID']
			);
			 try {
				$stmt   = $db->prepare($query);
				$result = $stmt->execute($query_params);
			}
			catch (PDOException $ex) {
				// For testing, you could use a die and message. 
				//die("Failed to run query: " . $ex->getMessage());
				
				//or just use this use this one:
				$response["success"] = 0;
				$response["message"] = "Database Error2. Please Try Again!";
				die(json_encode($response));
			}
			$query = "INSERT INTO friend_list ( UserID, FriendID ) VALUES ( :UserID, :username ) ";
			$query_params = array(
			':UserID' => $_POST['UserID'],
			':username' => $_POST['FriendID']
			);
			 try {
				$stmt   = $db->prepare($query);
				$result = $stmt->execute($query_params);
			}
			catch (PDOException $ex) {
				// For testing, you could use a die and message. 
				//die("Failed to run query: " . $ex->getMessage());
				
				//or just use this use this one:
				$response["success"] = 0;
				$response["message"] = "Database Error2. Please Try Again!";
				die(json_encode($response));
			}
			
			$response["success"] = 1;
			$response["message"] = "Add Friend Successful!";
			die(json_encode($response));
			
		} else {
			$response["success"] = 0;
			$response["message"] = "You are already friends.";
			die(json_encode($response));
		}
		
    
    } else {
        $response["success"] = 0;
        $response["message"] = "No friend found!";
        die(json_encode($response));
    }
} else {
	
?>
		<h1>Add Friend</h1> 
		<form action="add_friend.php" method="post"> 
		    Enter Friend Username:<br /> 
		    <input type="text" name="FriendID" placeholder="FriendID" /> 
		    <br /><br /> 
			<input type="text" name="UserID" placeholder="UserID" /> 
		    <br /><br /> 
		   <input type="submit" value="Submit" /> 
		</form> 
		
	<?php
}
?> 

