<?php
include("../secret_constant.php");

mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$restaurantID = $data["restaurantID"];
$menuType = $data["menuType"];

$success_cnt = 0;

$delete_query = "delete from Menu where restaurantID = '$restaurantID'";
mysqli_query($con, $delete_query);

for($i = 0; $i < count($menuType); $i++) {
    $insert_sql = "insert into Menu values ('$restaurantID', '$menuType[$i]')";
    $query_result = mysqli_query($con, $insert_sql);
    if ($query_result) {
        $success_cnt++;
    }
}

$response = array();
header('Content-Type: application/json');

if ($success_cnt == count($menuType)) {
    $response["result"] = "restaurant add success";
} else {
    $response["result"] = "restaurant add fail";
}
$response["success_cnt"] = $success_cnt;

echo json_encode($response);

?>