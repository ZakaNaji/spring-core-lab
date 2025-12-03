package com.znaji.core;

public interface RuleEngine {

    RuleResult execute(String ruleName, RuleContext context);
}