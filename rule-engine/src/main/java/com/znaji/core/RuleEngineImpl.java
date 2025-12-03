package com.znaji.core;

import com.znaji.events.RuleCompletedEvent;
import com.znaji.events.RuleFailedEvent;
import com.znaji.events.RuleStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RuleEngineImpl implements RuleEngine {

    private final RuleRegistry registry;
    private final ApplicationEventPublisher publisher;

    public RuleEngineImpl(RuleRegistry registry, ApplicationEventPublisher publisher) {
        this.registry = registry;
        this.publisher = publisher;
    }

    @Override
    public RuleResult execute(String ruleName, RuleContext context) {
        var optionalRule = registry.findByName(ruleName);

        if (optionalRule.isEmpty()) {
            RuleResult result = RuleResult.fail("Rule not found: " + ruleName);
            publisher.publishEvent(
                    new RuleFailedEvent(this, ruleName, context, result)
            );
            return result;
        }

        var rule = optionalRule.get();

        publisher.publishEvent(new RuleStartedEvent(this, ruleName, context));

        RuleResult result;
        try {
            result = rule.execute(context);
        } catch (Exception ex) {
            result = RuleResult.fail(
                    "Exception in rule '" + ruleName + "': " + ex.getMessage()
            );
        }

        if (result.isSuccess()) {
            publisher.publishEvent(
                    new RuleCompletedEvent(this, ruleName, context, result)
            );
        } else {
            publisher.publishEvent(
                    new RuleFailedEvent(this, ruleName, context, result)
            );
        }

        return result;
    }
}
