<?php
//start session for use in ids/names
session_start();

//build header
require("header.php");

//setup row constants
define("ID", "id");
define("NAME", "name");
define("PHONE", "phone");
define("EMAIL", "email");
define("PASSWORD", "password");
define("GRADE", "grade");

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//setup session constants
define("USER", "user");

if (isset($_SESSION[USER])){

    //construct a new my sql instance
    $mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

    if ($_SERVER['REQUEST_METHOD'] === 'GET') {

        //get the student's ID
        $id = $_GET[ID];

        //setup the sql query to see if user is an student
        $sql = $mysqli->prepare('SELECT * FROM users, students WHERE id = student_id AND id = ?'); 
        $sql->bind_param('s', $id);

        //get the result of the select query
        $sql->execute();
        $result = $sql->get_result();

        //check to see if this is an student
        if ($result->num_rows == 1) {
            
            //get the one student row
            $row = $result->fetch_assoc();

            //make a new form to update info
            $name = $row[NAME];
            $phone = $row[PHONE];
            $email = $row[EMAIL];
            $password = $row[PASSWORD];
            $grade = $row[GRADE];

            echo "<form method='POST'>";

            echo "<label for='name'>Full Name:</label><br/>";
            echo "<input type='text' value='$name' name='name'/><br/><br/>";

            echo "<label for='phone'>Phone Number:</label><br/>";
            echo "<input type='text' value='$phone' name='phone'/><br/><br/>";

            echo "<label for='email'>Email Address:</label><br/>";
            echo "<input type='text' value='$email' name='email'/><br/><br/>";

            echo "<label for='grade'>Grade:</label><br/>";
            echo "<input type='text' value='$grade' name='grade'/><br/><br/>";

            echo "<label for='password'>Password:</label><br/>";
            echo "<input type='password' value='$password' name='password'/><br/><br/>";

            echo "<input type='submit' value='Update'/>";

            echo "</form>";
            exit();
        }
    }
    else if ($_SERVER['REQUEST_METHOD'] === 'POST'){
        //get the parent's ID
        $id = $_GET[ID];

        $name = $_POST[NAME];
        $phone = $_POST[PHONE];
        $email = $_POST[EMAIL];
        $grade = $_POST[GRADE];
        $password = $_POST[PASSWORD];

        //setup the sql query to see if user is an parent
        $sql = $mysqli->prepare("UPDATE students SET grade = ? WHERE student_id = ?"); 
        $sql->bind_param('ss', $grade, $id);

        //get the result of the select query
        $sql->execute();

        //if user grade was not updated
        if ($sql->affected_rows != 1) {
            header("Location: login.php");
        }

        //setup the sql query to see if user is an parent
        $sql = $mysqli->prepare("UPDATE users SET name = ?, phone = ?, email = ?, password = ? WHERE id = ?"); 
        $sql->bind_param('sssss', $name, $phone, $email, $password, $id);

        //get the result of the select query
        $sql->execute();

        //if update was successful
        if ($sql->affected_rows == 1) {

            //set the session cookie for the user logged in
            $_SESSION[USER][NAME] = $name;
            $_SESSION[USER][PHONE] = $phone;
            $_SESSION[USER][EMAIL] = $email;
            $_SESSION[USER][PASSWORD] = $password;

            //redirect to the landing page
            header("Location: landing.php");
            exit();
        }
    }
}
header("Location: login.php");
?>