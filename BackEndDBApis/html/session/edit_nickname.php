<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);


$id = $data["id"];
$password = $data["password"];
$name = $data["name"];

$update_sql = "UPDATE user SET name = '$name' WHERE id = '$id' AND password = '$password'";

// 닉네임에 해당하는 값을 찾는 sql
$name_duplicate_sql = "select id from User where name = '$name'";

// 닉네임 sql 결과의 갯수가 1개 이상일 경우 -> nickname Duplicated.
$name_rows = mysqli_query($con, $name_duplicate_sql);
$name_count = mysqli_num_rows($name_rows);

$response = array();
header('Content-Type: application/json');

if($name_count > 0) {
    $response["result"] = "Duplicated nickname!";
} else {
    if ($con->query($insert_sql)) {
        $response["result"] = "edit nickname success";
    } else {
        $response["result"] = "edit nickname Error";
    }
}

echo json_encode($response);

?>