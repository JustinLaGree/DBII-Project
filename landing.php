<?php 
    session_start();
?>

Welcome, 
<?php
    echo $_SESSION["user"]["name"];
?>!

<br/>
<a href="logout.php">logout</a>
<a href="edit-account-controller.php">account settings</a>