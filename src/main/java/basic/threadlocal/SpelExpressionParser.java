package basic.threadlocal;

import basic.threadlocal.inf.Expression;
import basic.threadlocal.inf.ExpressionParser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内部聚合springParser的语法解析器
 * 默认通过Spring中的 SpelExpressionParser 来生成spring 中的 Expression 对象
 */
public class SpelExpressionParser implements ExpressionParser {
    private org.springframework.expression.ExpressionParser realExpressionParser;
    private static Map<String, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>();
    private boolean allowCache = true;

    public SpelExpressionParser() {
        this.realExpressionParser = new org.springframework.expression.spel.standard.SpelExpressionParser();
    }

    public SpelExpressionParser(org.springframework.expression.ExpressionParser realExpressionParser) {
        this.realExpressionParser = realExpressionParser;
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
        SpelExpression spelExpression = new SpelExpression(this.realExpressionParser.parseExpression(el));
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
        return expression.getValue(root,desiredCls);
    }

    @Override
    public void setValue(String el, Object root, Object value) {
        Expression expression = this.parseExpression(el);
        expression.setValue(root, value);
    }
}
