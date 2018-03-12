package com.kakao.rule.scheme;

import com.kakao.event.Event;

public class Account {
  private long timestamp;//계좌 개설 타임스탬프
  private long accountNumber;//계좌번호
  private long balance;//잔액

  public Account(Event event) {
    this.timestamp = event.getTimestamp();
    this.accountNumber = event.getAccountNumber();
    this.balance = event.getAmount();
  }

  public long getAccountNumber() {
    return this.accountNumber;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public long getBalance() {
    return this.balance;
  }

  public void depositAmount(long amount) {
    this.balance += amount;
  }

  public void withdrawAmount(long amount) {
    if (this.balance < amount)
      throw new IllegalArgumentException("There is not enough balance");
    this.balance -= amount;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("Account {");
    sb.append("timestamp : ").append(timestamp);
    sb.append(", accountNumber : ").append(accountNumber);
    sb.append(", balance : ").append(balance);
    sb.append("}");

    return sb.toString();
  }
}

