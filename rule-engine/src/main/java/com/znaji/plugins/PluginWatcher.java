package com.znaji.plugins;

import com.znaji.events.PluginReloadEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PluginWatcher {

    private final PluginManager pluginManager;
    private final ApplicationEventPublisher publisher;

    // id -> lastModified
    private final Map<String, Long> pluginState = new HashMap<>();


    public PluginWatcher(PluginManager pluginManager, ApplicationEventPublisher publisher) {
        this.pluginManager = pluginManager;
        this.publisher = publisher;

        // Pre-populate state so first scan does NOT trigger reload
        for (PluginMetadata meta : pluginManager.discoverPlugins()) {
            pluginState.put(meta.getId(), meta.getLastModified());
        }

    }

    @Scheduled(fixedDelay = 3000)
    public void scan() {
        List<PluginMetadata> plugins = pluginManager.discoverPlugins();

        for (PluginMetadata meta : plugins) {
            String id = meta.getId();
            long lastModified = meta.getLastModified();

            // new plugin
            if (!pluginState.containsKey(id)) {
                pluginState.put(id, lastModified);
                publisher.publishEvent(new PluginReloadEvent(this, meta));
                continue;
            }

            // modified plugin
            if (!Objects.equals(pluginState.get(id), lastModified)) {
                pluginState.put(id, lastModified);
                publisher.publishEvent(new PluginReloadEvent(this, meta));
            }
        }
    }
}
