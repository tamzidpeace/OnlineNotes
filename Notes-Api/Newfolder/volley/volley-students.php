<?php

 $serverName = "localhost";
 $userName = "root";
 $password = "";
 $dbName = "user_db";

 $con = mysqli_connect($serverName, $userName, $password, $dbName);

 // if (!$con) {
 //   die("Connection failed: " . mysqli_connect_error());
 // } else {
 //   echo "connection seccessful";
 // }

 $name = $_POST['name'];
 $dept = $_POST['dept'];
 $reg = $_POST['reg'];

 $sql = "INSERT INTO students (name, dept)
 VALUES ('".$name."', '".$dept."', '".$reg."')";


 if (mysqli_query($con, $sql)) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($con);
}

// "http://192.168.43.30/volley/volley-students.php"

 ?>
