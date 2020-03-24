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
?>