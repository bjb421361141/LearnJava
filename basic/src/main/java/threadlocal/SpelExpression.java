package threadlocal;

import threadlocal.inf.Expression;

/**
 * spel表达式(类似于装饰器 通过聚合一个对象来丰富这个对象)
 * 内部聚合 spring中的Expression 对象
 */
public class SpelExpression implements Expression {
    private org.springframework.expression.Expression spelExpression;

    public SpelExpression(org.springframework.expression.Expression spelExpression) {
        this.spelExpression = spelExpression;
    }

    @Override
    public Object getValue(Object root) {
        return this.spelExpression.getValue(root);
    }

    @Override
    public <T> T getValue(Object root, Class<T> desiredCls) {
        return this.spelExpression.getValue(root, desiredCls);
    }

    @Override
    public void setValue(Object root, Object value) {
        this.spelExpression.setValue(root, value);
    }
}
