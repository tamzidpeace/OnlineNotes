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
} else {
  echo "success";
}

$response = array();

if($_SERVER['REQUEST_METHOD'] == 'POST') {
  // DO SOMETHING
  $use_name = $_REQUEST['user_name'];

  $sql = "select * from user_registration where user_name = ? ";

  $stmt = $con->prepare($sql);

  $stmt -> bind_param("s", $user_name);
  $stmt->execute();
  $stmt->store_result();

    if($stmt->num_rows>0) {
      $response['true'] = true;
    } else {
      $response['fail'] = false;
    }
		$response['error'] = false;
		$response['message'] = 'Note saved successfully';
}else{
		//if not making failure response
		$response['error'] = true;
		$response['message'] = 'Please try later';
}

echo json_encode($response);

?>
