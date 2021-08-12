package basic.threadlocal;


import basic.threadlocal.inf.ExpressionParser;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * 测试标签 中的操作
     *
     * @throws IOException
     */
    void smallTest() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        retry:
        for (; ; ) {
            for (; ; ) {
                System.out.println(" working now!");
                String str = bufferedReader.readLine();
                if ("quit".equals(str)) {
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
        context.setValue(DefaultSwapArea.SERVICE_ID, "A0862A001");
        System.out.println(context.getValue(DefaultSwapArea.SERVICE_ID));  //SwapArea 中生成Expression 并取值
        System.out.println(context.getValue(DefaultSwapArea.THIS).toString());
        //
        ApplicationContext actionContext = new FileSystemXmlApplicationContext("F:\\workSpace\\LearnJava\\src\\main\\resources\\spring-data.xml");
        org.springframework.expression.ExpressionParser realExpressionParser = new org.springframework.expression.spel.standard.SpelExpressionParser();
        Expression expression = realExpressionParser.parseExpression("#{T(System).currentTimeMillis()}", new TemplateParserContext()); //引用具体的某个类
        System.out.println(expression.getValue(actionContext));
        Expression expression2 = realExpressionParser.parseExpression("#{beanFactory.getBean(\"jedisConnectionFactory\")}", new TemplateParserContext()); //引用具体的某个类
        org.springframework.data.redis.connection.jedis.JedisConnectionFactory tmp = expression2.getValue(actionContext, org.springframework.data.redis.connection.jedis.JedisConnectionFactory.class);
        System.out.println(tmp.getHostName());

        //spring 中的bean操作
        StandardBeanExpressionResolver sber = new StandardBeanExpressionResolver();
        BeanExpressionContext beanExpressionContext = new BeanExpressionContext(((FileSystemXmlApplicationContext) actionContext).getBeanFactory(), new SimpleThreadScope());
        org.springframework.data.redis.connection.jedis.JedisConnectionFactory jedi = (JedisConnectionFactory) sber.evaluate("#{jedisConnectionFactory}", beanExpressionContext);
        System.out.println(jedi.getHostName());
    }
}
