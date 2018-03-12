package com.kakao.rule.scheme;

import com.kakao.event.Event;

public class History {
  private long timestamp;//히스토리 타임스탬프
  private long amount;//히스토리 금액
  private long balance;//히스토리 잔액

  public History(Event event) {
    this.timestamp = event.getTimestamp();
    this.amount = event.getAmount();
  }

  public void setBalance(long balance) {
    this.balance = balance;
  }

  public long getAmount() {
    return this.amount;
  }

  public long getBalance() {
    return this.balance;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

}
