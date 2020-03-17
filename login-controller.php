<?php

session_start();

//setup regular constants
define("ID", "id");
define("EMAIL", "email");
define("PASSWORD", "password");

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//setup session constants
define("USER", "user");
define('USERTYPE', 'UserType');

//setup user enum constants
define("ADMIN", 1);
define("STUDENT", 2);
define("PARENT", 3);

//check to see if the form was submitted by the login page
if ($_SERVER['REQUEST_METHOD'] === 'POST'){

    //construct a new my sql instance
    $mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);
    
    //as long as there is a value in the email input
    if(isset($_POST[EMAIL])){
        $email = $_POST[EMAIL];
        
        //prepare a new query where we get the use with specified email
        $sql = $mysqli->prepare('SELECT * FROM users WHERE email = ?'); 
        $sql->bind_param('s', $email);

        //get the result of the select query
        $sql->execute();
        $result = $sql->get_result();

        //should only get one column result
        if ($result->num_rows == 1) {
            //fetch the one result
            $row = $result->fetch_assoc();

            //get the password from the post data
            $password = $_POST[PASSWORD];

            //verify that the email and password combo matches
            if (strcmp(strtolower($email), strtolower($row[EMAIL])) == 0
               && strcmp(strtolower($password), strtolower($row[PASSWORD])) == 0) 
            {
                $userType = 0;

                //get if the user is admin
                $sql = $mysqli->prepare('SELECT admin_id FROM admins WHERE admin_id IN (SELECT id FROM users WHERE email = ?)'); 
                $sql->bind_param('s', $row[EMAIL]);

                $sql->execute();

                if ($sql->get_result()->num_rows == 1){
                    $userType = ADMIN;
                }
                else{
                    $sql = $mysqli->prepare('SELECT student_id FROM students WHERE student_id IN (SELECT id FROM users WHERE email = ?)'); 
                    $sql->bind_param('s', $row[EMAIL]);

                    $sql->execute();

                    if ($sql->get_result()->num_rows == 1){
                        $userType = STUDENT;
                    }
                    else{
                        $userType = PARENT;
                    }
                }

                //set the session cookie for the user logged in
                unset($row[PASSWORD]);
                $_SESSION = array();
                $_SESSION[USER] = $row;
                $_SESSION[USER][USERTYPE] = $userType;

                //redirect to the landing page
                header("Location: index.php");
                exit();
            } 
        }
    }
}
//if did not redirect to landing page, there must be an error in loggin in
header("Location: login.php?LoginFailed");
?>