package com.kakao.event.queue;

import com.kakao.event.Event;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class TimeBoundedQueue implements EventQueue<Event> {
  private final PriorityBlockingQueue<Event> queue = new PriorityBlockingQueue<>(100, Comparator.comparingLong(Event::getTimestamp));
  private long timeBoundMillis;
  private long timestamp;

  public TimeBoundedQueue(long timeBoundMillis) {
    this.timeBoundMillis = timeBoundMillis;
    this.timestamp = System.currentTimeMillis();
  }

  public TimeBoundedQueue(long timeBoundMillis, TimeProvider timeProvider) {
    this.timeBoundMillis = timeBoundMillis;
    this.timestamp = timeProvider.getTimestamp();
  }

  public void put(Event e) {
    queue.put(e);
    removeExpiredElement();
  }

  public Event take() {
    try {
      removeExpiredElement();
      return queue.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void removeExpiredElement() {
    Event e = queue.peek();
    long cutOffTimestamp = this.timestamp - timeBoundMillis;

    if (e == null) return;

    while(e.getTimestamp() < cutOffTimestamp) {
      queue.poll();
      e = queue.peek();
      if (e == null) break;
    }
  }
}
