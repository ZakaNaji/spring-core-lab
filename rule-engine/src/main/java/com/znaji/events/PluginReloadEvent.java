package com.znaji.events;

import com.znaji.plugins.PluginMetadata;
import com.znaji.plugins.PluginWatcher;
import org.springframework.context.ApplicationEvent;

public class PluginReloadEvent extends ApplicationEvent {

    private final PluginMetadata metadata;

    public PluginReloadEvent(Object source, PluginMetadata metadata) {
        super(source);
        this.metadata = metadata;
    }

    public PluginMetadata getMetadata() {
        return metadata;
    }
}
