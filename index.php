<?php 
session_start();

//setup user constants
define("NAME", "name");

//setup session constants
define('USERTYPE', 'UserType');

require("header.php");

if (isset($_SESSION[USER]) && $_SESSION[USER][USERTYPE] == 1){
    echo "Welcome, " . $_SESSION[USER][NAME] . "<br/><br/>";

    //output a txt file for any meetings that need to be cancelled
    require("meeting-cancel-notification.php");

    require("view-meetings-admin.php");
}
else if (isset($_SESSION[USER])){
    echo "Welcome, " . $_SESSION[USER][NAME] . "<br/><br/>";
    echo "<a href='enroll_as_mentee.php'>Enroll As a Mentee</a><br/><br/>";
    echo "<a href='enroll_as_mentor.php'>Enroll As a Mentor</a><br/><br/>";
    echo "<a href='view_meetings.php'>View Currently Enrolled Meetings</a>";
}
else {
    echo "Please login<br/>";
}
?>

