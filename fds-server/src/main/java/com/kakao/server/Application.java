package com.kakao.server;

import com.kakao.event.DepositEvent;
import com.kakao.event.NewAccountEvent;
import com.kakao.event.WithdrawEvent;
import com.kakao.event.queue.EventQueue;
import com.kakao.event.queue.TimeBoundedQueue;
import com.kakao.event.queue.TimeBoundedDeduplicatedQueue;
import com.kakao.kafka.ProducerThreadHandler;
import com.kakao.rule.scheme.AccountHandler;
import com.kakao.server.detect.DetectHandler;
import com.kakao.event.Event;
import com.kakao.kafka.ConsumerThreadHandler;


public class Application {
  public static void run() {
    String brokers = "localhost:9092";
    String groupId = "fds-consumer";
    String eventTopic = "bank.events";
    String sendTopic = "fds.detection";

    int numberOfThread = 3;

    long timeBoundMillis = 10000;//10000ms 이내의 것만 큐에서 관리 탐지
    EventQueue<Event> receiveQueue = new TimeBoundedQueue(timeBoundMillis);
    EventQueue<Event> sendQueue = new TimeBoundedDeduplicatedQueue(timeBoundMillis);

    // KAFKA EVENT SOURCE
    ConsumerThreadHandler consumers = new ConsumerThreadHandler(receiveQueue, brokers, groupId, eventTopic, numberOfThread);
    consumers.execute();
    System.out.println("Start Consumer");

    // DETECTOR = EVENT + RULE
    AccountHandler accountHandler = new AccountHandler();
    DetectHandler detectors = new DetectHandler(numberOfThread);
    detectors.execute(receiveQueue, sendQueue, accountHandler);
    System.out.println("Start Detector");

    ProducerThreadHandler producers = new ProducerThreadHandler(sendQueue, brokers, sendTopic, numberOfThread);
    producers.execute();
    System.out.println("Start Producer");

//    consumers.shutdown();
//    detectors.shutdown();
//    producers.shutdown();
  }

  private static void putNewAccountEvent(EventQueue<Event> queue) {
    for (int i = 0; i < 3; i++) {
      NewAccountEvent e = new NewAccountEvent();
      e.setAccountNumber(1111 * (i + 1));
      e.setAmount(0);
      e.setCustomerNumber(1 * (i + 1));
      e.setTimestamp(System.currentTimeMillis());
      e.setType();
      queue.put(e);
    }
  }

  private static void putDepositEvent(EventQueue<Event> queue, long amount) {
    for (int i = 0; i < 3; i++) {
      DepositEvent e = new DepositEvent();
      e.setAccountNumber(1111 * (i + 1));
      e.setAmount(amount);
      e.setCustomerNumber(1 * (i + 1));
      e.setTimestamp(System.currentTimeMillis());
      e.setType();
      queue.put(e);
    }
  }

  private static void putWithDrawEvent(EventQueue<Event> queue, long amount) {
    for (int i = 0; i < 3; i++) {
      WithdrawEvent e = new WithdrawEvent();
      e.setAccountNumber(1111 * (i + 1));
      e.setAmount(amount);
      e.setCustomerNumber(1 * (i + 1));
      e.setTimestamp(System.currentTimeMillis());
      e.setType();
      queue.put(e);
    }
  }
  public static void main(String[] args) {
    String brokers = "localhost:9092";
    String topic = "bank.events";
    long timeBoundMillis = 1000;

    EventQueue<Event> queue = new TimeBoundedQueue(timeBoundMillis);

    Application.putNewAccountEvent(queue);
    ProducerThreadHandler producers = new ProducerThreadHandler(queue, brokers, topic, 3);
    producers.execute();

    run();

    try {
      Thread.sleep(100);
      Application.putDepositEvent(queue, 950000);
      Thread.sleep(100);
      Application.putWithDrawEvent(queue, 943000);
      Thread.sleep(1000);
      Application.putWithDrawEvent(queue, 3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

