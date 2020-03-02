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

<?php
    if (isset($_GET['Student'])){
        echo "Parent Email : <input type='text' name='parentemail' /><br/>";
    }
?>

        Password : <input type="password" name="password" />
        <br />
        <input type="submit" value="Insert" />

    </form>

</body>
</html>


<?php

if (isset($_POST['fullname']))
{
    $con = mysqli_connect('localhost', 'root', '');
    mysqli_select_db($con, 'DB2');

    $isStudent = isset($_GET['Student']);

    $fullName = $_POST['fullname'];
    $phoneNumber = $_POST['phone'];
    $email = $_POST['email'];
    $parentEmail = (array_key_exists('parentemail', $_POST))
 	? $_POST['parentemail']
	: "";
    $password = $_POST['password'];
    //$id = $_POST['id'];

    $sql = "INSERT INTO users (email, password, name, phone) VALUES ('$email', '$password', '$fullName', '$phoneNumber')";

    if(!mysqli_query($con, $sql))
    {
        echo 'Not Inserted';
        echo mysqli_error($con);
    }
    else
    {
        echo 'Inserted';
    }
}


?>