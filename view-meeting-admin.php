<?php
session_start();

//build header
require("header.php");

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//setup session constants
define('USERTYPE', 'UserType');

if ($_SESSION[USER][USERTYPE] === 1){

    $meetId = $_GET["meeting"];

    exit();
}
header("Location: index.php");
<?>