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
//user must be an admin to access this page
if (!($_SESSION[USER][USERTYPE] === 1)){
    header("Location: index.php");
}
else{
    //setup sql connection
    $mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

    //get all info for this specific meeting
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

    //object that holds all the meeting info
    $meeting = $result->fetch_assoc();
?>

<table border=2>
    <thead>
        <tr>
            <th><?php echo $meeting["meet_name"] ?></th>
            <th><?php echo $meeting["date"] ?></th>
            <th><?php echo $meeting["day_of_the_week"] ?></th>
            <th><?php echo $meeting["start_time"] ?></th>
            <th><?php echo $meeting["end_time"] ?></th>
            <th><a <?php echo "href=admin-create-materials.php?meeting=$meetId" ?>>Post Study Materials</a></th>
        </tr>
    <thead>
</table>

<?php
    //get the grade level for the specified meeting
    $sql = $mysqli->prepare('SELECT description FROM groups WHERE group_id IN (SELECT group_id FROM meetings WHERE meet_id = ?)');
    $sql->bind_param('s', $meetId);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();

    //get the grade level from desription attribute
    $gradeLevel = intval($result->fetch_assoc()["description"]);
?>

<div>
    <h3>Mentees (
<?php
//get the count of all enrolled mentees
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT mentee_id FROM enroll WHERE meet_id = ?)'); 
$sql->bind_param('s', $meetId);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();

$numEnroll = $result->num_rows;
echo $numEnroll . " / 6 "
?>
    )</h3>
    <table border=2>
        <thead>
            <tr>
                <th>Registered Users:</th>
                <th></th>
            </tr>
        <thead>
        <tbody>
        <?php 
        //iterate over enrolled mentees and output to table
        while($row = $result->fetch_assoc()){
        ?>
            <tr>
                <td><?php echo $row["name"]; ?></td>
                <td><a disabled>Cannot Enroll</a></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
<?php
//get all mentees that are not already enrolled and are eligible to enroll
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT student_id FROM students WHERE student_id NOT IN (SELECT mentee_id FROM enroll WHERE meet_id = ?) AND grade = ?)'); 
$sql->bind_param('si', $meetId, $gradeLevel);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();

//separate the list of users into two groups: ones that have conflicting schedules and ones that are available to register
$conflicts = [];
$unregistered = [];

