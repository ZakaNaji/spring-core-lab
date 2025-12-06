package com.znaji.plugins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PluginMetadata {

    private final Path jarPath;
    private final long lastModified;

    public PluginMetadata(Path jarPath) {
        this.jarPath = jarPath;
        try {
            this.lastModified = Files.getLastModifiedTime(jarPath).toMillis();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read lastModified for " + jarPath, e);
        }
    }

    public Path getJarPath() {
        return jarPath;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getId() {
        // simple identity: jar file name
        return jarPath.getFileName().toString();
    }

    public String getJarName() {
        return jarPath.getFileName().toString();
    }
}