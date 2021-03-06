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

  $user_name = $_GET['user_name'];
  $sql = "SELECT * FROM notes WHERE user_name = ? ";

  $stmt = $con->prepare($sql);
  $stmt -> bind_param("s", $user_name);
  //$user_name = $_POST['user_name'];
  $stmt->execute();
  $result = $stmt->get_result();

  //$result = $con -> query($sql);

  if($result -> num_rows > 0) {

    while ($row = $result -> fetch_assoc()) {
    	array_push($response, array("ID" => $row["id"], "Title" => $row["title"], "Note" => $row["note"], "USER_NAME" => $row["user_name"]));
    }

  }
} else {

  $response['error'] = true;
  $response['message'] = "invalid request";

  }

  echo json_encode(array("DataArray" => $response));

  // base-url: http://192.168.43.30/Notes-Api/read-data.php

?>
