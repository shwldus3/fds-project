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
  
  
  
코딩 과제
=======
- 실시간으로 다양한 규칙들의 위배 여부를 판단해 이상거래를 탐지하는 FDS(이상거래 탐지 시스템)를 개발하고자 한다.
- 이상거래 탐지에 필요한 정보가 Kafka를 통해 실시간 이벤트로 제공된다는 가정 하에, 아래의 요구사항을 준수한 프로그램을 Java로 구현하라.
- 1차 면접시 '규칙 A'에 대한 이상거래 탐지를 시연하고 동작 원리를 설명한다.

### 요구사항 ###
- Maven 기반 프로젝트
- '규칙 A'와 유사한 규칙들을 수용할 수 있는 룰 엔진 구현
- 구현한 룰 엔진을 이용해 '규칙 A' 구현
- Kafka를 실시간 이벤트 데이터 소스로 사용
- 실시간으로 유입되는 이벤트에 대해 구현한 룰 엔진을 이용해 100ms 이내에 이상거래를 탐지
- 탐지된 이상거래 내용을 탐지 즉시 Kafka 토픽 'fds.detections'에 발송
- 주어진 이벤트 데이터에 대한 가상의 부하를 만들어 룰 엔진을 테스트하는 테스트 코드 작성
- Mission Critical한 환경의 Production-Ready 수준으로 구현 (평가항목 참조)

### 규칙 A ###
- 7일 이내에 신규로 개설된 계좌로 90~100 만원이 입금된 후 2시간 이내에 출금되어 잔액이 1만원 이하가 되는 경우

### 이벤트 데이터 ###
아래의 유형으로 이벤트를 구현해 Kafka 토픽 'bank.events'에 실시간으로 Publish 한다.

* 계좌 신설 이벤트
- 발생시각
- 고객번호
- 계좌번호

* 이체 이벤트
- 발생시각
- 고객번호
- 송금 계좌번호
- 송금 이체전 계좌잔액
- 수취 은행
- 수취 계좌주
- 이체 금액

* 입금 이벤트
- 발생시각
- 고객번호
- 계좌번호
- 입금 금액

* 출금 이벤트
- 발생시각
- 고객번호
- 계좌번호
- 출금 금액

### 제약사항 ###
- POJO 형태로 구현 (Spring, Guava, Lombok이나 오픈소스 Rule Engine 등의 프레임워크 일절 사용 금지)

### 평가항목 ###   
- Maven을 잘 활용했는지 여부   
- Kafka API를 제대로 사용했는지 여부   
- OOP 기반의 클래스 설계와 구조화가 잘 되었는지 여부   
- Production-Ready 수준 코드 구현 여부   
  - 클린 코드
  - 오류없이 동작   
  - Thread-Safety 준수   
  - 성능 및 공간 최적화   
