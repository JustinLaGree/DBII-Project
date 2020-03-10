<?php 
//setup session constants
define("USER", "user");

if (isset($_SESSION[USER])){
    require("login-header.php");
}
else {
    require("logout-header.php");
}
?>
<br/>