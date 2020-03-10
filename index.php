<?php 
session_start();

//setup user constants
define("NAME", "name");

require("header.php");

if (isset($_SESSION[USER])){
    echo "Welcome, " . $_SESSION[USER][NAME];
}
else {
    echo "Please login";
}
?>

