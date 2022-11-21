<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$reviewID = $data["reviewID"];
$content = $data["content"];
$score = $data["score"];

$response = array();

if ($score != null) {
    $update_sql = "update review set score = '$score' where reviewID = '$reviewID'";
    $query_result = mysqli_query($con, $update_sql);
    if ($query_result) {
        $response["score"] = "success";
    } else {
        $response["score"] = "fail";
    }
}

if ($content != null) {
    $update_sql = "update review set content = '$content' where reviewID = '$reviewID'";
    $query_result = mysqli_query($con, $update_sql);
    if ($query_result) {
        $response["content"] = "success";
    } else {
        $response["content"] = "fail";
    }
}

header('Content-Type: application/json');

echo json_encode($response);

?>