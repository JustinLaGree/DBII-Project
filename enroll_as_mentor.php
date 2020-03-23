<?php 
    session_start();
    
    //build header
    require("header.php");
?>

<h1>Enroll As a Mentor</h1>
<br/> 

<?php
    $id = $_SESSION['user']['id'];
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $sql = "SELECT * FROM meetings WHERE group_id IN (SELECT group_id FROM groups WHERE mentor_grade_req <= (SELECT grade FROM students WHERE student_id = $id))
                AND date NOT IN (SELECT date from meetings WHERE meet_id in (SELECT meet_id FROM enroll2 where mentor_id = $id))";
    //select the meetings where the group_id is in the groups where the mentee_grade_req < the student grade level
    //echo $_SESSION["user"]["name"];

$result = $mysqli->query($sql);
?>


<form method="post" action="enroll_as_mentor.php">
    <table name="available_meetings" border = 2>
    <thead id="td">
        <tr>
            <td>Group ID</td>
            <td>Meeting Name</td>
            <td>Date</td>
            <td>Current Mentor Enrollment</td>
            <td>Enroll</td>
        </tr>
    <thead>
    <tbody>
        <?php
            
            while($row = $result->fetch_assoc()){?>
            <tr>
    <td>
        <?php echo $row[ 'group_id']?>

    </td>
    <td>
        <?php echo $row[ 'meet_name']?>

    </td>
    <td>
        <?php echo $row[ 'date'];?>
    </td>
    <td>
        <?php
            $mentor_cap_sql = "SELECT COUNT(mentor_id) as c1 FROM enroll2 WHERE meet_id = $row[meet_id]";
            $result2 = $mysqli->query($mentor_cap_sql);
            echo $mysqli->error;
            while($row2 = $result2->fetch_assoc()){
            echo $row2[ 'c1'];
            echo '/3';
            }
        ?>
    </td>
    <td>
        <form method="post" action="enroll_as_mentor.php">
            <input type="hidden" name="meet_id" value="<?php echo $row['meet_id'] ?>" />
            <input type="submit" name="submit" value="Enroll as Mentor"/>
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

    $sql = "INSERT INTO mentors VALUES ($id)";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - Mentor<br>';
        echo mysqli_error($mysqli);
        echo '<br>';
    }
    else
    {
        echo 'Inserted - Mentor<br>';
    }
    
    

    $sql = "INSERT INTO enroll2 VALUES ($meet_id, $id)";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - enroll2<br>';
        echo mysqli_error($mysqli);
        
    }
    else
    {
        echo 'Inserted - enroll2<br>';
    }
    header("Location: enroll_as_mentor.php");
}
?>