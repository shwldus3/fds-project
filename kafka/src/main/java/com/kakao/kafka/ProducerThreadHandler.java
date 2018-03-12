package com.kakao.kafka;

import com.kakao.event.Event;
import com.kakao.event.queue.EventQueue;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerThreadHandler {

  private final int numberOfProducers;
  private final Properties props;
  private final String topic;
  private List<ProducerThread> producerThreads;
  private ExecutorService executor;
  private EventQueue<Event> queue;

  public ProducerThreadHandler(EventQueue<Event> queue, String brokers, String topic, int numberOfProducers) {
    this.queue = queue;
    this.props = createProperties(brokers);
    this.topic = topic;
    this.numberOfProducers = numberOfProducers;
  }

  private Properties createProperties(String brokers) {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    return props;
  }

  public void execute() {
    executor = Executors.newFixedThreadPool(numberOfProducers);
    producerThreads = new ArrayList<>(numberOfProducers);

    for (int i = 0; i < numberOfProducers; i++) {
      ProducerThread pThread = new ProducerThread(this.queue, this.props, this.topic);
      producerThreads.add(pThread);
      executor.submit(pThread);
    }
  }

  public void shutdown() {
    if (producerThreads != null) {
      for (ProducerThread pThread : producerThreads) {
        pThread.shutdown();
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
