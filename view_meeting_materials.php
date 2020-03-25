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

$meetId = $_POST["meeting"];

//setup sql connection
$mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

//get all info for this specific meeting
$sql = $mysqli->prepare('SELECT * FROM material WHERE material_id IN (SELECT material_id from assign WHERE meet_id = ?) ORDER BY assigned_date ASC');
$sql->bind_param('i', $meetId);

//get the result of the select query
$sql->execute();
$result = $sql->get_result();
?>

<h3>View Materials for Meeting <?php echo $meetId; ?>: </h3>

<table border=2>
    <thead>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Type</th>
            <th>URL</th>
            <th>Assigned Date</th>
            <th>Notes</th>
        </tr>
    <thead>
    <tbody>
        <?php 
            //iterate over the unregistered students and output them to the table
            while ($row = $result->fetch_assoc()){
        ?>
        <tr>
            <td><?php echo $row["title"]; ?></td>
            <td><?php echo $row["author"]; ?></td>
            <td><?php echo $row["type"]; ?></td>
            <td><a <?php $url = $row["url"];
                echo "href='$url' target='_externalMaterials'>$url"; ?></a></td>
            <td><?php echo $row["assigned_date"]; ?></td>
            <td><?php echo $row["notes"]; ?></td>
        </tr>
    <?php } ?>
    </tbody>
</table>