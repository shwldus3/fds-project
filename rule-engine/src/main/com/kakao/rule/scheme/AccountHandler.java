package com.kakao.rule.scheme;

import com.kakao.event.*;

public class AccountHandler {
  private Accounts accounts;
  private DepositHistory depositHistory;
  private RemittanceHistory remittanceHistory;
  private WithdrawHistory withdrawHistory;

  public AccountHandler() {
    this.accounts = new Accounts();
    this.depositHistory = new DepositHistory();
    this.remittanceHistory = new RemittanceHistory();
    this.withdrawHistory = new WithdrawHistory();
  }

  public void apply(Event event) {
    if (event.getType().equals(NewAccountEvent.class.getSimpleName())) modifyNewAccountEvent(event);
    if (event.getType().equals(DepositEvent.class.getSimpleName())) modifyDepositEvent(event);
    if (event.getType().equals(RemittanceEvent.class.getSimpleName())) modifyRemittanceEvent(event);
    if (event.getType().equals(WithdrawEvent.class.getSimpleName())) modifyWithdrawEvent(event);
  }

  private void modifyNewAccountEvent(Event event) {
    this.accounts.createAccount(event);
  }

  private void modifyDepositEvent(Event event) {
    Account account = this.accounts.getAccount(event.getCustomerNumber());
    account.depositAmount(event.getAmount());

    this.depositHistory.put(event, account.getBalance());
  }

  private void modifyRemittanceEvent(Event event) {
    this.accounts.getAccount(event.getCustomerNumber()).depositAmount(event.getAmount());
//    this.remittanceHistory.put(event);
  }

  private void modifyWithdrawEvent(Event event) {
    Account account = this.accounts.getAccount(event.getCustomerNumber());
    account.withdrawAmount(event.getAmount());

    this.withdrawHistory.put(event, account.getBalance());
  }

  public Accounts getAcounts() {
    return this.accounts;
  }

  public DepositHistory getDepositHistory() {
    return depositHistory;
  }

  public RemittanceHistory getRemittanceHistory() {
    return remittanceHistory;
  }

  public WithdrawHistory getWithdrawHistory() {
    return withdrawHistory;
  }
}
