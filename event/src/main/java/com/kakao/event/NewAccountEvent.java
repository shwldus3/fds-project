package com.kakao.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "amount" })
public class NewAccountEvent extends AbstractEvent {
  private long timestamp;
  private long customerNumber;
  private long accountNumber;
  private String type;
  private long amount;

  @JsonProperty("timestamp")
  public long getTimestamp() { return timestamp; }

  @JsonProperty("customerNumber")
  public long getCustomerNumber() { return customerNumber; }

  @JsonProperty("accountNumber")
  public long getAccountNumber() { return accountNumber; }

  @JsonProperty("type")
  public String getType() { return type; }

  @JsonIgnore
  public long getAmount() { return 0; }

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
    StringBuffer sb = new StringBuffer("NewAccountEvent {");
    sb.append("timestamp : ").append(this.timestamp);
    sb.append(", customerNumber : ").append(this.customerNumber);
    sb.append(", accountNumber : ").append(this.accountNumber);
    sb.append(", type : ").append(this.type);
    sb.append("}");

    return sb.toString();
  }
}
