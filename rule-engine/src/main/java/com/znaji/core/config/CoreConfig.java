package com.znaji.core.config;

import com.znaji.plugins.PluginBeanDefinitionRegistrar;
import com.znaji.plugins.PluginManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.znaji.core",
        "com.znaji.events",
        "com.znaji.dsl",
        "com.znaji.plugins"
})
public class CoreConfig {

    @Bean
    public static PluginBeanDefinitionRegistrar pluginBeanDefinitionRegistrar(ApplicationEventPublisher publisher) {
        return new PluginBeanDefinitionRegistrar(new PluginManager(), publisher);
    }
}
