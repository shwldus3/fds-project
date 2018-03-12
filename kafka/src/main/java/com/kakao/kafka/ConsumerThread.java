package com.kakao.kafka;

import com.kakao.event.Event;
import com.kakao.event.EventMapper;
import com.kakao.event.queue.EventQueue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerThread implements Runnable {
  private final KafkaConsumer<String, String> consumer;
  private EventQueue<Event> queue;

  public ConsumerThread(EventQueue<Event> queue, Properties props, String topic) {
    this.consumer = new KafkaConsumer<>(props);
    this.queue = queue;
    this.consumer.subscribe(Arrays.asList(topic));
  }

  public void run() {
    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
        for (ConsumerRecord<String, String> record : records) {
          System.out.println("[Consumer] Receive message: " + record.value() + ", Partition: "
                                 + record.partition() + ", Offset: " + record.offset() + ", by ThreadID: "
                                 + Thread.currentThread().getId());

          Event e = EventMapper.parse(record.value());
          System.out.println("[Consumer] Deserialized Event : " + e);
          queue.put(e);
        }

      }
    } catch (Exception ignored) {
    } finally {
      consumer.close();
    }
  }

  public void shutdown() {
    consumer.wakeup();
  }
}
