<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$id = $data["id"];
$password = $data["password"];

$statement = mysqli_prepare($con, "SELECT * FROM User WHERE id = ? AND password = ?");
mysqli_stmt_bind_param($statement, "ss", $id, $password);
mysqli_stmt_execute($statement);


mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $id, $password, $name, $email);

$response = array();
$response["success"] = false;

while (mysqli_stmt_fetch($statement)) {
    $response["success"] = true;
    $response["id"] = $id;
    $response["password"] = $password;
    $response["name"] = $name;
    $response["email"] = $email;
}
header('Content-Type: application/json');
echo json_encode($response);
?>