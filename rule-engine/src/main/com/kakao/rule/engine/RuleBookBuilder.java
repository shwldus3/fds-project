package com.kakao.rule.engine;

import com.kakao.event.Event;
import com.kakao.rule.scheme.AccountHandler;


public class RuleBookBuilder<T extends RuleOperator> implements RuleBuilder{
  private RuleOperator rule;
  private boolean ruleResult = false;

  public static RuleBookBuilder create(AccountHandler accountHandler, Event e) {
    RuleBookBuilder ruleBookBuilder = new RuleBookBuilder();
    ruleBookBuilder.rule = new RuleOperator(accountHandler, e);
    return ruleBookBuilder;
  }

  public RuleBookBuilder addRule(Operator op) {
    ruleResult = op.exec(rule);
    return this;
  }

  public boolean build() {
    System.out.println("[RuleBookBuilder] Result of Rule : " + ruleResult);
    return ruleResult;
  }
}
