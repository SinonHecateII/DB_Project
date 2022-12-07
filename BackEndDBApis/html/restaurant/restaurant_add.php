<?php
include("../secret_constant.php");

mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$location = $data["location"];
$name = $data["name"];
$mood = $data["mood"];
$photoCnt = $data["photoCnt"];
$Writer = $data["Writer"];

$insert_sql = "INSERT INTO  restaurant(location, name, mood, photoCnt, Writer) values ('$location', '$name', '$mood', '$photoCnt', '$Writer') ";

$response = array();
header('Content-Type: application/json');

if ($con->query($insert_sql)) {
    $response["result"] = "restaurant add success";
    $idx_search = "SELECT restaurantID FROM restaurant WHERE location = '$location' AND name = '$name'";

    $idx_result = mysqli_query($con, $idx_search);
    $response["restaurantID"] = (int)mysqli_fetch_array($idx_result)[0];

} else {
    $response["result"] = "restaurant add fail";
}

echo json_encode($response);

?>