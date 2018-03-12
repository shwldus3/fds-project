package com.kakao.rule.book;

import com.kakao.event.Event;
import com.kakao.rule.engine.RuleBookBuilder;
import com.kakao.rule.engine.RuleBuilder;
import com.kakao.rule.scheme.AccountHandler;

public class FishingCatchRule implements RuleBook {
  private RuleBuilder ruleBuilder;

  public FishingCatchRule(AccountHandler accountHandler, Event e) {
    this.ruleBuilder = RuleBookBuilder.create(accountHandler, e);
  }

  @Override
  public FishingCatchRule define() {
    this.ruleBuilder
      .addRule(rule -> rule.isAccountWithIn(604800000))//7 days
      .addRule(rule -> rule.isWithdrawJustAfterDeposit(900000, 1000000, 10000));
    return this;
  }

  @Override
  public boolean run() {
    return this.ruleBuilder.build();
  }
}
