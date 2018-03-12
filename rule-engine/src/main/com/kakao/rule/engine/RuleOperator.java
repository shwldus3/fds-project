package com.kakao.rule.engine;

import com.kakao.event.Event;
import com.kakao.rule.scheme.Account;
import com.kakao.rule.scheme.AccountHandler;
import com.kakao.rule.scheme.History;

import java.util.LinkedList;


public class RuleOperator {
  private final Account account;
  private final LinkedList<History> depositHistory;
  private final LinkedList<History> withdrawHistory;
  private final LinkedList<History> remittanceHistory;

  public RuleOperator(AccountHandler accountHandler, Event e) {
    this.account = accountHandler.getAcounts().getAccount(e.getCustomerNumber());
    this.depositHistory = accountHandler.getDepositHistory().getHistory(e.getAccountNumber());
    this.withdrawHistory = accountHandler.getWithdrawHistory().getHistory(e.getAccountNumber());
    this.remittanceHistory = accountHandler.getRemittanceHistory().getHistory(e.getAccountNumber());
  }

  public boolean isAccountWithIn(long num) {
    if (this.account == null) return false;

    boolean result = this.account.getTimestamp() > System.currentTimeMillis() - num;
    System.out.println("[RuleOperator] Is created account with in " + num + " >>> " + result);
    return result;
  }

  public boolean isWithdrawJustAfterDeposit(long startAmount, long endAmount, long balance) {
    if (this.depositHistory == null) return false;

    long[] timestamps = this.depositHistory.stream()
      .filter(history -> startAmount < history.getBalance() && endAmount > history.getBalance())
      .mapToLong(history -> history.getTimestamp()).toArray();

    boolean result = false;
    for(long t: timestamps) {
      result = this.isWithdrawBalanceBetween(t, t + 7200000, balance);
      if (result) break;
    }
    System.out.println("[RuleOperator] Is it showing strange sign ? " + result);
    return result;
  }

  public boolean isWithdrawBalanceBetween(long startTimestamp, long endTimestamp, long balance) {
    if (this.withdrawHistory == null) return false;

    Object[] list = this.withdrawHistory.stream()
      .filter(history ->
        startTimestamp < history.getTimestamp()
          && endTimestamp > history.getTimestamp()
          && history.getBalance() <= balance)
      .toArray();

    return list.length > 0;
  }
}
