<?php

$serverName = "localhost";
$userName = "root";
$password = "";
$dbName = "user_db";

// inatializing connection

$con = new mysqli($serverName, $userName, $password, $dbName);

// checking connection

if($con -> connect_error) {
  die("Connection error: " . $con -> connect_error);
}

$response = array();

// checking request method

if($_SERVER['REQUEST_METHOD'] == 'GET') {

  $sql = "SELECT * FROM notes";

  $result = $con -> query($sql);

  if($result -> num_rows > 0) {

    while ($row = $result -> fetch_assoc()) {
    	array_push($response, array("ID" => $row["id"], "Title" => $row["title"], "Note" => $row["note"]));
    }

  }
} else {

  $response['error'] = true;
  $response['message'] = "invalid request";

  }

  echo json_encode(array("DataArray" => $response));

  // base-url: http://192.168.43.30/Notes-Api/read-data.php

?>
