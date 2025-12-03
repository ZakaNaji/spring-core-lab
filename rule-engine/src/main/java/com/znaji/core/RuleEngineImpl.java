package com.znaji.core;

import org.springframework.stereotype.Component;

@Component
public class RuleEngineImpl implements RuleEngine {

    private final RuleRegistry registry;

    public RuleEngineImpl(RuleRegistry registry) {
        this.registry = registry;
    }

    @Override
    public RuleResult execute(String ruleName, RuleContext context) {
        return registry.findByName(ruleName)
                .map(rule -> rule.execute(context))
                .orElseGet(() -> RuleResult.fail("Rule not found: " + ruleName));
    }
}
