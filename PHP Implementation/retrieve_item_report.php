<?php

require("config.inc.php");


if (!empty($_POST)) {

    //$query        = "SELECT * FROM PROFILE WHERE Username = :user";
    $query = "SELECT * FROM REPORTED_ITEM WHERE FriendID = :user";
    $query_params = array(
        ':user' => $_POST['Username'],
    );

    try {
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error. Please Try Again!";
        die(json_encode($response));
    }


    $have_data = false;

    $a = array();

    while ($row = $stmt->fetch()) {
        $have_data = true;
        array_push($a, $row);
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
    <h1>Username</h1>
    <form action="retrieve_item_report.php" method="post">
        Username:<br/>
        <input type="text" name="Username" value=""/>
        <br/><br/>
    </form>
<?php
}
?>