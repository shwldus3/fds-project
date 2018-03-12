package com.kakao.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawEvent extends AbstractEvent {
  private long timestamp;
  private long customerNumber;
  private long accountNumber;
  private long amount;
  private String type;

  @JsonProperty("timestamp")
  public long getTimestamp() { return timestamp; }

  @JsonProperty("customerNumber")
  public long getCustomerNumber() { return customerNumber; }

  @JsonProperty("accountNumber")
  public long getAccountNumber() { return accountNumber; }

  @JsonProperty("amount")
  public long getAmount() { return amount; }

  @JsonProperty("type")
  public String getType() { return type; }

  @JsonIgnore
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  @JsonIgnore
  public void setCustomerNumber(long customerNumber) {
    this.customerNumber = customerNumber;
  }

  @JsonIgnore
  public void setAmount(long amount) {
    this.amount = amount;
  }

  @JsonIgnore
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  @JsonIgnore
  public void setType() {
    this.type = this.getClass().getSimpleName();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("WithdrawEvent {");
    sb.append("timestamp : ").append(timestamp);
    sb.append(", customerNumber : ").append(customerNumber);
    sb.append(", accountNumber : ").append(accountNumber);
    sb.append(", amount : ").append(amount);
    sb.append(", type : ").append(type);
    sb.append("}");

    return sb.toString();
  }
}
