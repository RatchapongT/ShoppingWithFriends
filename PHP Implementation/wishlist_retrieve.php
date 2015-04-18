<?php

require("config.inc.php");


if (!empty($_POST)) {
    
    
    $query        = "SELECT * FROM WISH_LIST WHERE Username = :user";
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
    
	$have_data = false;
	$a=array();
	$i=-1;
	while ($row = $stmt->fetch()) {
		$have_data = true;
		$i++;
		$a[$i]['Item']=$row['Item'];
		$a[$i]['Price']=$row['Price'];
	}

    if ($have_data) {
	$response["success"] = 1;
        $response["message"] = $a;
        die(json_encode($response));
    } else {
	$response["success"] = 2;
        $response["message"] = "No wishlist!";
        die(json_encode($response));
	}


} else {
?>
	<h1>Username</h1> 
	<form action="wishlist_retrieve.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Retrieve WishList" /> 
	</form>
	<?php
}
?>