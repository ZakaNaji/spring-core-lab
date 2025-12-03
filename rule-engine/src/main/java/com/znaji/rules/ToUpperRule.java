package com.znaji.rules;

import com.znaji.core.Rule;
import com.znaji.core.RuleContext;
import com.znaji.core.RuleResult;
import org.springframework.stereotype.Service;

@Service
public class ToUpperRule implements Rule {
    @Override
    public String getName() {
        return "to-upper";
    }

    @Override
    public RuleResult execute(RuleContext context) {
        String text = context.getString("text");
        if (text == null) {
            return RuleResult.fail("Missing field: text");
        }
        return RuleResult.ok(text.toUpperCase());
    }
}
