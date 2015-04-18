<?php

require("config.inc.php");


if (!empty($_POST)) {
    
    //$query        = " SELECT * FROM PROFILE WHERE Username = :user";
    $query        = "UPDATE PROFILE SET Name = :name, Biography = :biography, Email = :email, Location = :location, Phonenum = :phonenum WHERE Username = :user";
    $query_params = array(
        ':user' => $_POST['Username'],
        ':name' => $_POST['Name'],
        ':biography' => $_POST['Biography'],
        ':email' => $_POST['Email'],
        ':location' => $_POST['Location'],
        ':phonenum' => $_POST['Phonenum']

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
	<form action="profile_update.php" method="post"> 
	    Username:<br /> 
	    <input type="text" name="Username" value="" /> 
	    <br /><br /> 
	    Name:<br /> 
	    <input type="text" name="Name" value="" /> 
	    <br /><br /> 
	    Biography:<br /> 
	    <input type="text" name="Biography" value="" /> 
	    <br /><br /> 
	    Email:<br /> 
	    <input type="text" name="Email" value="" /> 
	    <br /><br /> 
	    Location:<br /> 
	    <input type="text" name="Location" value="" /> 
	    <br /><br /> 
	    Phonenum:<br /> 
	    <input type="text" name="Phonenum" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Update Profile" /> 
	</form>
	<?php
}
?>