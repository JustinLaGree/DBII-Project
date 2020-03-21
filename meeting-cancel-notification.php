<?php
//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//user must be an admin to access this page
if (!($_SESSION[USER][USERTYPE] === 1)){
    header("Location: index.php");
}

//setup sql connection
$mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

//get the meeting timeslot info for all meetings
$sql = $mysqli->prepare('SELECT * FROM meetings m, time_slot t WHERE m.time_slot_id = t.time_slot_id ORDER BY date ASC'); 

//get the result of the select query
$sql->execute();
$result = $sql->get_result();

$dow = date('l');
$now = time();

while ($row = $result->fetch_assoc()){
    $meetId = $row["meet_id"];
    $meetDateFormatted = $row["date"];
    $meetTimeFormatted = substr($row["end_time"], 0, 2) . "-" . substr($row["end_time"], 3, 2) . "_" . substr($row["start_time"], 0, 2) . "-" . substr($row["start_time"], 3, 2);

    $meetDate = strtotime($meetDateFormatted);
    $dateDif = round(($now - $meetDate) / (60 * 60 * 24));

    //if the current day is friday and meeting happens today, tomorrow, or sunday
    if ($dow == "Friday" && $dateDif > -1 && $dateDif <= 3){

        echo "Cancellation files have been created! <br/><br/>";

        $fileName = "meeting-cancellations/" . $meetDateFormatted . "_" . $meetTimeFormatted . "_Cancel.txt";
        $myfile = fopen($fileName, "w");

        if ($myfile){
            //get the meeting timeslot info for all meetings
            $sql = $mysqli->prepare('SELECT * FROM users WHERE id IN ((SELECT mentee_id as id FROM enroll WHERE meet_id = ?) UNION (SELECT mentor_id as id FROM enroll2 WHERE meet_id = ?))'); 
            $sql->bind_param('ii', $meetId, $meetId);

            //get the result of the select query
            $sql->execute();
            $result = $sql->get_result();

            while ($user = $result->fetch_assoc()){
                fwrite($myfile, $user["email"] . "\n");
            }
        }
        fclose($myfile);
    }
}
?>