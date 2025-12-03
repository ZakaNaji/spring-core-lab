package com.znaji.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.znaji.core",
        "com.znaji.rules",
        "com.znaji.events"
})
public class CoreConfig {
}
