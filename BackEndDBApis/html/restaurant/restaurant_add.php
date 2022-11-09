<?php
include("../secret_constant.php");

mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$location = $data["location"];
$name = $data["name"];
$mood = $data["mood"];
$photoCnt = $data["photoCnt"];

$insert_sql = "INSERT INTO  restaurant(location, name, mood, photoCnt) values ('$location', '$name', '$mood', '$photoCnt') ";

$response = array();
header('Content-Type: application/json');

if ($con->query($insert_sql)) {
    $response["result"] = "restaurant add success";
} else {
    $response["result"] = "restaurant add fail";
}

echo json_encode($response);

?>