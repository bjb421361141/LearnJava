package basic.threadlocal;

//import org.springframework.expression.EvaluationContext;
//import org.springframework.expression.Expression;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;

import basic.threadlocal.inf.Expression;
import basic.threadlocal.inf.ExpressionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Main {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        DefaultSwapArea context = new DefaultSwapArea(parser);
        context.setValue("[\'end\']", "!");
        System.out.println(context.getValue("[\'end\']"));  //SwapArea 中生成Expression 并取值
        System.out.println(context.getValue("(1+1)"));
//        ApplicationContext actionContext = new FileSystemXmlApplicationContext("F:\\workSpace\\LearnJava\\src\\main\\resources\\spring-data.xml");
//        StandardEvaluationContext itemContext = new StandardEvaluationContext(actionContext);
//        Expression exp = parser.parseExpression("#jedisConnectionFactory");
//        org.springframework.data.redis.connection.jedis.JedisConnectionFactory tmp = exp.getValue(itemContext, org.springframework.data.redis.connection.jedis.JedisConnectionFactory.class);
//        System.out.println(tmp.getHostName());
    }
}
