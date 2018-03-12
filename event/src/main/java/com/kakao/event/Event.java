package com.kakao.event;

public interface Event {
  long getTimestamp();

  long getCustomerNumber();

  long getAmount();

  long getAccountNumber();

  String getType();
}
