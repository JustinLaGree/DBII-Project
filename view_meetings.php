<?php 
    session_start();
    
    //build header
    require("header.php");
?>

<h1>Currently Enrolled Meetings</h1>
<br/>
 

<?php
    $id = $_SESSION['user']['id'];
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $sql = "SELECT * FROM meetings WHERE meet_id IN (SELECT meet_id FROM enroll WHERE mentee_id = $id) OR meet_id IN (SELECT meet_id FROM enroll2 WHERE mentor_id = $id)";
    //select the meetings where the group_id is in the groups where the mentee_grade_req < the student grade level
    //echo $_SESSION["user"]["name"];

$result = $mysqli->query($sql);

?>


<form method="post" action="view_meetings.php">
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
            
            while($row = $result->fetch_assoc()){
                $meetId = $row["meet_id"]; ?>
        <tr>
            <td>
                <?php echo $row[ 'meet_name']?>

            </td>
            <td>
                <?php echo $row[ 'date'];?>
            </td>
            <td>
                <form method="post" action="view_meetings.php">
                    <input type="hidden" name="meet_id" value="<?php echo $row['meet_id'] ?>" />
                    <input type="submit" name="submit" value="Remove Enrollment"/>
                </form>
            </td>
            <td>
                <form method="post" action="view_meetings.php">
                    <input type="hidden" name="view_members_id" value="<?php echo $row['meet_id'] ?>" />
                    <input type="submit" name="submit" value="View Members"/>
                </form>
            </td>
            <td>                
                <form method="post" action='view_meeting_materials.php'>
                    <input type="hidden" name="meeting" value="<?php echo $row['meet_id'] ?>" />
                    <input type="submit" name="submit" value="View Materials"/>
                </form>
            </td>
        </tr>
    <?php
    }
    ?>
    </tbody>
</form>


<?php

if (isset($_POST['meet_id']))
{
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $meet_id = $_POST['meet_id'];

    $sql = "DELETE FROM mentees VALUES ($id)";

    //get the result of the select query
    if($mysqli->query($sql))
    {
        echo 'Inserted';
    }
    
    

    $sql = "DELETE FROM enroll WHERE meet_id = $meet_id AND mentee_id = $id";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Deleted - enroll';
        echo mysqli_error($mysqli);
        
    }

    $sql = "DELETE FROM enroll2 WHERE meet_id = $meet_id AND mentor_id = $id";

    //get the result of the select query
    if(!$mysqli->query($sql))
    {
        echo 'Not Deleted - enroll2';
        echo mysqli_error($mysqli);
        
    }

    header("Refresh:0");
}

if (isset($_POST['view_members_id']))
{
    $_SESSION['meet_id'] = $_POST['view_members_id'];
    echo $_SESSION['meet_id'];
    header('Location: view_members.php');
}
?>