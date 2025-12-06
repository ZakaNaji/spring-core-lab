# 🔧 Spring Core — Plugin-Based Rules Engine
*A learning project to explore advanced Spring Core internals (IoC container manipulation, dynamic classloading, bean registration, and application events).*

---

## 📌 Overview
This mini-project demonstrates how to treat the Spring IoC container as a **dynamic, extensible runtime system**, rather than a static configuration mechanism.

Users can drop external `.jar` files into a `plugins/` directory. The application:

1. Discovers plugins dynamically  
2. Loads their classes using a **custom ClassLoader**  
3. Scans for Spring-managed components that implement the `Rule` interface  
4. Registers them into the ApplicationContext at runtime  
5. Allows executing rules interactively through a simple CLI  
6. Supports hot reload of plugins  

The project intentionally does **not** use Spring Boot.  
It focuses on **Spring Framework internals**.

---

## 🎯 Goals of the Project
This project was built as part of a personal effort to master **Spring Core fundamentals**.

It covers:
- `BeanDefinitionRegistryPostProcessor` and its limitations
- Switching to a safer late-binding plugin loading approach
- Manual bean definition creation
- Dynamic bean registration
- Custom class loaders
- Application events & event listeners
- Refreshing the context safely
- Designing extensible module architectures
- Building a minimal CLI to interact with the Spring container

By completing this mini-project, the developer gains practical experience with **how Spring manages beans, dependencies, scanning, and the lifecycle of the ApplicationContext**.

---

## 🧩 Project Structure
```
spring-core-lab/
  ├── rule-engine/          // main module containing the dynamic loader + CLI
  ├── hello-plugin/         // example plugin jar containing a Rule implementation
  ├── plugins/              // directory scanned at runtime for jars
  └── ...
```

---

## 📝 The Rule Model
A plugin must provide classes implementing the `Rule` interface:

```
public interface Rule {
    String getName();
    Set<String> requiredInputs();
    RuleResult execute(RuleContext context);
}
```

Example rule:
```
public class ToUpperRule implements Rule {
    public String getName() { return "to-upper"; }
    public Set<String> requiredInputs() { return Set.of("text"); }

    public RuleResult execute(RuleContext ctx) {
        String text = ctx.getString("text");
        return RuleResult.ok(text.toUpperCase());
    }
}
```

Rules are discovered dynamically when a plugin JAR is loaded.

---

## 🚀 CLI Usage
Start the application:
```
> java -jar rule-engine.jar
```

Type `help`:
```
Commands:
  rules               - list all rule names
  run <rule>          - execute a rule (prompts for required inputs)
  plugins             - list available plugin jars
  reload              - reload plugins from disk
  exit                - quit
```

### ▶ Listing rules
```
> rules
- to-upper
- trim
- validate-email
```

### ▶ Running a rule
```
> run validate-email
email: ZAKARIA@Example.com
SUCCESS: Valid email: zakaria@example.com
```

### ▶ Reloading plugins
```
> reload
Plugins reloaded.
```

---

## 🔥 Features Demonstrated
| Concept | Where It Appears |
|--------|------------------|
| Custom ClassLoader | PluginClassLoader |
| Plugin Discovery | PluginManager |
| Dynamic Bean Registration | PluginLoaderService |
| Application Events | PluginLoadedEvent, listeners |
| Hot reload Strategy | loadAllPlugins() |
| CLI Interaction | CliRunner |
| Rule Execution Engine | RuleEngine |

---

## 📚 Lessons Learned
- Spring's IoC container **can be extended dynamically** — but must be done *after* context initialization.
- Application events fire early, so event timing matters.
- Custom class loaders allow safe plugin isolation.
- Rule contracts (`requiredInputs()`) allow generic CLI interaction.
- Clean command architecture keeps the system maintainable.

---

## 👤 Author
Created as part of a deep-dive into **Spring Core internals**.

---

## ✔ Next Steps
- Implement rule chains / pipelines  
- Add rule metadata validation  
- Add plugin versioning or unload support  
- Build a lightweight web UI on top of the engine  

---

### ⭐ This project is intentionally minimal — not production-grade — but extremely valuable for understanding the **real mechanics** of the Spring IoC container.
```
