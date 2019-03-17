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

$user_name = $_POST['user_name'];

$sql = "select * from notes where user_name like  '".$user_name."';";

$result = mysqli_query($con, $sql);

$response = array();

if(mysqli_num_rows($result)>0) {
  while ($row = $result -> fetch_assoc()) {
    array_push($response, array("ID" => $row["id"], "Title" => $row["title"], "Note" => $row["note"], "USER_NAME" => $row["user_name"]));
  }
} else {
  $response['error'] = true;
  $response['message'] = "invalid request";
}

  echo json_encode(array("DataArray" => $response));

  // base-url: http://192.168.43.30/Notes-Api/read-data.php

?>
