package com.znaji;

import com.znaji.core.*;
import com.znaji.core.config.CoreConfig;
import com.znaji.dsl.DslParser;
import com.znaji.plugins.PluginManager;
import com.znaji.shell.CliRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext()) {
            ctx.register(CoreConfig.class);
            ctx.refresh();

            CliRunner cli = ctx.getBean(CliRunner.class);
            cli.start();
        }
    }
}
