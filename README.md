# fds-project

### 01. Requirements

1. Java 1.8
2. Maven Project
3. Kafka
4. POJO



### 02. TO-DO

> Develop FDS System that detects abnormal transactions by judging whether various rules are violated in real time. 
> Assume that the information needed for abnormal transaction detection is provided as a live event through Kafka.



### 03. Project Structure

- Rule-engine
  - An engine that allows you to set the criteria needed for FDS detection, and to create rules that combine these criteria in various combinations.
  - The currently implemented example rules are:
    - If the balance is less than 10,000 won after 90 ~ 10 million won has been deposited into the newly opened account within 7 days and withdrawn within 2 hours
- Kafka
  - It is a Kafka Consumer / Producer that fetches event data in real time and sends back the detected data.
  - Detecting abnormal transactions, Kafka topic 'fds.detections' sent to
- Event
  - It contains the Event and Queue classes that are essential to the overall FDS project configuration.
  - Event type
    - Account Creation Event
    - Transfer event
    - Deposit Event
    - Withdrawal event
- Fds-server
  - This module actually executes the event, kafka, and rule-engine of this project.