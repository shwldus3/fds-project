package com.kakao.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemittanceEvent extends AbstractEvent {
  private long timestamp;
  private long customerNumber;
  private long accountNumber;
  private long beforeAccountBalance;
  private int receiveBankCode;
  private String receiver;
  private long amount;

  @JsonProperty("timestamp")
  public long getTimestamp() { return timestamp; }

  @JsonProperty("customerNumber")
  public long getCustomerNumber() { return customerNumber; }

  @JsonProperty("accountNumber")
  public long getAccountNumber() { return accountNumber; }

  @JsonProperty("beforeAccountBalance")
  public long getBeforeAccountBalance() { return beforeAccountBalance; }

  @JsonProperty("receiveBankCode")
  public int getReceiveBankCode() { return receiveBankCode; }

  @JsonProperty("receiver")
  public String getReceiver() { return receiver; }

  @JsonProperty("amount")
  public long getAmount() { return amount; }

  @Override
  @JsonIgnore
  public String getType() {
    return this.getClass().getSimpleName();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("RemittanceEvent {");
    sb.append("timestamp : ").append(timestamp);
    sb.append("customerNumber : ").append(customerNumber);
    sb.append("accountNumber : ").append(accountNumber);
    sb.append("beforeAccountBalance : ").append(beforeAccountBalance);
    sb.append("receiveBankCode : ").append(receiveBankCode);
    sb.append("receiver : ").append(receiver);
    sb.append("amount : ").append(amount);
    sb.append("}");

    return sb.toString();
  }
}
