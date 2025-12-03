package com.znaji.core;

public class RuleResult {

    private final boolean success;
    private final Object output;
    private final String errorMessage;

    private RuleResult(boolean success, Object output, String errorMessage) {
        this.success = success;
        this.output = output;
        this.errorMessage = errorMessage;
    }

    public static RuleResult ok(Object output) {
        return new RuleResult(true, output, null);
    }

    public static RuleResult fail(String errorMessage) {
        return new RuleResult(false, null, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getOutput() {
        return output;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
