package com.znaji.events;

import com.znaji.core.RuleChain;
import com.znaji.core.RuleContext;
import org.springframework.context.ApplicationEvent;

public class ChainStartedEvent extends ApplicationEvent {

    private final RuleChain chain;
    private final RuleContext context;

    public ChainStartedEvent(Object source, RuleChain chain, RuleContext context) {
        super(source);
        this.chain = chain;
        this.context = context;
    }

    public RuleChain getChain() {
        return chain;
    }

    public RuleContext getContext() {
        return context;
    }
}
