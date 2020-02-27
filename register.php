<?php

$con = mysqli_connect('localhost', 'root', '');
mysqli_select_db($con, 'DB2');



$FullName = $_POST['fullname'];
$PhoneNumber = $_POST['phone'];
$Email = $_POST['email'];
$Password = $_POST['password'];
$ID = $_POST['id'];

$sql = "INSERT INTO users (id, email, password, name, phone) VALUES ('$ID', '$Email', '$Password', '$FullName', '$PhoneNumber')";

if(!mysqli_query($con, $sql))
{
    echo 'Not Inserted';
    echo mysqli_error($con);
}
else
{
    echo 'Inserted';
}

header("refresh:10; url=register.html");


?>