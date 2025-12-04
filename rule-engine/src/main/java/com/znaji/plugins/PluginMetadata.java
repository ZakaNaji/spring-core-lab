package com.znaji.plugins;

import java.nio.file.Path;

public class PluginMetadata {

    private final Path jarPath;

    public PluginMetadata(Path jarPath) {
        this.jarPath = jarPath;
    }

    public Path getJarPath() {
        return jarPath;
    }

    public String getJarName() {
        return jarPath.getFileName().toString();
    }
}