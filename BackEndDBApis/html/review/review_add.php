<?php
include("../secret_constant.php");

mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$content = $data["content"];
$score = $data["score"];
$restaurantID = $data["restaurantID"];
$writer = $data["writer"];
$createdAt = $data["createdAt"];

$insert_sql = "INSERT INTO  review(content, score, restaurantID, writer, createdAt) values ('$content', '$score', '$restaurantID', '$writer', '$createdAt') ";

$response = array();
header('Content-Type: application/json');

if ($con->query($insert_sql)) {
    $response["result"] = "restaurant add success";
} else {
    $response["result"] = "restaurant add fail";
}

echo json_encode($response);

?>