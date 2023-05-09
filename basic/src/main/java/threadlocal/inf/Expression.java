package threadlocal.inf;

public interface Expression {
    Object getValue(Object root);

    <T> T getValue(Object root, Class<T> desiredCls);

    void setValue(Object root, Object value);
}
