package com.znaji.rules;

import com.znaji.core.Rule;
import com.znaji.core.RuleContext;
import com.znaji.core.RuleResult;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ToUpperRule implements Rule {
    @Override
    public String getName() {
        return "to-upper";
    }

    @Override
    public RuleResult execute(RuleContext context) {
        String input = context.getString("text");
        if (input == null) {
            return RuleResult.fail("Missing field: text");
        }
        String output = input.toUpperCase();
        context.put("text", output);

        return RuleResult.ok(output);
    }

    @Override
    public Set<String> requiredInputs() {
        return Set.of("text");
    }
}
