package threadlocal.inf;

import java.util.Map;

public interface SwapArea extends Map {

    Object getValue(String el);

    <T> T getValue(String val, Class<T> cls);

    void setValue(String val, Object object);

    boolean containPath(String val);
}
