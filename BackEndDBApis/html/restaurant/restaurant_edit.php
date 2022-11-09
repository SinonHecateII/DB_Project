<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$restaurantID = $data["restaurantID"];
$location = $data["location"];
$name = $data["name"];
$mood = $data["mood"];
$photoCnt = $data["photoCnt"];

$response = array();

if ($location != null) {
    $result = mysqli_query($con, "update restaurant set location  = '$location' where restaurantID = '$restaurantID'");
    if($result) {
        $response["location"] = "success";
    } else {
        $response["location"] = "fail";
    }
}
if ($name != null) {
    $result = mysqli_query($con, "update restaurant set name  = '$name' where restaurantID = '$restaurantID'");
    if($result) {
        $response["name"] = "success";
    } else {
        $response["name"] = "fail";
    }
}
if ($mood != null) {
    $result = mysqli_query($con, "update restaurant set mood  = '$mood' where restaurantID = '$restaurantID'");
    if($result) {
        $response["mood"] = "success";
    } else {
        $response["mood"] = "fail";
    }
}
if (is_numeric($photoCnt)) {
    $result = mysqli_query($con, "update restaurant set photoCnt  = '$photoCnt' where restaurantID = '$restaurantID'");
    if($result) {
        $response["photoCnt"] = "success";
    } else {
        $response["photoCnt"] = "fail";
    }
}

header('Content-Type: application/json');


echo json_encode($response);

?>