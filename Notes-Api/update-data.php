<?php

$serverName = "localhost";
$userName = "root";
$password = "";
$dbName = "user_db";

// inatializing connection

$con = new mysqli($serverName, $userName, $password, $dbName);

// checking connection

if ($con -> connect_error) {
    die("Connection error: " . $con -> connect_error);
}



if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_REQUEST['id'];
    $title = $_REQUEST['title'];
    $note = $_REQUEST['note'];

    $sql = "UPDATE notes SET title=? , note=? WHERE id=?";

    $stmt = $con->prepare($sql);
    
    $stmt -> bind_param("ssi", $title, $note, $id);

    if ($stmt->execute()) {
        //making success response
        $response['error'] = false;
        $response['message'] = 'Note updated successfully';
    } else {
        //if not making failure response
        $response['error'] = true;
        $response['message'] = 'Please try later';
    }
} else {
    $response['error'] = true;
    $response['message'] = "invalid request";
}
  
  echo json_encode($response);
