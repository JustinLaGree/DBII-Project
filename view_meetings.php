<h1>Currently Enrolled Meetings</h1>
<br/>

<?php 
    session_start();
?>



 

<?php
    $id = $_SESSION['user']['id'];
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $sql = "SELECT * FROM meetings WHERE meet_id IN (SELECT meet_id FROM enroll WHERE mentee_id = $id) OR meet_id IN (SELECT meet_id FROM enroll2 WHERE mentor_id = $id)";
    //select the meetings where the group_id is in the groups where the mentee_grade_req < the student grade level
    //echo $_SESSION["user"]["name"];

$result = $mysqli->query($sql);

?>


<form method="post" action="enroll_as_mentee.php">
    <table name="available_meetings" border = 2>
    <thead id="td">
        <tr>
            <td>Meeting Name</td>
            <td>Date</td>
            <td>Choose</td>
        </tr>
    <thead>
    <tbody>
        <?php
            
            while($row = $result->fetch_assoc()){?>
            <tr>
    <td>
        <?php echo $row[ 'meet_name']?>

    </td>
    <td>
        <?php echo $row[ 'date'];?>
    </td>
    <td>
        <form method="post" action="enroll_as_mentee.php">
            <input type="hidden" name="meet_id" value="<?php echo $row['meet_id'] ?>" />
            <input type="submit" name="submit" value="Remove Enrollment"/>
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
    if($mysqli->query($sql))
    {
        echo 'Inserted';
    }
    
    

    $sql = "INSERT INTO enroll VALUES ($meet_id, $id)";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Inserted - enroll';
        echo mysqli_error($mysqli);
        
    }
    else
    {
        echo 'Inserted';
    }
}
?>