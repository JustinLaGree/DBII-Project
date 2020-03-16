<?php 
require("header.php");
?>

<form method="post" action="login-controller.php">

    <label for="email">Email:</label>
    <input type="text" placeholder="Enter Email" name="email"/><br/>
    
    <label for="password">Password:</label>
    <input type="password" placeholder="Enter Password" name="password"/><br/>

    <input type="submit" value="Login"/><br/><br/>
    
    <label <?php
    //display error message if LoginFailed is specified in the GET header
    if (!array_key_exists('LoginFailed', $_GET)){
        echo "style = 'display: none'";
    }
    ?> >Invalid login attempt! <br/>Server did not recognize email/password combination.</label>

</form>


