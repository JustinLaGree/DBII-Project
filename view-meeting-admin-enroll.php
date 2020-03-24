<?php
session_start();

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//setup session constants
define('USER', 'user');
define('USERTYPE', 'UserType');

if (!($_SESSION[USER][USERTYPE] === 1)){
    header("Location: index.php");
}

//setup db connection
$mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

//get params from get request
$meetId = $_GET["meeting"];
$userId = $_GET["user"];
$mode = $_GET["mode"];

//use mode param to insert relation into enroll/enroll2 table
if ($mode === "mentee"){
    //setup insert statement
    $sql = $mysqli->prepare('INSERT INTO mentees (mentee_id) VALUES (?)');
    $sql->bind_param('s', $userId);

    //get the result of the select query
    $sql->execute();

    //setup insert statement
    $sql = $mysqli->prepare('INSERT INTO enroll (meet_id, mentee_id) VALUES (?, ?)');
    $sql->bind_param('ss', $meetId, $userId);

    //get the result of the select query
    $sql->execute();
}
else if ($mode === "mentor"){
    //setup insert statement
    $sql = $mysqli->prepare('INSERT INTO mentors (mentor_id) VALUES (?)');
    $sql->bind_param('s', $userId);

    //get the result of the select query
    $sql->execute();

    //setup insert statement
    $sql = $mysqli->prepare('INSERT INTO enroll2 (meet_id, mentor_id) VALUES (?, ?)');
    $sql->bind_param('ss', $meetId, $userId);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();
}

//redirect back to the admin view page
header("Location: view-meeting-admin.php?meeting=$meetId");
?>