<?php
session_start();

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//setup session constants
define('USERTYPE', 'UserType');
define('USER', 'user');

if (!($_SESSION[USER][USERTYPE] === 1)){
    header("Location: index.php");
}

if (isset($_POST)){
    $meetId = $_POST["meeting"];

    $title = $_POST["title"];
    $author = $_POST["author"];
    $type = $_POST["type"];
    $url = $_POST["url"];
    $assignedDate = $_POST["assigned_date"];
    $notes = $_POST["notes"];

    //setup db connection
    $mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

    //setup insert statement
    $sql = $mysqli->prepare('INSERT INTO material (title, author, type, url, assigned_date, notes) VALUES (?, ?, ?, ?, ?, ?)');
    $sql->bind_param('ssssss', $title, $author, $type, $url, $assignedDate, $notes);

    //get the result of the select query
    $sql->execute();

    $materialId = $mysqli->insert_id;

    //setup insert statement
    $sql = $mysqli->prepare('INSERT INTO assign (meet_id, material_id) VALUES (?, ?)');
    $sql->bind_param('ii', $meetId, $materialId);

    //get the result of the select query
    $sql->execute();
}
//redirect back to the admin view page
header("Location: view-meeting-admin.php?meeting=$meetId");
?>