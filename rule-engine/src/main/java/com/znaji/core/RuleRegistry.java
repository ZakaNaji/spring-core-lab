package com.znaji.core;

import java.util.Collection;
import java.util.Optional;

public interface RuleRegistry  {

    /**
     * Finds a rule by its name.
     *
     * @param name the name of the rule to search for
     * @return an {@code Optional} containing the found rule, or {@code Optional.empty()} if no rule is found
     */
    Optional<Rule> findByName(String name);

    /**
     * Retrieves all registered rules in the registry.
     *
     * @return a collection of all registered rules
     */
    Collection<Rule> findAll();
}
