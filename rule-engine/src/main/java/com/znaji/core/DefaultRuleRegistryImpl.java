package com.znaji.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DefaultRuleRegistryImpl implements RuleRegistry {

    private final Map<String, Rule> rules = new HashMap<>();

    @Autowired
    public DefaultRuleRegistryImpl(List<Rule> rules) {
        for (Rule rule : rules) {
            this.rules.put(rule.getName(), rule);
        }
    }

    @Override
    public Optional<Rule> findByName(String name) {
        return Optional.ofNullable(rules.get(name));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }
}
