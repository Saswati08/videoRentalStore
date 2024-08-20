# **Requirements**

You need to have Java JDK 17 installed on your machine and available on your path.

# Instructions

The application is built using Maven.
To build the application and run test cases, use **mvn clean install** .
Any tests that you add under the tests folder can be run using **mvn clean test** .
To run the application, use **mvn spring-boot:run**

# Sequence
1. Add inventory using add video api: 
sample curl: `curl --location 'localhost:8080/videos' \
   --header 'Content-Type: application/json' \
   --data '{
   "videoList": [
   {
   "videoName": "Stree",
   "videoType": "NEW_RELEASES"
   },
   {
   "videoName": "Srikanth",
   "videoType":"NEW_RELEASES"
   },
   {
   "videoName": "Kal ho na ho",
   "videoType":"REGULAR"
   },
   {
   "videoName": "Rowdy Rathore",
   "videoType": "REGULAR"
   },
   {
   "videoName": "Shawshank Redemption",
   "videoType": "OLD_RELEASES"
   },
   {
   "videoName": "Mughal e Azam",
   "videoType": "OLD_RELEASES"
   }
   ]
   }'`
2. See the videos available in the system using get video api
sample curl: `curl --location 'localhost:8080/videos'`
3. Rent videos using rental api
sample curl: 
`curl --location 'localhost:8080/videos/rental' \
   --header 'Content-Type: application/json' \
   --data '{
   "requestId": "dd8da6e6-1916-48d1-b0f4-9d702d68c705",
   "rentInfo": {
   "videoList": [
   {
   "videoId": "26c0759d-5382-4558-b64a-8d48830c799e",
   "days": 3
   },
   {
   "videoId": "f9ee4529-a8ef-45a4-9657-c4eac41bcf50",
   "days": 7
   },
   {
   "videoId": "b4727dac-bf57-4eaf-9807-2850a595d489",
   "days": 8
   },
   {
   "videoId": "589dc4bd-2e94-47a3-a239-d492486e2e9f",
   "days": 8
   },
   {
   "videoId": "14a06205-db7e-445b-ab07-472f849f4ada",
   "days": 5
   }
 ]
},
"returnInfo": {
    "rentId": "29b06713-2ab2-4a6d-bcd6-37fae2172dcf",
    "videoList":[
        {"videoId": "26c0759d-5382-4558-b64a-8d48830c799e",
        "days": 10
        }
    ]
}
}'`
4. Get rent information using get rent api
sample curl:
  ` curl --location 'localhost:8080/videos/rent'`



# Assumptions:
1. There are infinite number of rentals available for a particular video, so quantity is not stored in inventory nor are there any validations regarding it
2. Negative/Edge cases for post video, get video and get rent are not handled
3. To simplify testing days parameter is taken as input in return instead of calculating from created_on parameter of rent and current date
4. Authentication/Authorization is not handled
5. Administration makes one request at a time and quantity is infinite so concurrency is not handled. Eg: youtube video rentals
6. Since only one api was asked to design and develop for both rent and return, even if return is success and return is not we will get error status code in 4XX or 5XX

# Miscellaneous
Sample request and responses given inside resources folder

# Rent-Return API
end point: videos/rental
Method: POST
Request Body: Given as json in resources/request/RentReturnRequest.json
Response Body: resources/response/RentReturnVideoResponse.json
Http Code:
Success: 200
InvalidInputException: 400 (Input is not as expected)
InternalServerException: 500 (Unexpected error)
Error scenario response: resources/response/ErrorResponse.json

