<form method="post" action="login-controller.php">

    <label for="email">Email:</label>
    <input type="text" placeholder="Enter Email" name="email"/><br/>
    
    <label for="password">Password:</label>
    <input type="password" placeholder="Enter Password" name="password"/><br/>

    <input type="hidden" name="form_submitted" value="1" />

    <input type="submit"/><br/><br/>
    
    <label
<?php
    if (!array_key_exists('LoginFailed', $_GET)){
        echo "style = 'display: none'";
    }
?>
    >Invalid login attempt! <br/>Server did not recognize email/password combination.</label>
</form>


