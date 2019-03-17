
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
  $name = $_REQUEST['name'];
  $pass = $_REQUEST['pass'];

  $sql = "INSERT INTO registration_info_2 (name, password) VALUES (?, ?)";

  $stmt = $con->prepare($sql);

  $stmt -> bind_param("ss", $name, $pass);

  //if data inserts successfully
	if($stmt->execute()){
		//making success response
		$response['error'] = false;
		$response['message'] = 'Name saved successfully';
	}else{
		//if not making failure response
		$response['error'] = true;
		$response['message'] = 'Please try later';
	}

} else {

  $response['error'] = true;
  $response['message'] = "invalid request";
}

echo json_encode($response);

// "http://192.168.43.30/volley/volley-insert-data.php"

 ?>
