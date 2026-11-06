# MealRestController — curl

## Get

curl.exe -i "http://localhost:8081/topjava/rest/profile/meals/100003"

## GetAll

curl.exe -i "http://localhost:8081/topjava/rest/profile/meals/"

## Delete

curl.exe -i -X DELETE "http://localhost:8081/topjava/rest/profile/meals/100007"

## Update

curl.exe -i -X PUT "http://localhost:8081/topjava/rest/profile/meals/100003" ^
-H "Content-Type: application/json" ^
-d "{\"id\": 100003,\"dateTime\": \"2020-01-30T10:00\",\"description\": \"Завтрак\",\"calories\": 800}"

## CreateWithLocation

curl.exe -i -X POST "http://localhost:8081/topjava/rest/profile/meals/" ^
-H "Content-Type: application/json" ^
-d "{\"dateTime\": \"2020-01-30T11:00\",\"description\": \"Завтрак2\",\"calories\": 100}"

## GetBetween

curl.exe
-i "http://localhost:8081/topjava/rest/profile/meals/get-between?startDate=2020-01-31&startTime=09:00&endDate=2020-01-31&endTime=14:00"