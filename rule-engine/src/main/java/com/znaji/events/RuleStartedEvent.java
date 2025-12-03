package com.znaji.events;


import com.znaji.core.RuleContext;
import org.springframework.context.ApplicationEvent;

public class RuleStartedEvent extends ApplicationEvent {

    private final String ruleName;
    private final RuleContext context;

    public RuleStartedEvent(Object source, String ruleName, RuleContext context) {
        super(source);
        this.ruleName = ruleName;
        this.context = context;
    }

    public String getRuleName() {
        return ruleName;
    }

    public RuleContext getContext() {
        return context;
    }
}
