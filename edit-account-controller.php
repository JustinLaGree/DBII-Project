<?php

//start session for use in ids/names
session_start();

//setup row constants
define("ID", "id");
define("NAME", "name");

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

    //get the user id
    $id = $_SESSION[USER][ID];

    //setup the sql query to see if user is an admin
    $sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT admin_id from admins WHERE admin_id = ?)'); 
    $sql->bind_param('s', $id);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();

    //check to see if this is an admin
    if ($result->num_rows == 1) {
        //redirect to the admin account page
        header("Location: edit-account-admin.php?id=$id");
        exit();
    }

    //setup the sql query to see if user is an student
    $sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT student_id from students WHERE student_id = ?)'); 
    $sql->bind_param('s', $id);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();

    //check to see if this is an student
    if ($result->num_rows == 1) {
        //redirect to the student account page
        header("Location: edit-account-student.php?id=$id");
        exit();
    }

    //setup the sql query to see if user is an parent
    $sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT parent_id from parents WHERE parent_id = ?)'); 
    $sql->bind_param('s', $id);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();

    //check to see if this is an parent
    if ($result->num_rows == 1) {

        //fetch the one result
        $parentRow = $result->fetch_assoc();

        //setup sql to get all children of parent
        $studentSql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT student_id from students WHERE parent_id = ?)'); 
        $studentSql->bind_param('s', $id);
            
        //get the result of the select query
        $studentSql->execute();
        $studentResult = $studentSql->get_result();

        if ($studentResult->num_rows <= 0){
            //redirect to the parent account page
            header("Location: edit-account-parent.php?id=$id");
            exit();
        }
        else {
            //give the user a selection between parent and students
            $parentName = $parentRow[NAME];
            echo "<h2>Which user would you like to edit?</h2>";
            echo "<a href='edit-account-parent.php?id=$id'>$parentName</a><br/>";

            while($studentRow = $studentResult->fetch_assoc()){
                $studentId = $studentRow[ID];
                $studentName = $studentRow[NAME];
                echo "<a href='edit-account-student.php?id=$studentId'>$studentName</a><br/>";
            }
            exit();
        }
    }
}
header("Location: login.php");
?>