<?php

require("config.inc.php");


if (!empty($_POST)) {
    
    //$query        = " SELECT * FROM PROFILE WHERE Username = :user";
    $query        = "INSERT INTO WISH_LIST ( Username, Item, Price) VALUES ( :user, :item, :price)";
    $query_params = array(
        ':user' => $_POST['Username'],
        ':item' => $_POST['Item'],
        ':price' => $_POST['Price']
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
    $response["message"] = "Success!";
    die(json_encode($response));
    

} else {
?>
	<h1>Username</h1> 
	<form action="wishlist_update.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="Username" value="" /> 
	    <br /><br /> 
	    Item:<br /> 
	    <input type="text" name="Item" value="" /> 
	    <br /><br /> 
	    Price :<br /> 
	    <input type="text" name="Price" value="" /> 
	    <br /><br /> 

	    <input type="submit" value="Update Wishlist" /> 
	</form>
	<?php
}
?>