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

    @EventListener
    public void onChainStarted(ChainStartedEvent event) {
        System.out.println(
                "[Event] ChainStarted: " + event.getChain().getName() +
                        " | Rules: " + String.join(" -> ", event.getChain().getRuleNames())
        );
    }

    @EventListener
    public void onChainCompleted(ChainCompletedEvent event) {
        System.out.println(
                "[Event] ChainCompleted: " + event.getChain().getName()
        );
    }

    @EventListener
    public void onChainFailed(ChainFailedEvent event) {
        System.out.println(
                "[Event] ChainFailed: " + event.getChain().getName() +
                        " | error=" + event.getResult().getErrorMessage()
        );
    }

}
