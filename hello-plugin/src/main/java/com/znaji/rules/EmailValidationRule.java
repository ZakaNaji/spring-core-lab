package com.znaji.rules;

import com.znaji.core.Rule;
import com.znaji.core.RuleContext;
import com.znaji.core.RuleResult;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.regex.Pattern;

@Service
public class EmailValidationRule implements Rule {

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    @Override
    public String getName() {
        return "validate-email";
    }

    @Override
    public Set<String> requiredInputs() {
        return Set.of("email");
    }

    @Override
    public RuleResult execute(RuleContext context) {
        String email = context.getString("email");

        if (email == null || email.isBlank()) {
            return RuleResult.fail("Missing required input: email");
        }

        String normalized = email.trim().toLowerCase();
        boolean valid = EMAIL_REGEX.matcher(normalized).matches();

        context.put("emailNormalized", normalized);
        context.put("isValid", valid);

        if (!valid) {
            return RuleResult.fail("Invalid email format.");
        }

        return RuleResult.ok("Valid email: " + normalized);
    }
}