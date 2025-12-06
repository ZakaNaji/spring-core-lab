package com.znaji.shell;

import com.znaji.core.Rule;
import com.znaji.core.RuleContext;
import com.znaji.core.RuleEngine;
import com.znaji.core.RuleResult;
import com.znaji.plugins.PluginLoaderService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CliRunner{

    private final RuleEngine engine;
    private final ApplicationContext ctx;
    private final PluginLoaderService pluginLoaderService;

    public CliRunner(RuleEngine engine, ApplicationContext ctx, PluginLoaderService pluginLoaderService) {
        this.engine = engine;
        this.ctx = ctx;
        this.pluginLoaderService = pluginLoaderService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Rules Engine CLI ===");
        System.out.println("Type 'help' for commands.\n");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            }

            switch (line) {
                case "help" -> printHelp();
                case "rules" -> listRules();
                case "plugins" -> listPlugins();
                case "reload" -> reloadPlugins();
                default -> {
                    if (line.startsWith("run ")) {
                        runRule(line.substring(4), scanner);
                    } else {
                        System.out.println("Unknown command: " + line);
                    }
                }
            }
        }
    }

    private void runRule(String args, Scanner scanner) {
        String[] parts = args.split(" ", 1);
        if (parts.length < 1) {
            System.out.println("Usage: run <rule-name>");
            return;
        }

        String ruleName = parts[0];

        // 1) find rule by logical name (Rule.getName())
        Rule rule = ctx.getBeansOfType(Rule.class)
                .values()
                .stream()
                .filter(r -> r.getName().equalsIgnoreCase(ruleName))
                .findFirst()
                .orElse(null);

        if (rule == null) {
            System.out.println("No rule found with name: " + ruleName);
            System.out.println("Use 'rules' to list available rules.");
            return;
        }

        // 2) build context and put "text"
        RuleContext context = new RuleContext();
        for (String key : rule.requiredInputs()) {
            System.out.print(key + ": ");
            String value = scanner.nextLine().trim();
            context.put(key, value);
        }

        // 3) execute rule
        RuleResult result = rule.execute(context);

        // 4) print result (adapt to your RuleResult API)
        System.out.println("Result: " + result);
    }

    private void listRules() {
        System.out.println("Available rules:");
        ctx.getBeansOfType(Rule.class)
                .values()
                .forEach(rule -> System.out.println("- " + rule.getName()
                        + " (" + rule.getClass().getSimpleName() + ")"));
    }

    private void listPlugins() {
        pluginLoaderService.discover().forEach(meta ->
                System.out.println("- " + meta.getJarName())
        );
    }
    private void reloadPlugins() {
        pluginLoaderService.loadAllPlugins();
        System.out.println("Plugins reloaded.");
    }


    private void printHelp() {
        System.out.println("""
            Commands:
              rules               - list available rules
              run <rule> <text>   - execute rule on input text
              plugins             - list plugin JARs
              reload              - reload all plugins
              exit                - quit
            """);
    }
}
