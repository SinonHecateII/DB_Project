# About DB
```
/*Menu 테이블로 해당 식당에서 판매하고 있는 음식 메뉴를 저장한다.*/

CREATE TABLE Menu(
    restaurantID INT NOT NULL,
    menuType VARCHAR(10),
    FOREIGN KEY (restaurantID)
    REFERENCES restaurant(restaurantID)
);
```
```
/*User 테이블로 사용자의 아이디, 닉네임 그리고 비밀번호를 저장한다.*/

CREATE TABLE user(
    Id VARCHAR(20) NOT NULL,
    Name VARCHAR(20),
    Password VARCHAR(20),
    PRIMARY KEY(iD)
);
```
```
/*Review 테이블로 작성한 리뷰와 별점 및 작성 일자 정보를 담고 있으며 Restaurant 테이블과 User 테이블을 외래키로써 참조한다.*/

CREATE TABLE review(
    reviewID INT NOT NULL AUTO_INCREMENT,
    content VARCHAR(20),
    score INT,
    createdAt INT
    restaurantId INT,
    writer VARCHAR(20),
    PRIMARY KEY(reviewID),
    FOREIGN KEY(restaurantID) REFERENCES restaurant(restaurantID),
    FOREIGN KEY(writer) REFERENCES User(Name)
);
```
```
/*Restaurant 테이블로 식당의 위치, 이름 그리고 분위기 정보를 담고있다.*/

CREATE TABLE restaurant(
    restaurantID INT NOT NULL AUTO_INCREMENT,
    location VARCHAR(10),
    name VARCHAR(20),
    photoCnt INT,
    mood VARCHAR(20),
    PRIMARY KEY(restaurantID)
);
```

> 이미지 파일명 형식

[restaurantID]_img[0 ~ photoCnt - 1].jpg
+ restaurantID = restaurant 테이블에서 각 식당의 primary Key
+ photoCnt = 해당 식당의 사진 수
+ ex) restaurantID가 1인 이미지의 photoCnt가 2라면 1_img0.jpg와 1_img1.jpg 파일이 서버에 존재한다.