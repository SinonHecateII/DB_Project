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
    $response[] = array('reviewID' => $row[0], 'content' => $row[1], 'score' => $row[2], 'createdAt' => $row[3], 'restaurantID' => $row[4], 'writer' => $row[5]);
}

// 배열형식의 결과를 json으로 변환
echo json_encode(array("result" => $response), JSON_UNESCAPED_UNICODE);
// DB 접속 종료
mysqli_close($con);

?>