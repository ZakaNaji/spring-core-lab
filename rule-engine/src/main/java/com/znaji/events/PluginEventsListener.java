package com.znaji.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PluginEventsListener {

    @EventListener
    public void onPluginLoaded(PluginLoadedEvent event) {
        System.out.println("[Event] PluginLoaded: " + event.getMetadata().getJarName());
    }
}