package com.znaji.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginEventListener {

    @EventListener
    public void handleRuleStarter(RuleStartedEvent event) {
        System.out.println("[Event] RuleStartedEvent: " + event.getRuleName());
    }
    @EventListener
    public void handleCompleted(RuleCompletedEvent event) {
        System.out.println("[Event] RuleCompletedEvent: " + event.getRuleName());
    }

    @EventListener
    public void handleFailed(RuleFailedEvent event) {
        System.out.println("[Event] RuleFailedEvent: " + event.getRuleName()
                + " - error=" + event.getResult().getErrorMessage());
    }

}
