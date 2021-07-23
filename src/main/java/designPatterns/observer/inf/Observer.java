package designPatterns.observer.inf;

/**
 * 如果观察对象有情况改变触发观察者的update方法
 */
public abstract class Observer {
    protected String observerName;

    protected Observer(String observerName) {
        this.observerName = observerName;
    }

    public String getObserverName() {
        return observerName;
    }

    public abstract void update(SubjectEvent target);
}
