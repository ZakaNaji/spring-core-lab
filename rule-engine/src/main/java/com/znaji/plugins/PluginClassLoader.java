package com.znaji.plugins;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class PluginClassLoader extends URLClassLoader {


    public PluginClassLoader(Path jarPath) throws MalformedURLException {
        super(new URL[]{jarPath.toUri().toURL()},
                PluginClassLoader.class.getClassLoader());
    }
}
