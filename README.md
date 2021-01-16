# interview-subscription
What You Need
JDK 1.8 or later
Maven 3.2+

You can also import the code straight into your IDE.

To run , you can obtain the subscription-0.0.1-SNAPSHOT.jar under target/ folder and run the command
on the termninal.

java -jar target/subscription-0.0.1-SNAPSHOT.jar

Test cases are written with JUNIT under /src/test/java/com/fookkeong/subscsription/SubscriptionApplicationTests

You can also use the curl command to invoke the REST API.
i.e.
curl -i \
-H "Accept: application/json" \
-H "Content-Type:application/json" \
-X POST --data 
  '{"amount": "40.0", "subscriptionType": "w", "dayOfWeek": "Friday", "startDate": "15/01/2021","endDate": "20/02/2021"}' "https://localhost:8080/subscription/subscribe"
  
The input for the API are as followed:
amount: a double value
subscriptionType:
'd' -> DAILY
'w' -> WEEKLY
'm' -> MONTHLY

dayOfWeek:
if subscriptionType = 'w', dayOfWeek will be day of the week .i.e Tuesday
if subscriptionType = 'm', dayOfWeek will be day of the month .i.e. 20

startDate & endDate:
The expected format is dd/MM/yyyy


