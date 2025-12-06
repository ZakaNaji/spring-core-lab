package com.znaji.events;

import com.znaji.plugins.PluginMetadata;
import org.springframework.context.ApplicationEvent;

public class PluginLoadedEvent extends ApplicationEvent {

    private final PluginMetadata metadata;

    public PluginLoadedEvent(Object source, PluginMetadata metadata) {
        super(source);
        this.metadata = metadata;
    }

    public PluginMetadata getMetadata() {
        return metadata;
    }
}