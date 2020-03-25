<?php 
    session_start();

    //build header
    require("header.php");
?>

<h1>Enroll As a Mentee</h1>
<br/>

<?php
    $id = $_SESSION['user']['id'];
    $date = date('Ymd');
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');
    //Show meetings only if the group matches the user, the meeting is not full, and it is not passed the available time to enroll.
    $sql = "SELECT * FROM meetings WHERE group_id IN
                (SELECT group_id FROM groups WHERE description =
                    (SELECT grade FROM students WHERE student_id = $id))
                AND meet_id NOT IN (SELECT meet_id FROM enroll
                    GROUP BY meet_id
                        HAVING count(*) > 5)
                AND DATEDIFF(meetings.date, $date) > 3
                AND (meetings.date, time_slot_id) NOT IN
                    (SELECT date, time_slot_id FROM meetings WHERE meet_id IN
                        (SELECT meet_id FROM enroll WHERE mentee_id = $id)
                            OR meet_id IN
                                (SELECT meet_id FROM enroll2 WHERE mentor_id = $id))";
                                                     
    
    
$result = $mysqli->query($sql);
echo mysqli_error($mysqli);
?>


<form method="post" action="enroll_as_mentee.php">
    <table name="available_meetings" border = 2>
    <thead id="td">
        <tr>
            <td>Grade</td>
            <td>Meeting Name</td>
            <td>Date</td>
            <td>Time Slot</td>
            <td>Current Mentee Enrollment</td>
            <td>Enroll</td>
            <td>Bulk Enroll</td>
        </tr>
    <thead>
    <tbody>
        <?php
            
            while($row = $result->fetch_assoc()){
                

?>
            <tr>
    <td>
        <?php echo $row[ 'group_id'] + 5?>

    </td>
    <td>
        <?php echo $row[ 'meet_name']?>

    </td>
    <td>
        <?php echo $row[ 'date'];?>
    </td>
    <td>
        <?php echo $row[ 'time_slot_id'];?>
    </td>
    <td>
        <?php
            //Display the amount of remaining spots in the meeting
            $mentee_cap_sql = "SELECT COUNT(mentee_id) as c1 FROM enroll WHERE meet_id = $row[meet_id]";
            $result2 = $mysqli->query($mentee_cap_sql);
            echo $mysqli->error;
            while($row2 = $result2->fetch_assoc()){
            echo $row2[ 'c1'];
            echo ' / 6';
            }
        ?>
    </td>
    
    <td>
        <form method="post" action="enroll_as_mentee.php">
            <input type="hidden" name="meet_id" value="<?php echo $row['meet_id'] ?>" />
            <input type="submit" name="submit" value="Enroll as Mentee"/>
        </form>
    </td>

    <td>
        <form method="post" action="enroll_as_mentee.php">
            <input type="hidden" name="all_meet_id" value="<?php echo $row['meet_id'] ?>" />
            <input type="hidden" name="all_group_id" value="<?php echo $row['group_id'] ?>" />
            <input type="hidden" name="all_meet_name" value="<?php echo $row['meet_name'] ?>" />
            <input type="submit" name="submit" value="Enroll in All Recurring Meetings"/>
        </form>
    </td>
    
</tr>
    <?php
    }
    ?>
</form>


<?php

if (isset($_POST['meet_id']))
{
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $meet_id = $_POST['meet_id'];

    $sql = "INSERT INTO mentees VALUES ($id)";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - Mentee<br>';
        echo mysqli_error($mysqli);
        echo '<br>';
    }
    else
    {
        echo 'Inserted - Mentee<br>';
    }
    
    

    $sql = "INSERT INTO enroll VALUES ($meet_id, $id)";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - enroll<br>';
        echo mysqli_error($mysqli);
        
    }
    else
    {
        echo 'Inserted - enroll<br>';
    }
    header("Refresh:0");
}
?>

<?php

if (isset($_POST['all_meet_id']))
{
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $meet_id = $_POST['all_meet_id'];
    $group_id = $_POST['all_group_id'];
    $meet_name = $_POST['all_meet_name'];

    $sql = "INSERT INTO mentees VALUES ($id)";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - Mentee<br>';
        echo mysqli_error($mysqli);
        echo '<br>';
    }
    else
    {
        echo 'Inserted - Mentee<br>';
    }
    
    echo $meet_name;

    //insert the student into all meetings from that group and that meeting type. Ex) All math meetings for grade 6
    $sql = "INSERT INTO enroll(meet_id, mentee_id)
                SELECT meet_id, $id FROM meetings WHERE group_id = $group_id
                    AND meet_name = '$meet_name'";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - BULK enroll<br>';
        echo mysqli_error($mysqli);
        
    }
    else
    {
        echo 'Inserted - BULK enroll<br>';
    }
    header("Refresh:0");
    
}
?>
 