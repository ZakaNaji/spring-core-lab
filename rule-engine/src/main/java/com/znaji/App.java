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
        try (var ctx = new AnnotationConfigApplicationContext()) {
            ctx.register(CoreConfig.class);
            ctx.refresh();

            //Rule engine:
            RuleEngine engine = ctx.getBean(RuleEngine.class);

            RuleContext context = new RuleContext();
            context.put("text", "   Hello World!   ");
            RuleResult result1 = engine.execute("trim", context);
            System.out.println("Trim result: " + result1.getOutput());

            RuleContext ctx2 = new RuleContext();
            ctx2.put("text", "hello");
            RuleResult result2 = engine.execute("to-upper", ctx2);
            System.out.println("Uppercase result: " + result2.getOutput());

            RuleContext ctx3 = new RuleContext();
            RuleResult result3 = engine.execute("non-existing", ctx3);
            System.out.println("Missing rule result: " + result3.getErrorMessage());
        }
    }
}
