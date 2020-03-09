<?php 
    session_start();
?>

Welcome, 
<?php
    echo $_SESSION["user"]["name"];
?>!

<br/>
<a href="logout.php">Logout</a>
<br/>
<br/>
<a href="edit-account-controller.php">Account Settings</a>
<br/>
<br/>
<a href="enroll_as_mentee.php">Enroll As a Mentee</a>
<br/>
<br/>
<a href="enroll_as_mentor.php">Enroll As a Mentor</a>
<br/>
<br/>
<a href="view_meetings.php">View Currently Enrolled Meetings</a>