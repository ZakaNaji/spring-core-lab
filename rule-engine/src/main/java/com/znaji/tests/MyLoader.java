package com.znaji.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyLoader extends ClassLoader {

    private final Path path;

    public MyLoader(Path path, ClassLoader parent) {
        super(parent);
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            Path classFile = path.resolve(name.replace(".", "/") + ".class");
            byte[] classFileBytes = Files.readAllBytes(classFile);
            return defineClass(name, classFileBytes, 0, classFileBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}
