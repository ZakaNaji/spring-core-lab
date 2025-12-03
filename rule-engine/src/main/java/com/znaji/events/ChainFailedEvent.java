package com.znaji.events;

import com.znaji.core.RuleChain;
import com.znaji.core.RuleContext;
import com.znaji.core.RuleResult;
import org.springframework.context.ApplicationEvent;

public class ChainFailedEvent extends ApplicationEvent {

    private final RuleChain chain;
    private final RuleContext context;
    private final RuleResult result;

    public ChainFailedEvent(Object source, RuleChain chain,
                            RuleContext context, RuleResult result) {
        super(source);
        this.chain = chain;
        this.context = context;
        this.result = result;
    }

    public RuleChain getChain() {
        return chain;
    }

    public RuleContext getContext() {
        return context;
    }

    public RuleResult getResult() {
        return result;
    }
}
