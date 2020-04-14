<?php

//setup request constants
define('REQUEST_METHOD', 'REQUEST_METHOD');
define('POST', 'POST');
define('POST_BODY_ADDRESS', 'php://input');
define('QUERY', 'query');
define('INVALID_REQUEST', 'Not a valid request!');

//setup database constants
define("SERVER_NAME", "localhost");
define("DB_USER", "root");
define("DB_PWD", "");
define("DB_NAME", "DB2");

//verify that the request is a POST request
if($_SERVER[REQUEST_METHOD] === POST) {
    //setup db connection
    $mysqli = new mysqli(SERVER_NAME, DB_USER, DB_PWD, DB_NAME);

    //get raw request body
    $requestBody = file_get_contents(POST_BODY_ADDRESS);

    //decode the raw json and get the query value from it
    $obj = json_decode($requestBody, true);
    $query = $obj[QUERY];

    //construct query object
    $sql = $mysqli->prepare($query);

    //get the result of the select query
    $sql->execute();
    $result = $sql->get_result();
    
    //build results array from query results
    $resultArr = array();
    while($row = $result->fetch_assoc()) {
        array_push($resultArr, $row);
    }

    //return response with jsonified array
    exit(json_encode($resultArr));
}
else {
    //return invalid response message
    exit(json_encode(INVALID_REQUEST));
}
?>