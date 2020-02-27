<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Register</title>
</head>
<body>

    <form action="register.php" method="post">

        Full Name : <input type="text" name="fullname" />
        <br />
        Phone Number : <input type="text" name="phone" />
        <br />
        Email : <input type="text" name="email" />
        <br />
        Password : <input type="text" name="password" />
        <br />
        ID : <input type="text" name="id" />
        <br />
        <input type="submit" value="Insert" />

    </form>

</body>
</html>


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

//header("refresh:10; url=register.html");


?>