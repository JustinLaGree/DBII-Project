<?php 
session_start();

//setup user constants
define("NAME", "name");

require("header.php");

if (isset($_SESSION[USER])){
    echo "Welcome, " . $_SESSION[USER][NAME] . "<br/><br/>";
    echo "<a href='enroll_as_mentee.php'>Enroll As a Mentee</a><br/><br/>";
    echo "<a href='enroll_as_mentor.php'>Enroll As a Mentor</a><br/><br/>";
    echo "<a href='view_meetings.php'>View Currently Enrolled Meetings</a>";
}
else {
    echo "Please login<br/>";
}
?>

