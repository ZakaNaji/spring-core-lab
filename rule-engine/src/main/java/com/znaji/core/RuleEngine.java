package com.znaji.core;

public interface RuleEngine {

    RuleResult execute(String ruleName, RuleContext context);

    RuleResult executeChain(RuleChain chain, RuleContext ctx);
}