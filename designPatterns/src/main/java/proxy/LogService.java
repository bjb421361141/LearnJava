package proxy;

/**
 * 日志记录业务逻辑
 */
public class LogService {

    public void beforelog() {
        System.out.println("do before log");
    }

    public void afterlog() {
        System.out.println("do after log");
    }
}
