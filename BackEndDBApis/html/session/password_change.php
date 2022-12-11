<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);


$id = $data["id"];
$password = $data["password"];

$update_sql = "UPDATE User SET Password = '$password' where id = '$id' ";

$response = array();
header('Content-Type: application/json');


if (mysqli_query($con, $update_sql)) {
    $response["result"] = "Password Update success";
} else {
    $response["result"] = "Password Update fail";
}

echo json_encode($response);

?>