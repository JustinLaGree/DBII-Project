<?php

$mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

//prepare a new query where we get the use with specified email
$sql = $mysqli->prepare('SELECT * FROM meetings ORDER BY date ASC'); 

//get the result of the select query
$sql->execute();
$result = $sql->get_result();
?>

    <table name="available_meetings" border=2>
    <thead>
        <tr>
            <td>Date</td>
            <td>Meeting Name</td>
            <td>Number of Mentees</td>
            <td>Number of Mentors</td>
            <td>Select</td>
        </tr>
    <thead>
    <tbody>
        <?php
            
            while($row = $result->fetch_assoc()){
                $meetId = $row['meet_id'];
        ?>
        <tr>
            <td><?php echo $row['date'];?></td>
            <td><?php echo $row['meet_name']?></td>
<?php
//prepare a new query where we get the use with specified email
$countSql = $mysqli->prepare('SELECT COUNT(mentee_id) as numMentees FROM enroll WHERE meet_id = ?'); 
$countSql->bind_param('s', $meetId);

//get the result of the select query
$countSql->execute();
$countResult = $countSql->get_result();
?>

            <td><?php echo $countResult->fetch_assoc()["numMentees"] . " / 6"?></td>
 <?php
//prepare a new query where we get the use with specified email
$countSql = $mysqli->prepare('SELECT COUNT(mentor_id) as numMentors FROM enroll2 WHERE meet_id = ?'); 
$countSql->bind_param('s', $meetId);

//get the result of the select query
$countSql->execute();
$countResult = $countSql->get_result();
?>
            <td><?php echo $countResult->fetch_assoc()["numMentors"] . " / 3"?></td>
            <td><a <?php echo "href='view-meeting-admin.php?meeting=$meetId'" ?>>Select</a></td>
        </tr>
    <?php
    }
    ?>
    </tbody>
</table>