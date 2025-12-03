package com.znaji.core;


import java.util.List;

public class RuleChain {

    private final String name;
    private final List<String> ruleNames;

    public RuleChain(String name, List<String> ruleNames) {
        this.name = name;
        this.ruleNames = ruleNames;
    }

    public String getName() {
        return name;
    }

    public List<String> getRuleNames() {
        return ruleNames;
    }
}
