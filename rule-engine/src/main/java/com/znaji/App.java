package com.znaji;

import com.znaji.core.RuleContext;
import com.znaji.core.RuleEngine;
import com.znaji.core.RuleResult;
import com.znaji.core.config.CoreConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(CoreConfig.class);
            context.refresh();

            RuleContext ruleContext = new RuleContext();
            ruleContext.put("text", "    test        ");
            //Rule engine:
            RuleEngine engine = context.getBean(RuleEngine.class);

            RuleResult trimResult = engine.execute("trim", ruleContext);
            System.out.println(trimResult.getOutput());

            RuleResult toUpperResult = engine.execute("to-upper", ruleContext);
            System.out.println(toUpperResult.getOutput());
        }
    }
}
