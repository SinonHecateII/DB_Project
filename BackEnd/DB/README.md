# About DB
> 이미지 파일명 형식

[restaurantID]_img[0 ~ photoCnt - 1].jpg
+ restaurantID = restaurant 테이블에서 각 식당의 primary Key
+ photoCnt = 해당 식당의 사진 수
+ ex) restaurantID가 1인 이미지의 photoCnt가 2라면 1_img0.jpg와 1_img1.jpg 파일이 서버에 존재한다.