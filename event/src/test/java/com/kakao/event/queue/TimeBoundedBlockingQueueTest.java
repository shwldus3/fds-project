package com.kakao.event.queue;

import com.kakao.event.Event;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TimeBoundedBlockingQueueTest {

  @Test
  public void timeBoundMillis_이내의_동일한_event_를_반환한다() throws Exception {
    TimeProvider timeProvider = new TimeProvider();
    TimeBoundedQueue queue = new TimeBoundedQueue(100, timeProvider);
    Event expected = mock(Event.class);
    when(expected.getTimestamp()).thenReturn(timeProvider.getTimestamp());

    queue.put(expected);

    Event actual = queue.take();

    assertEquals(expected, actual);
 }

  @Test
  public void timestamp가_100ms_지난_element_는_정리되어_다음_element_으로_넘어가서_반환된다() throws Exception {
    TimeProvider timeProvider = new TimeProvider();
    TimeBoundedQueue queue = new TimeBoundedQueue(100, timeProvider);
    Event expiredEvent = mock(Event.class);
    when(expiredEvent.getTimestamp()).thenReturn(timeProvider.getTimestamp() - 1000);
    Event expected = mock(Event.class);
    when(expected.getTimestamp()).thenReturn(timeProvider.getTimestamp());

    queue.put(expiredEvent);
    queue.put(expected);

    Event actual = queue.take();
    assertEquals(expected, actual);
  }
}
