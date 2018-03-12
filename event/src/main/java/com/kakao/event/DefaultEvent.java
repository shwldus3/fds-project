package com.kakao.event;

public class DefaultEvent implements Event{
  private long timestamp;

  public DefaultEvent(long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public long getTimestamp() {
    return this.timestamp;
  }

  @Override
  public long getCustomerNumber() {
    return 0;
  }

  @Override
  public long getAmount() {
    return 0;
  }

  @Override
  public long getAccountNumber() {
    return 0;
  }

  @Override
  public String getType() {
    return null;
  }


}
