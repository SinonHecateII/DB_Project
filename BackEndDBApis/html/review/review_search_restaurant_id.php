<?php
header('Content-Type: text/html; charset=utf-8');
header('Content-Type: application/json');


include("../secret_constant.php");
ini_set("display_errors", 0);



$data = json_decode(file_get_contents('php://input'), true);

$restaurantID = $data["restaurantID"];

$select_sql = "select * from review where restaurantID = '$restaurantID'";
$response = array();

$select_sql_result = mysqli_query($con, $select_sql);




// 쿼리문의 결과(res)를 배열형식으로 변환(result)
while ($row = mysqli_fetch_array($select_sql_result)) {
    $nickname_sql = "select Name from User where Id = '$row[4]'";
    $nickname_sql_result = mysqli_query($con, $nickname_sql);
    $nickname = mysqli_fetch_array($nickname_sql_result);

    $response[] = array('reviewID' => $row[0], 'content' => $row[1], 'score' => $row[2], 'restaurantID' => $row[3], 'nickname' => $nickname[0], 'writer' => $row[4]);
}

// 배열형식의 결과를 json으로 변환
echo json_encode(array("result" => $response), JSON_UNESCAPED_UNICODE);
// DB 접속 종료
mysqli_close($con);

?>