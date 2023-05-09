package threadlocal;

import threadlocal.inf.ExpressionParser;
import threadlocal.inf.SwapArea;
import threadlocal.inf.SwapAreaStatics;

import java.util.HashMap;

/**
 * 本身继承于HashMap
 * 内聚一个expressionParser 对象对设置的值进行翻译（这边主要是用spring el表达式对map对象数据的操作）
 */
public class DefaultSwapArea extends HashMap implements SwapArea, SwapAreaStatics {
    private static final long serialVersionUID = 4305683403992544070L;
    private ExpressionParser expressionParser;

    public DefaultSwapArea(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Override
    public Object getValue(String el) {
        return this.expressionParser.getValue(el, this);
    }

    @Override
    public <T> T getValue(String el, Class<T> cls) {
        return this.expressionParser.getValue(el, this, cls);
    }

    @Override
    public void setValue(String el, Object value) {
        this.expressionParser.setValue(el, this, value);
    }

    @Override
    public boolean containPath(String el) {
        try {
            Object t = this.expressionParser.getValue(el, this);
            return t != null;
        } catch (Throwable t) {
            return false;
        }
    }
}