//iterate over the list of all unenrolled mentees
while($row = $result->fetch_assoc()){
    //get info for the target meeting we are looking to enroll in
    $currentDate = strtotime($meeting["date"]);
    $currentName = $meeting["meet_name"];

    $userId = intval($row["id"]);

    //get all meeting time slot info where this user is enrolled
    $enrollSql = $mysqli->prepare('SELECT * FROM time_slot t, meetings m WHERE t.time_slot_id = m.time_slot_id AND m.meet_id IN (SELECT meet_id FROM enroll WHERE mentee_id = ?)');
    $enrollSql->bind_param('i', $userId);

    $enrollSql->execute();
    $enrollResult = $enrollSql->get_result();

    if ($numEnroll >= 6){
        array_push($conflicts, $row);
        continue;
    }

    $inserted = false;
    //iterate over all the meeting time info
    while ($meetingtimeRow = $enrollResult->fetch_assoc()){
        $targetDate = strtotime($meetingtimeRow["date"]);

        $dateDif = abs(round(($targetDate - $currentDate) / (60 * 60 * 24)));
        
        //check to see if there is a conflict
        //mentors cannot register for two classes if they are on the same data/time or if they are on the same weekend and same subject/name
        if (($dateDif < 2 && $meetingtimeRow["meet_name"] == $currentName) || $currentDate == $targetDate && $meeting["start_time"] == $meetingtimeRow["start_time"]){
            array_push($conflicts, $row);
            $inserted = true;
            break;
        }
    }
    //if no conflicts found
    if (!$inserted){
        array_push($unregistered, $row);
    }
    $inserted = true;
}
?>
        <table border=2>
        <thead>
            <tr>
                <th>Conflicted Users:</th>
                <th></th>
            </tr>
        <thead>
        <tbody>
        <?php 
        //iterate over the conflicting students and output them to the table
        foreach ($conflicts as $conflict){
        ?>
            <tr>
                <td><?php echo $conflict["name"]; ?></td>
                <td><a disabled>Cannot Enroll</a></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
    <table border=2>
        <thead>
            <tr>
                <th>Unregistered Users:</th>
                <th></th>
            </tr>
        <thead>
        <tbody>
        <?php 
        //iterate over the unregistered students and output them to the table
        foreach ($unregistered as $unreg){
            $userId = $unreg["id"];
        ?>
            <tr>
                <td><?php echo $unreg["name"]; ?></td>
                <td><a <?php echo "href='view-meeting-admin-enroll.php?meeting=$meetId&user=$userId&mode=mentee'"?>>Enroll</a></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
</div>

<div>
    <h3>Mentors (
<?php
//get all enrolled mentors
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT mentor_id FROM enroll2 WHERE meet_id = ?)'); 
$sql->bind_param('s', $meetId);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();

$numEnroll = $result->num_rows;
echo $numEnroll . " / 3 "
?>
    )</h3>
    <table border=2>
        <thead>
            <tr>
                <th>Registered Users:</th>
                <th></th>
            </tr>
        <thead>
        <tbody>
        <?php 
        //iterate over the registered students and output them to the table
        while($row = $result->fetch_assoc()){
        ?>
            <tr>
                <td><?php echo $row["name"]; ?></td>
                <td><a disabled>Cannot Enroll</a></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
<?php
//calculate the grade level requirement needed to mentor this meeting
$mentorGradeLevel = $gradeLevel + 3;

//get all users who are eligible to enroll in this meeting as a mentor
$sql = $mysqli->prepare('SELECT * FROM users WHERE id IN (SELECT student_id FROM students WHERE student_id NOT IN (SELECT mentor_id FROM enroll2 WHERE meet_id = ?) AND grade >= ?)'); 
$sql->bind_param('si', $meetId, $mentorGradeLevel);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();

//separate the list of users into two groups: ones that have conflicting schedules and ones that are available to register
$conflicts = [];
$unregistered = [];

//iterate over the list of all unenrolled mentees
while($row = $result->fetch_assoc()){
    //get the date of the current meeting we are trying to register for
    $currentDate = strtotime($meeting["date"]);

    $userId = intval($row["id"]);

    //get the meeting time slot info for all of the meetings that the given user has enrolled in
    $enrollSql = $mysqli->prepare('SELECT * FROM time_slot t, meetings m WHERE t.time_slot_id = m.time_slot_id AND meet_id IN (SELECT meet_id FROM enroll2 WHERE mentor_id = ?)');
    $enrollSql->bind_param('i', $userId);

    $enrollSql->execute();
    $enrollResult = $enrollSql->get_result();

    $inserted = false;

    if ($numEnroll >= 3){
        array_push($conflicts, $row);
        continue;
    }

    //iterate over all of the time slot info for the given user
    while ($timeRow = $enrollResult->fetch_assoc()){
        $targetDate = strtotime($timeRow["date"]);

        $dateDif = abs(round(($targetDate - $currentDate) / (60 * 60 * 24)));
        //if the user has a conflict:
        //mentors cannot register for more than one class in a given weekend
        if ($dateDif < 2){
            array_push($conflicts, $row);
            $inserted = true;
            break;
        }
    }
    //if no conflicts found
    if (!$inserted){
        array_push($unregistered, $row);
    }
    $inserted = true;
}

?>
    <table border=2>
        <thead>
            <tr>
                <th>Conflicted Users:</th>
                <th></th>
            </tr>
        <thead>
        <tbody>
        <?php 
        //iterate over the conflicting students and output them to the table
        foreach ($conflicts as $conflict){
        ?>
            <tr>
                <td><?php echo $conflict["name"]; ?></td>
                <td><a disabled>Cannot Enroll</a></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
    <table border=2>
        <thead>
            <tr>
                <th>Unregistered Users:</th>
                <th></th>
            </tr>
        <thead>
        <tbody>
        <?php 
        //iterate over the unregistered students and output them to the table
        foreach ($unregistered as $unreg){
            $userId = $unreg["id"];
        ?>
            <tr>
                <td><?php echo $unreg["name"]; ?></td>
                <td><a <?php echo "href='view-meeting-admin-enroll.php?meeting=$meetId&user=$userId&mode=mentor'"?>>Enroll</a></td>
            </tr>
        <?php } ?>
        </tbody>
    </table>
</div>

<?php } ?>