package com.znaji.plugins;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    private static final String PLUGIN_DIR = "plugins";

    public List<PluginMetadata> discoverPlugins() {
        List<PluginMetadata> plugins = new ArrayList<>();

        Path pluginsPath = null;
        try {
            pluginsPath = getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (!Files.exists(pluginsPath) || !Files.isDirectory(pluginsPath)) {
            return plugins;
        }

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(pluginsPath, "*.jar")) {
            for (Path jar : paths) {
                plugins.add(new PluginMetadata(jar));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return plugins;
    }

    private Path getPath() throws URISyntaxException {
        Path moduleRoot = Path.of(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI())
                .getParent();
        Path modulePath = moduleRoot.getParent();
        Path pluginsPath = modulePath.resolve(PLUGIN_DIR);
        return pluginsPath;
    }
}
