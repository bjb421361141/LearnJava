package basic.threadlocal.inf;

public interface ExpressionParser {
    Expression parseExpression(String el);

    Object getValue(String el, Object root);

    <T> T getValue(String el, Object root, Class<T> cls);

    void setValue(String el, Object root, Object value);
}
