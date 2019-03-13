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

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id = $_REQUEST['id'];

    $sql = "DELETE FROM notes WHERE id=?";

    $stmt = $con->prepare($sql);
    
    $stmt -> bind_param("i", $id);

    if($stmt->execute()){
		//making success response
		$response['error'] = false;
		$response['message'] = 'Note deleted successfully';
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


// base-url: http://192.168.43.30/Notes-Api/delete-data.php

?>