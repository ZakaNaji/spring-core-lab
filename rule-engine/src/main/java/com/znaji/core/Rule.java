package com.znaji.core;

import java.util.Set;

public interface Rule {
    /**
     * A human-friendly name for this rule, used in DSL or single execution.
     */
    String getName();

    /**
     * Execute with the provided context.
     */
    RuleResult execute(RuleContext context);

    Set<String> requiredInputs();
}
