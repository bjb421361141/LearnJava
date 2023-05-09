package chainResponsibility;

public class MyTarget {
    String business;

    public MyTarget(String business) {
        this.business = business;
    }

    public void execute() {
        System.out.println("execute "+business+" code");
    }
}
