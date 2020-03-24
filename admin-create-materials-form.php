<?php
session_start();

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//setup session constants
define('USERTYPE', 'UserType');

//navbar
require("header.php");

if (!($_SESSION[USER][USERTYPE] === 1)){
    header("Location: index.php");
}

//setup db connection
$mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

//get params from get request
$meetId = $_GET["meeting"];

?>
<form action="admin-create-materials.php" method="post">
    Title : <input type="text" name="title" required/>
    <br />
    Author : <input type="text" name="author"/>
    <br />
    Type : <input type="text" name="type" required/>
    <br />
    URL : <input type="text" name="url"/>
    <br />
    Assigned Date : <input type="date" name="assigned_date"/>
    <br />
    Notes : <input type="text" name="notes"/>
    <br />
    <input type="hidden" name="meeting" <?php echo "value='$meetId'";?>/>
    <input type="submit" value="Create" />
</form>