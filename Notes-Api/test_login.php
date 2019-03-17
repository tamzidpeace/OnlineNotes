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

  // DO SOMETHING
  $sql = "select * from user_registration where user_name = ? and user_pass = ? ";

  $stmt = $con->prepare($sql);
  $stmt -> bind_param("ss", $user_name, $user_pass);
  $user_name = $_POST['user_name'];
  $user_pass = $_POST['user_pass'];
  $stmt->execute();
  $result = $stmt->get_result();

  // optional
  // $result2 = $stmt->show_result();
  // echo $result2;

  if($result->num_rows>0) {
    $row = $result-> fetch_assoc();
    echo $row["user_name"];
      //$response['true'] = true;
  } else {
      $response['fail'] = false;
      echo "invalid username or password";
  }

}else{
		//if not making failure response
		$response['error'] = true;
		$response['message'] = 'Please try later';
    echo "invalid request";
}

//echo json_encode($response);

?>
