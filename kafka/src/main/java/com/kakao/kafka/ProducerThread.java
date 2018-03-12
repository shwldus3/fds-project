package com.kakao.kafka;

import com.kakao.event.Event;
import com.kakao.event.EventMapper;
import com.kakao.event.queue.EventQueue;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class ProducerThread implements Runnable {
  private final KafkaProducer<String, String> producer;
  private final EventQueue<Event> queue;
  private final String topic;

  public ProducerThread(EventQueue<Event> queue, Properties props, String topic) {
    this.producer = new KafkaProducer<>(props);
    this.queue = queue;
    this.topic = topic;
  }

  public void run() {
    try {
      while (true) {
        Event event = this.queue.take();
        if (event == null) continue;
        String dataMsg = EventMapper.serializer(event);
        this.producer.send(new ProducerRecord<>(this.topic, dataMsg), new Callback() {
          public void onCompletion(RecordMetadata metadata, Exception e) {
            if (e != null) {
              e.printStackTrace();
            }
            System.out.println("Sent message:" + dataMsg + ", topic: " + metadata.topic() + ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
          }
        });
      }
    } catch (Exception ignored) {
    } finally {
      producer.close();
    }
  }

  public void shutdown() {
    producer.close();
  }
}
