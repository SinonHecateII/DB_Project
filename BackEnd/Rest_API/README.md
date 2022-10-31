# REST API 외부 접속 허용
    #Install Localtunnel
    > npm install localtunnel

    #To Portfowarding
    > lt --port [Port_Num]
    
    #To change address to subdomain
    > lt --port [Port_Num] --subdomain [Subdomain name]



> 이미지 url : http://dbproject.com/shareimage/[이미지이름].jpg

API URL : https://test.loca.lt
# POST
> /upload

Key : "img", Value : [jpg형식의 파일]
+ status code 200
    > 파일 전송 성공

# GET
> /response
+ status code 201
    > return 형식 : json