
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
  $title = $_REQUEST['title'];
  $note = $_REQUEST['note'];

  $sql = "INSERT INTO notes (title, note) VALUES (?, ?)";

  $stmt = $con->prepare($sql);

  $stmt -> bind_param("ss", $title, $note);

  //if data inserts successfully
	if($stmt->execute()){
		//making success response
		$response['error'] = false;
		$response['message'] = 'Note saved successfully';
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

// "http://192.168.43.30/Notes-Api/insert-data.php"

 ?>
