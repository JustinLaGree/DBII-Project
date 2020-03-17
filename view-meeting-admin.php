<?php
session_start();

//build header
require("header.php");

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//setup session constants
define('USERTYPE', 'UserType');

$meetId = $_GET["meeting"];
?>

<h2>Manage Meeting # <?php echo $meetId . ":"; ?></h2>

<?php
if (!($_SESSION[USER][USERTYPE] === 1)){
    header("Location: index.php");
}
else{
    //setup sql connection
    $mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

    //prepare a new query where we get the use with specified email
    $sql = $mysqli->prepare('SELECT * FROM meetings m, time_slot t WHERE meet_id = ? AND m.time_slot_id = t.time_slot_id');
    $sql->bind_param('s', $meetId);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();

    //redirect back to landing if invalid meeting
    if ($result->num_rows != 1){
        //header("Location: index.php");
        exit();
    }

    $row = $result->fetch_assoc();
?>

<table name="available_meetings" border=2>
    <thead>
        <tr>
            <th><?php echo $row["meet_name"] ?></th>
            <th><?php echo $row["date"] ?></th>
            <th><?php echo $row["day_of_the_week"] ?></th>
            <th><?php echo $row["start_time"] ?></th>
            <th><?php echo $row["end_time"] ?></th>
        </tr>
    <thead>
</table>

<?php
    //prepare a new query where we get the use with specified email
    $sql = $mysqli->prepare('SELECT description FROM groups WHERE group_id IN (SELECT group_id FROM meetings WHERE meet_id = ?)');
    $sql->bind_param('s', $meetId);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();

    $gradeLevel = intval($result->fetch_assoc()["description"]);
?>

<div>
    <h3>Mentees (
<?php
//prepare a new query where we get the use with specified email
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT mentee_id FROM enroll WHERE meet_id = ?)'); 
$sql->bind_param('s', $meetId);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();

echo $result->num_rows . " / 6 "
?>
    )</h3>
    <table border=2>
        <thead>
            <tr>
                <th>Registered Users:</th>
            </tr>
        <thead>
        <tbody>
        <?php 
        while($row = $result->fetch_assoc()){
        ?>
            <tr>
                <td><?php echo $row["name"]; ?></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
<?php
//prepare a new query where we get the use with specified email
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT student_id FROM students WHERE student_id NOT IN (SELECT mentee_id FROM enroll WHERE meet_id = ?) AND grade = ?)'); 
$sql->bind_param('si', $meetId, $gradeLevel);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();
?>
    <table border=2>
        <thead>
            <tr>
                <th>Unregistered Users:</th>
            </tr>
        <thead>
        <tbody>
        <?php 
        while($row = $result->fetch_assoc()){
        ?>
            <tr>
                <td><?php echo $row["name"]; ?></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
</div>

<div>
    <h3>Mentors (
<?php
//prepare a new query where we get the use with specified email
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT mentor_id FROM enroll2 WHERE meet_id = ?)'); 
$sql->bind_param('s', $meetId);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();

echo $result->num_rows . " / 3 "
?>
    )</h3>
    <table border=2>
        <thead>
            <tr>
                <th>Registered Users:</th>
            </tr>
        <thead>
        <tbody>
        <?php 
        while($row = $result->fetch_assoc()){
        ?>
            <tr>
                <td><?php echo $row["name"]; ?></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
<?php
$mentorGradeLevel = $gradeLevel + 3;

//prepare a new query where we get the use with specified email
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT student_id FROM students WHERE student_id NOT IN (SELECT mentor_id FROM enroll2 WHERE meet_id = ?) AND grade >= ?)'); 
$sql->bind_param('si', $meetId, $mentorGradeLevel);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();
?>
    <table border=2>
        <thead>
            <tr>
                <th>Unregistered Users:</th>
            </tr>
        <thead>
        <tbody>
        <?php 
        while($row = $result->fetch_assoc()){
        ?>
            <tr>
                <td><?php echo $row["name"]; ?></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
</div>

<?php } ?>