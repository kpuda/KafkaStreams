# KafkaStreams
This is a project created for my public speech at incoming ING conference.<br>
Use postman for sending POST's:<br>
- localhost:8080/sendString <br>
- localhost:8080/sendStringFromDifferentTopic<br>
- localhost:8080/sendPizza<br><br>
# Example of json for pizzaRequest: 
{
    "pizzaOrderId": "923ad84c-13ae-4cff-9575-c03c213a18e6",
    "recipientDetails": {
        "firstName": "John",
        "lastName": "Doe",
        "phoneNumber": "+48 543 258 751",
        "recipientAddress": {
            "city": "Katowice",
            "street": "Gliwicka",
            "number": "9",
            "apartamentNumber":"25"
        }
    },
    "pizzaDetails": {
        "pizzaSize": "BIG",
        "doughType": "THIN",
        "ingredients": [
            "Salami",
            "Mozzarella",
            "pineapple"
        ]
    }
}
# DOCKER
Open cmd in directory ><b> docker-compose up</b> 
<br>and the whole magic happens
# JAVA 
Compile and start two apps
