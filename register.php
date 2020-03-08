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
    if (isset($_GET['Student']) || isset($_POST['parentemail'])){
        echo "Parent Email : <input type='text' name='parentemail' /><br/>";
        echo "Grade Level : <input type='text' name='grade' /><br/>";
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
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $isStudent = isset($_POST['grade']);

    $fullName = $_POST['fullname'];
    $phoneNumber = $_POST['phone'];
    $email = $_POST['email'];
    $parentEmail = (array_key_exists('parentemail', $_POST))
 	? $_POST['parentemail']
	: "";
    $grade = (array_key_exists('grade', $_POST))
 	? $_POST['grade']
	: "";
    $password = $_POST['password'];

    //prepare a new query where we get the use with specified email
    $sql = "INSERT INTO users (email, password, name, phone) VALUES ('$email', '$password', '$fullName', '$phoneNumber')"; 

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - User';
        echo mysqli_error($mysqli);
        return;
    }
    else
    {
        echo 'Inserted';
    }

    $insertId = $mysqli->insert_id;
    echo "insert: $insertId";
    
    if ($isStudent)
    {
        $sql_insert_student = "INSERT INTO students (student_id, grade, parent_id)
                                SELECT $insertId, $grade, id
                                FROM users
                                WHERE email = '$parentEmail'";



        if(!mysqli_query($mysqli, $sql_insert_student))
        {
            echo 'Not Inserted: Student';
            echo mysqli_error($mysqli);
        }
        else
        {
            echo 'Inserted';
        }
    }
    else
    {
        $sql_insert_parent = "INSERT INTO parents (parent_id) VALUES ('$insertId')";
        if(!mysqli_query($mysqli, $sql_insert_parent))
        {
            echo 'Not Inserted: Parent';
            echo mysqli_error($mysqli);
        }
        else
        {
            echo 'Inserted';
        }
        }
}


?>