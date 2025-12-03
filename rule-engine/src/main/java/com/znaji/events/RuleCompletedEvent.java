package com.znaji.events;

import com.znaji.core.RuleContext;
import com.znaji.core.RuleResult;
import org.springframework.context.ApplicationEvent;

public class RuleCompletedEvent extends ApplicationEvent {

    private final String ruleName;
    private final RuleContext context;
    private final RuleResult result;

    public RuleCompletedEvent(Object source,
                              String ruleName,
                              RuleContext context,
                              RuleResult result) {
        super(source);
        this.ruleName = ruleName;
        this.context = context;
        this.result = result;
    }

    public String getRuleName() {
        return ruleName;
    }

    public RuleContext getContext() {
        return context;
    }

    public RuleResult getResult() {
        return result;
    }
}