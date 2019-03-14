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
$user_pass = $_POST['user_pass'];

$sql = "select * from user_registration where user_name like  '".$user_name."' and user_pass like '".$user_pass."';";

$result = mysqli_query($con, $sql);

$response = array();

if(mysqli_num_rows($result)>0) {
  echo "Login Success";
} else {
  echo "Login fail";
}

  // 192.168.0.105/Notes-Api/user_login.php (base url)
?>
