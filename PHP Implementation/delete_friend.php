<?php

require("config.inc.php");


if (!empty($_POST)) {
    
    
    $query        = "DELETE FROM friend_list WHERE FriendID = :FriendID AND UserID = :UserID;";
    $query_params = array(
        ':UserID' => $_POST['UserID'],
        ':FriendID' => $_POST['FriendID']


    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error. Please Try Again!";
        die(json_encode($response));
    }

    $response["success"] = 1;
        $response["message"] = "Success";
        die(json_encode($response));

} else {
?>
	<h1>Username</h1> 
	<form action="delete_friend.php" method="post"> 
	    FriendID :<br /> 
	    <input type="text" name="FriendID" value="" /> 
	    <br /><br /> 
            UserID :<br /> 
	    <input type="text" name="UserID" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Delete" /> 
	</form>
	<?php
}
?>