package com.kakao.event.queue;

public interface EventQueue<Event>{
  void put(Event e);
  Event take();
}
