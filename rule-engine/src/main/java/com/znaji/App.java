package com.znaji;

import com.znaji.core.*;
import com.znaji.core.config.CoreConfig;
import com.znaji.dsl.DslParser;
import com.znaji.plugins.PluginManager;
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

            RuleContext context = new RuleContext();
            context.put("text", "  Test  ");
            Map<String, Rule> rules = ctx.getBeansOfType(Rule.class);
            for (Rule rule : rules.values()) {
                System.out.println(rule.execute(context).getOutput());
            }
        }
    }
}
