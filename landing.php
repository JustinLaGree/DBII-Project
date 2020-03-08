<?php 
    session_start();
?>

Welcome, 
<?php
    echo $_SESSION["user"]["name"];
?>!

<br/>
<a href="edit-account-controller.html">account settings</a>