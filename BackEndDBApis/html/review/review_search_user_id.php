<?php
header('Content-Type: text/html; charset=utf-8');
header('Content-Type: application/json');


include("../secret_constant.php");
ini_set("display_errors", 0);



$data = json_decode(file_get_contents('php://input'), true);

$writer = $data["writer"];

$select_sql = "select * from review where Writer = '$writer'";
$response = array();

$select_sql_result = mysqli_query($con, $select_sql);



// 쿼리문의 결과(res)를 배열형식으로 변환(result)
while ($row = mysqli_fetch_array($select_sql_result)) {
    $restaurant_name_sql = "select name from restaurant where restaurantID = '$row[3]'";
    $restaurant_name_sql_result = mysqli_query($con, $restaurant_name_sql);
    $restaurant_name = mysqli_fetch_array($restaurant_name_sql_result);
    $response[] = array('restaurantName' => $restaurant_name[0], 'reviewID' => $row[0], 'content' => $row[1], 'score' => $row[2], 'restaurantID' => $row[3], 'writer' => $row[4]);
}



// 배열형식의 결과를 json으로 변환
echo json_encode(array("result" => $response), JSON_UNESCAPED_UNICODE);
// DB 접속 종료
mysqli_close($con);

?>