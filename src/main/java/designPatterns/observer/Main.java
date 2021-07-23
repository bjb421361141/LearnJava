package designPatterns.observer;


public class Main {
    public static void main(String[] args) {
        TextSubject textSubject = new TextSubject();
        textSubject.attach(new ActionObverser("action1"));
        textSubject.attach(new ViewObverser("action1"));
        textSubject.fireEvent("showTip");
    }
}
