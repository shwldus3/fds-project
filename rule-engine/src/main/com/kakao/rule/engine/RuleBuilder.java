package com.kakao.rule.engine;

public interface RuleBuilder {
  RuleBuilder addRule(Operator op);

  boolean build();
}
