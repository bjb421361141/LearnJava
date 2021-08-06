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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * 测试标签 中的操作
     * @throws IOException
     */
    void smallTest() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        retry:
        for (;;) {
            for (;;) {
                System.out.println(" working now!");
                String str = bufferedReader.readLine();
                if ("quit".equals(str)){
                    return; //直接结束方法
                }
                if ("try".equals(str)) {
                    break retry; //跳回至标签继续执行余下内容
                }
                if ("continue".equals(str)) {
                    continue retry; //继续循环
                }
            }
        }
        System.out.println("do things.");
    }

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
