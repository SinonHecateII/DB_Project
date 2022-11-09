<?php
include("../secret_constant.php");
mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);


$id = $data["id"];
$password = $data["password"];
$name = $data["name"];
$email = $data["email"];

$insert_sql = "INSERT INTO User values ('$id', '$password', '$name', '$email')";

// 아이디에 해당하는 값을 찾는 sql
$id_duplicate_sql = "SELECT id FROM User where id = '$id'";

// 해당 sql 결과의 갯수가 1개 이상일 경우 -> id Duplicated.
$id_rows = mysqli_query($con, $id_duplicate_sql);
$id_count = mysqli_num_rows($id_rows);


// 닉네임에 해당하는 값을 찾는 sql
$name_duplicate_sql = "select id from User where name = '$name'";

// 닉네임 sql 결과의 갯수가 1개 이상일 경우 -> nickname Duplicated.
$name_rows = mysqli_query($con, $name_duplicate_sql);
$name_count = mysqli_num_rows($name_rows);

$response = array();
header('Content-Type: application/json');

if ($id_count > 0) {
    $response["result"] = "Duplicated id";
} else {
    if($name_count > 0) {
        $response["result"] = "Duplicated nickname!";
    } else {
        if ($con->query($insert_sql)) {
            $response["result"] = "sign up success";
        } else {
            $response["result"] = "sign up Error";
        }
    }
}
echo json_encode($response);

?>