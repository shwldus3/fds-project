package com.kakao.server.detect;

import com.kakao.event.Event;
import com.kakao.event.queue.EventQueue;
import com.kakao.rule.book.FishingCatchRule;
import com.kakao.rule.scheme.AccountHandler;


public class Detector implements Runnable {
  private final EventQueue<Event> queue;
  private final EventQueue<Event> sendQueue;
  private final AccountHandler accountHandler;

  public Detector(EventQueue<Event> queue, EventQueue<Event> sendQueue, AccountHandler accountHandler) {
    this.queue = queue;
    this.sendQueue = sendQueue;
    this.accountHandler = accountHandler;
  }

  public void run() {
    while (true) {
      try {
        Thread.sleep(1000);

        Event event = queue.take();
        if (event == null) continue;
        System.out.println("[Detector] Receive Event: " + event + ", by ThreadID: " + Thread.currentThread().getId());

        accountHandler.apply(event);

        FishingCatchRule ruleBook = new FishingCatchRule(accountHandler, event);
        boolean ruleResult = ruleBook.define().run();

        if (ruleResult) {
          this.sendQueue.put(event);
        }

      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

