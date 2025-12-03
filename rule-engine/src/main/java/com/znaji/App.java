package com.znaji;

import com.znaji.core.RuleChain;
import com.znaji.core.RuleContext;
import com.znaji.core.RuleEngine;
import com.znaji.core.RuleResult;
import com.znaji.core.config.CoreConfig;
import com.znaji.dsl.DslParser;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try (var ctx = new AnnotationConfigApplicationContext()) {
            ctx.register(CoreConfig.class);
            ctx.refresh();

            //Rule engine:
            RuleEngine engine = ctx.getBean(RuleEngine.class);
            DslParser parser = ctx.getBean(DslParser.class);

            RuleContext context = new RuleContext();
            context.put("text", "   HELLO   ");

            // Example chain
            String chainInput = "cleanup: trim -> to-upper";
            RuleChain chain = parser.parse(chainInput);

            RuleResult result = engine.executeChain(chain, context);

            System.out.println("Final result: " + result.getOutput());
        }
    }
}
