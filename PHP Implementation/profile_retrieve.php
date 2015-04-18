<?php

require("config.inc.php");


if (!empty($_POST)) {
    
    
    $query        = " SELECT * FROM PROFILE WHERE Username = :user";
    $query_params = array(
        ':user' => $_POST['username']
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
    
    $row = $stmt->fetch();
    if ($row) {
        $response["success"] = 1;
        $response["message"] = $row;
        die(json_encode($response));
    }

} else {
?>
	<h1>Username</h1> 
	<form action="profile_retrieve.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Retrieve Profile" /> 
	</form>
	<?php
}
?>