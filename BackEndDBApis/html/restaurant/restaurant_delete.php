<?php

include("../secret_constant.php");

mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$restaurantID = $data["restaurantID"];
$delete_sql = "delete from restaurant where restaurantID = '$restaurantID'";

$query_result = mysqli_query($con, $delete_sql);

$response = array();
header('Content-Type: application/json');

if ($query_result) {
    $response["result"] = "Delete success";
} else {
    $response["result"] = "Delete fail";
}

echo json_encode($response);

?>