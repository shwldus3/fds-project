package com.kakao.kafka;

import com.kakao.event.Event;
import com.kakao.event.queue.EventQueue;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConsumerThreadHandler {

  private final int numberOfConsumers;
  private final Properties props;
  private final String topic;
  private List<ConsumerThread> consumerThreads;
  private ExecutorService executor;
  private EventQueue<Event> queue;

  public ConsumerThreadHandler(EventQueue<Event> queue, String brokers, String groupId, String topic, int numberOfConsumers) {
    this.queue = queue;
    this.props = createProperties(brokers, groupId);
    this.topic = topic;
    this.numberOfConsumers = numberOfConsumers;
  }

  private static Properties createProperties(String brokers, String groupId) {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return props;
  }

  public void execute() {
    executor = Executors.newFixedThreadPool(numberOfConsumers);
    consumerThreads = new ArrayList<>(numberOfConsumers);

    for (int i = 0; i < numberOfConsumers; i++) {
      ConsumerThread cThread = new ConsumerThread(this.queue, this.props, this.topic);
      consumerThreads.add(cThread);
      executor.submit(cThread);
    }
  }

  public void shutdown() {
    if (consumerThreads != null) {
      for (ConsumerThread cThread : consumerThreads) {
        cThread.shutdown();
      }
    }
    if (executor != null) {
      executor.shutdown();
      try {
        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
      } catch (InterruptedException ignored) {
      }
    }
  }

}
