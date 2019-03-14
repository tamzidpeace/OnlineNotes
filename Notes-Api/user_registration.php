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

if($_SERVER['REQUEST_METHOD'] == 'POST') {

  $user_name = $_REQUEST['user_name'];
  $user_pass = $_REQUEST['user_pass'];
  $user_email = $_REQUEST['user_email'];

  $sql = "INSERT INTO user_registration (user_name, user_pass, user_email) VALUES (?, ?, ?)";

  $stmt = $con->prepare($sql);
  $stmt -> bind_param("sss", $user_name, $user_pass, $user_email);


  if($stmt -> execute()) {

    $response['error'] = false;
    $response['message'] = 'Registration successful';
  } else {
    $response['error'] = true;
    $response['message'] = 'Username or email already exist! Try with different one';
  }
} else {
  $response['error'] = true;
  $response['message'] = 'invalid request';
}

echo json_encode($response);

// 192.168.0.105/Notes-Api/user_registration.php (base url)
 ?>
