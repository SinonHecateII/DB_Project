<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$id = $data["id"];
$password = $data["password"];
$name = $data["name"];

// 아이디와 비밀번호에 해당하는 닉네임 검색
$select_sql = "SELECT User name = '$name' where id = '$id', pw = "$password" ";

$query_result = mysqli_query($con, $update_sql);

$response = array();
header('Content-Type: application/json');

if($name) {
    $response["name"] = $name;
}

echo json_encode($response);

?>