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

  $sql = "SELECT * FROM registration_info_2";

  $result = $con -> query($sql);

  if($result -> num_rows > 0) {

    while ($row = $result -> fetch_assoc()) {
    	array_push($response, array("ID" => $row["id"], "Name" => $row["name"], "Password" => $row["password"]));
    }

  }
} else {

  $response['error'] = true;
  $response['message'] = "invalid request";

  }

  echo json_encode(array("DataArray" => $response));

  // base-url: http://192.168.43.30/volley/volley-request-data.php

?>
