package com.kakao.server;

import com.kakao.event.Event;
import com.kakao.event.NewAccountEvent;
import com.kakao.event.queue.EventQueue;
import com.kakao.event.queue.TimeBoundedQueue;
import com.kakao.kafka.ProducerThreadHandler;
import org.junit.Before;
import org.junit.Test;

public class AppTest {
  @Before
  public void setUp() throws Exception {
    String brokers = "localhost:9092";
    String topic = "bank.events";
    long timeBoundMillis = 1000;

    EventQueue<Event> queue = new TimeBoundedQueue(timeBoundMillis);
    //NewAccount - 3명
    for (int i = 0; i < 3; i++) {
      NewAccountEvent e = new NewAccountEvent();
      e.setAccountNumber(1111 * (i + 1));
      e.setAmount(0);
      e.setCustomerNumber(1 * (i + 1));
      e.setTimestamp(System.currentTimeMillis());
      queue.put(e);
    }
    ProducerThreadHandler producers = new ProducerThreadHandler(queue, brokers, topic, 3);
    producers.execute();
  }

  @Test
  public void dummy_데이터를_보내면_Application_가_에러없이_잘_동작합니다() throws Exception {
    Application.run();
  }
}
