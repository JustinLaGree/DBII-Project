<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "DB2";

if (isset($_POST['form_submitted'])){
    $mysqli = new mysqli($servername, $username, $password, $dbname);
    
    if(isset($_POST['email'])){
        $email = $_POST['email'];
        
        $sql = "SELECT * FROM users WHERE email='". $email ."'";
        $result = $mysqli->query($sql);
        
        if ($result->num_rows > 0) {
        // output data of each row
            while($row = $result->fetch_assoc()) {
                echo "id: " . $row["id"]. " - Name: " . $row["name"]. "<br>";
            }
        } else {
            echo "0 results";
        }
    }
}
else {
    header("Location: login.php");
}
?>