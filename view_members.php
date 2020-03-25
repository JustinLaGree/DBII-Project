<?php 
    session_start();
    
    //build header
    require("header.php");
?>

<h1>Currently Enrolled Members</h1>
<br/>
 

<?php
    $meet_id = $_SESSION['meet_id'];
    
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');

    $sql = "SELECT * FROM users WHERE id IN
                (SELECT mentee_id FROM enroll WHERE meet_id = $meet_id)";
    //select the meetings where the group_id is in the groups where the mentee_grade_req < the student grade level
    //echo $_SESSION["user"]["name"];

$result = $mysqli->query($sql);

?>
<form method="post" action="view_members.php">
    <table name="view_mentees" border = 2>
    <thead id="td">
        <tr>
            <td>Mentee Name</td>
            <td>Email</td>
            <td>Phone</td>
        </tr>
    <thead>
    <tbody>
        <?php
            
            while($row = $result->fetch_assoc()){?>
            <tr>
    <td>
        <?php echo $row[ 'name']?>

    </td>
    <td>
        <?php echo $row[ 'email'];?>
    </td>
    <td>
        <?php echo $row[ 'phone'];?>
    </td>
    
</tr>
    <?php
    }
    ?>
</form>

<?php
$sql2 = "SELECT * FROM users WHERE id IN
            (SELECT mentor_id FROM enroll2 WHERE meet_id = $meet_id)";
$result2 = $mysqli->query($sql2);
?>


<form method="post" action="view_members.php">
    <table name="view_mentors" border = 2>
    <thead id="td">
        <tr>
            <td>Mentor Name</td>
            <td>Email</td>
            <td>Phone</td>
        </tr>
    <thead>
    <tbody>
        <?php
            
            while($row = $result2->fetch_assoc()){?>
            <tr>
    <td>
        <?php echo $row[ 'name']?>

    </td>
    <td>
        <?php echo $row[ 'email'];?>
    </td>
    <td>
        <?php echo $row[ 'phone'];?>
    </td>
    
</tr>
    <?php
    }
    ?>
</form>




