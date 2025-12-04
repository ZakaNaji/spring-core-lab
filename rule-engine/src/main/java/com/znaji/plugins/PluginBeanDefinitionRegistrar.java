package com.znaji.plugins;

import com.znaji.core.Rule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.List;
import java.util.Set;

public class PluginBeanDefinitionRegistrar implements BeanDefinitionRegistryPostProcessor {

    private PluginManager manager;
    private ApplicationEventPublisher publisher;

    public PluginBeanDefinitionRegistrar(PluginManager manager, ApplicationEventPublisher publisher) {
        this.manager = manager;
        this.publisher = publisher;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        List<PluginMetadata> pluginMetadata = manager.discoverPlugins();

        for (PluginMetadata metadata : pluginMetadata) {
            try {
                PluginClassLoader loader = new PluginClassLoader(metadata.getJarPath());

                ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
                scanner.setResourceLoader(new PathMatchingResourcePatternResolver(loader));
                scanner.addIncludeFilter(new AssignableTypeFilter(Rule.class));

                Set<BeanDefinition> candidates = scanner.findCandidateComponents("");

                for (BeanDefinition candidate : candidates) {
                    Class<?> clazz = loader.loadClass(candidate.getBeanClassName());
                    if (!Rule.class.isAssignableFrom(clazz)) {
                        continue;
                    }

                    if (clazz.getClassLoader() != loader) {
                        continue; //prevent loading classpath rules twice
                    }

                    Class<? extends Rule> ruleClass = (Class<? extends Rule>) clazz;

                    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                    beanDefinition.setBeanClass(ruleClass);
                    beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

                    registry.registerBeanDefinition(
                            ruleClass.getSimpleName(),
                            beanDefinition
                    );
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
