package eshopUtil.Exception;

/**
 * 一般异常:直接使用Exception 中的方法
 */
public class BusinessException extends Exception {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
