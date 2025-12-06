package com.znaji.plugins;

import com.znaji.core.Rule;
import com.znaji.events.PluginLoadedEvent;
import com.znaji.events.PluginReloadEvent;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PluginLoaderService {

    private final PluginManager pluginManager;
    private final BeanDefinitionRegistry registry;
    private final ApplicationEventPublisher publisher;

    public PluginLoaderService(PluginManager pluginManager, ConfigurableApplicationContext ctx, ApplicationEventPublisher publisher) {
        this.pluginManager = pluginManager;
        this.registry = (BeanDefinitionRegistry) ctx.getBeanFactory();
        this.publisher = publisher;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshed(ContextRefreshedEvent event) {
        loadAllPlugins();
    }

    @EventListener
    public void onPluginReload(PluginReloadEvent event) {
        loadOrReloadPlugin(event.getMetadata());
    }

    public void loadAllPlugins() {
        for (PluginMetadata metadata : pluginManager.discoverPlugins()) {
            loadOrReloadPlugin(metadata);
        }
    }
    public void loadOrReloadPlugin(PluginMetadata metadata) {
        try {
            PluginClassLoader loader = new PluginClassLoader(metadata.getJarPath());

            ClassPathScanningCandidateComponentProvider scanner =
                    new ClassPathScanningCandidateComponentProvider(false);

            scanner.setResourceLoader(new PathMatchingResourcePatternResolver(loader));
            scanner.addIncludeFilter(new AssignableTypeFilter(Rule.class));

            Set<BeanDefinition> candidates = scanner.findCandidateComponents("");

            for (BeanDefinition candidate : candidates) {
                Class<?> clazz = loader.loadClass(candidate.getBeanClassName());

                // ensure class really comes from plugin, not main classpath
                if (clazz.getClassLoader() != loader) {
                    continue;
                }

                if (!Rule.class.isAssignableFrom(clazz)) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                Class<? extends Rule> ruleClass = (Class<? extends Rule>) clazz;

                GenericBeanDefinition def = new GenericBeanDefinition();
                def.setBeanClass(ruleClass);
                def.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

                String beanName = ruleClass.getSimpleName();

                // for reload: remove old definition if exists
                if (registry.containsBeanDefinition(beanName)) {
                    registry.removeBeanDefinition(beanName);
                }

                registry.registerBeanDefinition(beanName, def);
            }

            publisher.publishEvent(new PluginLoadedEvent(this, metadata));

        } catch (Exception e) {
            throw new RuntimeException("Failed to load plugin " + metadata.getJarPath(), e);
        }
    }

    public List<PluginMetadata> discover() {
        return pluginManager.discoverPlugins();
    }
}
