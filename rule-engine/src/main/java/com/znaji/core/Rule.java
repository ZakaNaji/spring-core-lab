package com.znaji.core;

public interface Rule {
    /**
     * A human-friendly name for this rule, used in DSL or single execution.
     */
    String getName();

    /**
     * Execute with the provided context.
     */
    RuleResult execute(RuleContext context);
}
