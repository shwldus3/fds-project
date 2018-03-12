package com.kakao.rule.scheme;

import com.kakao.event.Event;

import java.util.Hashtable;
import java.util.Map;

public class Accounts {
  private Map<Long, Account> accounts;

  public Accounts() {
    this.accounts = new Hashtable<>();
  }

  public void createAccount(Event event) {
    System.out.println("[AccountHandler] CreateAccount : " + new Account(event));
    this.accounts.put(event.getCustomerNumber(), new Account(event));
  }

  public Map<Long, Account> getAccounts() {
    return this.accounts;
  }

  public Account getAccount(long customerNumber) {
    return this.accounts.get(customerNumber);
  }

}
