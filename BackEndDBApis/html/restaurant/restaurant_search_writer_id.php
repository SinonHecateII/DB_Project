<?php
include("../secret_constant.php");
ini_set("display_errors", 0);

mysqli_query($con, 'SET NAMES utf8');

$data = json_decode(file_get_contents('php://input'), true);

$writerID = $data["writerID"];

$select_sql = "select * from restaurant where Writer = '$writerID'";

header('Content-Type: application/json');

$select_sql_result = mysqli_query($con, $select_sql);

// 쿼리문의 결과(res)를 배열형식으로 변환(result)
// 쿼리문의 결과(res)를 배열형식으로 변환(result)
while ($row = mysqli_fetch_array($select_sql_result)) {
    $response[] = array('restaurantID' => $row[0], 'location' => $row[1], 'name' => $row[2], 'mood' => $row[3], 'photoCnt' => $row[4], 'writerID' => $row[5]);
}
// 배열형식의 결과를 json으로 변환
echo json_encode(array("result" => $response), JSON_UNESCAPED_UNICODE);
// DB 접속 종료
mysqli_close($con);

?>