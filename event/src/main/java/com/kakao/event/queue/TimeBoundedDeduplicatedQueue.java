package com.kakao.event.queue;

import com.kakao.event.Event;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

public class TimeBoundedDeduplicatedQueue implements EventQueue<Event> {
  private final PriorityBlockingQueue<Event> queue = new PriorityBlockingQueue<>(100, Comparator.comparingLong(Event::getTimestamp));
  private final Map<Long, Long> eventMap;
  private long timeBoundMillis;
  private long timestamp;

  public TimeBoundedDeduplicatedQueue(long timeBoundMillis) {
    this.eventMap = new Hashtable<>();
    this.timeBoundMillis = timeBoundMillis;
    this.timestamp = System.currentTimeMillis();
  }

  public TimeBoundedDeduplicatedQueue(long timeBoundMillis, TimeProvider timeProvider) {
    this.eventMap = new Hashtable<>();
    this.timeBoundMillis = timeBoundMillis;
    this.timestamp = timeProvider.getTimestamp();
  }

  public void put(Event e) {
    if (eventMap.get(e.getCustomerNumber()) == null) {
      queue.put(e);
      eventMap.put(e.getCustomerNumber(), e.getAccountNumber());
    }
    removeExpiredElement();
  }

  public Event take() {
    try {
      removeExpiredElement();
      Event e = queue.take();
      eventMap.remove(e.getCustomerNumber());
      return e;
    } catch (InterruptedException ignored) {
      ignored.printStackTrace();
      return null;
    }
  }

  private void removeExpiredElement() {
    Event e = queue.peek();
    long cutOffTimestamp = this.timestamp - timeBoundMillis;

    if (e == null) return;

    while(e.getTimestamp() < cutOffTimestamp) {
      queue.poll();
      eventMap.remove(e.getCustomerNumber());

      e = queue.peek();
      if (e == null) break;
    }
  }
}
