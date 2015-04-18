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
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
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
    $a = array();

     // Get lat and long by address         
        


    while ($row = $stmt->fetch()) {
        $have_data = true;
        array_push($a, $row['FriendID']);
    }
    while (!empty($a)) {
        $have_data = true;
        
        $address = $_POST['Location']; // Google HQ
$prepAddr = str_replace(' ','+',$address);
$geocode=file_get_contents('http://maps.google.com/maps/api/geocode/json?address='.$prepAddr.'&sensor=false');
$output= json_decode($geocode);
$latitude = $output->results[0]->geometry->location->lat;
		$longitude = $output->results[0]->geometry->location->lng;
$query = "INSERT INTO REPORTED_ITEM(Name, Price, Location, Quantity, Username, FriendID, Latitude, Longitude) VALUES(:name,:price, :location,:quantity , :user , :friend, $latitude, $longitude)";
        $query_params = array(
            ':user' => $_POST['UserID'],
            ':name' => $_POST['Name'],
            ':location' => $_POST['Location'],
            ':quantity' => $_POST['Quantity'],
            ':price' => $_POST['Price'],
            ':friend' => array_pop($a)
        );




        try {
            $stmt = $db->prepare($query);
            $result = $stmt->execute($query_params);
        } catch (PDOException $ex) {
            $response["success"] = 0;
            $response["message"] = $ex;
            die(json_encode($response));
        }

        $response["success"] = 1;
        $response["message"] = "Success!";
       

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
    <form action="update_item_report.php" method="post">
        Username:<br/>
        <input type="text" name="UserID" value=""/>
        <br/><br/>

        ItemName:<br/>
        <input type="text" name="Name" value=""/>
        <br/><br/>

        Location:<br/>
        <input type="text" name="Location" value=""/>
        <br/><br/>

        Quantity:<br/>
        <input type="text" name="Quantity" value=""/>
        <br/><br/>

        Price:<br/>
        <input type="text" name="Price" value=""/>
        <br/><br/>


        <input type="submit" value="Add Report"/>
    </form>
<?php
}

?>