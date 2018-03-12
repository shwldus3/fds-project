package com.kakao.server.detect;

import com.kakao.event.Event;
import com.kakao.event.queue.EventQueue;
import com.kakao.rule.scheme.AccountHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DetectHandler {
  private final int numberOfDetectors;
  private List<Detector> detectors;
  private ExecutorService executor;

  public DetectHandler(int numberOfDetectors) {
    this.numberOfDetectors = numberOfDetectors;
  }

  public void execute(EventQueue<Event> queue, EventQueue<Event> sendQueue, AccountHandler accountHandler) {
    executor = Executors.newFixedThreadPool(numberOfDetectors);
    detectors = new ArrayList<>(numberOfDetectors);

    for (int i = 0; i < numberOfDetectors; i++) {
      Detector detector = new Detector(queue, sendQueue, accountHandler);
      detectors.add(detector);
      executor.submit(detector);
    }
  }

  public void shutdown() {
    if (executor != null) {
      executor.shutdown();
      try {
        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
      } catch (InterruptedException ignored) {
      }
    }
  }

}
