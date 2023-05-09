package threadlocal;

import threadlocal.inf.Expression;
import threadlocal.inf.ExpressionParser;
import org.springframework.expression.ParserContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内部聚合springParser的语法解析器
 * 默认通过Spring中的 SpelExpressionParser 来生成spring 中的 Expression 对象
 * <p>
 * 这个类默认使用 NON_TEMPLATE_PARSER_CONTEXT 即不使用修饰符来进行变量的标注
 */
public class SpelExpressionParser implements ExpressionParser {
    private org.springframework.expression.ExpressionParser realExpressionParser;
    private static Map<String, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>();
    private boolean allowCache = true;
    private ParserContext parserContext; //转义模板

    public SpelExpressionParser() {
        this.realExpressionParser = new org.springframework.expression.spel.standard.SpelExpressionParser();
        this.parserContext = null;
    }

    public SpelExpressionParser(org.springframework.expression.ExpressionParser realExpressionParser) {
        this.realExpressionParser = realExpressionParser;
        this.parserContext = null;
    }

    public SpelExpressionParser(org.springframework.expression.ExpressionParser realExpressionParser, ParserContext parserContext) {
        this.realExpressionParser = realExpressionParser;
        this.parserContext = parserContext;
    }

    @Override
    public Expression parseExpression(String el) {
        Expression expression = null;
        if (this.allowCache) {
            expression = EXPRESSION_CACHE.get(el);
            if (expression != null) {
                return expression;
            }
        }
        SpelExpression spelExpression = null;
        if (parserContext != null) {
            spelExpression = new SpelExpression(this.realExpressionParser.parseExpression(el, parserContext));
        } else {
            spelExpression = new SpelExpression(this.realExpressionParser.parseExpression(el));
        }
        if (this.allowCache) {
            EXPRESSION_CACHE.put(el, spelExpression);
        }
        return spelExpression;
    }

    @Override
    public Object getValue(String el, Object root) {
        Expression expression = this.parseExpression(el);
        return expression.getValue(root);
    }

    @Override
    public <T> T getValue(String el, Object root, Class<T> desiredCls) {
        Expression expression = this.parseExpression(el);
        return expression.getValue(root, desiredCls);
    }

    @Override
    public void setValue(String el, Object root, Object value) {
        Expression expression = this.parseExpression(el);
        expression.setValue(root, value);
    }
}
