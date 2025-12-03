package com.znaji.dsl;

import com.znaji.core.RuleChain;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DslParser {
    /**
     * Parses strings like:
     * "discount: vip-check -> apply-10-percent"
     */
    public RuleChain parse(String input) {
        String[] parts = input.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid DSL format, expected: name: rule1 -> rule2");
        }

        String chainName = parts[0].trim();
        String rulesSection = parts[1].trim();

        List<String> ruleNames =
                Arrays.stream(rulesSection.split("->"))
                        .map(String::trim)
                        .collect(Collectors.toList());

        return new RuleChain(chainName, ruleNames);
    }
}
