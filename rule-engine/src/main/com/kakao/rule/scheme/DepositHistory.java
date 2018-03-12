package com.kakao.rule.scheme;

import com.kakao.event.Event;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

public class DepositHistory {
  private final Map<Long, LinkedList<History>> depositHistory;

  public DepositHistory() {
    this.depositHistory = new Hashtable<>();
  }

  public void put(Event event, long balance) {
    long accountNumber = event.getAccountNumber();
    LinkedList<History> history = this.depositHistory.get(accountNumber);

    History h = new History(event);
    h.setBalance(balance);

    if (history == null) {
      this.depositHistory.put(accountNumber, new LinkedList<>(Arrays.asList(h)));
    } else {
      if (history.getLast().getTimestamp() < event.getTimestamp())
        history.addLast(h);
//      this.depositHistory.replace(accountNumber, history);
    }
  }

  public LinkedList<History> getHistory(long accountNumber) {
    return this.depositHistory.get(accountNumber);
  }

  public History getLast(long accountNumber) {
    return this.getHistory(accountNumber).getLast();
  }
}
