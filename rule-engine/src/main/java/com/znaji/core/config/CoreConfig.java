package com.znaji.core.config;

import com.znaji.plugins.PluginBeanDefinitionRegistrar;
import com.znaji.plugins.PluginManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(basePackages = {
        "com.znaji.core",
        "com.znaji.events",
        "com.znaji.dsl",
        "com.znaji.plugins",
        "com.znaji.shell"
})
@EnableScheduling
public class CoreConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("plugin-watcher-");
        scheduler.initialize();
        return scheduler;
    }

    //@Bean
    public static PluginBeanDefinitionRegistrar pluginBeanDefinitionRegistrar(ApplicationEventPublisher publisher) {
        return new PluginBeanDefinitionRegistrar(new PluginManager(), publisher);
    }
}
