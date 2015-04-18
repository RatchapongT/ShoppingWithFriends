<?php

require("config.inc.php");


if (!empty($_POST)) {
     $r = 0;
    //$query        = " SELECT * FROM PROFILE WHERE Username = :user";
    $query        = "UPDATE REPORTED_ITEM SET readReport = 1 WHERE FriendID= :user";
    $query_params = array(
        ':user' => $_POST['Username']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = $ex;
        die(json_encode($response));
    }
    
    $response["success"] = 1;
    $response["message"] = "Success!";
    die(json_encode($response));
    

} else {
?>
	<h1>Username</h1> 
	<form action="update_read.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="Username" value="" /> 
	    <br /><br /> 

	    <input type="submit" value="Update Read" /> 
	</form>
	<?php
}
?>