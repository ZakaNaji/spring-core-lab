package com.znaji.rules;

import com.znaji.core.Rule;
import com.znaji.core.RuleContext;
import com.znaji.core.RuleResult;
import org.springframework.stereotype.Component;

@Component
public class HelloRule implements Rule {
    public String getName() { return "hello"; }

    public RuleResult execute(RuleContext ctx) {
        return RuleResult.ok("Hello from plugin!");
    }
}
