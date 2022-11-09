<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$id = $data["id"];

$delete_query = "delete from User where id = '$id'";

$query_result = mysqli_query($con, $delete_query);

$response = array();
header('Content-Type: application/json');

if ($query_result) {
    $response["result"] = "Delete success";
} else {
    $response["result"] = "Delete fail";
}

echo json_encode($response);

?>