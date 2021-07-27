package basic.threadlocal;

import basic.threadlocal.inf.ExpressionParser;
import basic.threadlocal.inf.SwapArea;
import basic.threadlocal.inf.SwapAreaStatics;

import java.util.HashMap;

/**
 * 可以做一些表达式的翻译？
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
