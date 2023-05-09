package observer.inf;

import java.util.ArrayList;

/**
 *
 */
public abstract class Subject {
    private String status;  //对象状态
    protected ArrayList<Observer> observersList = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void attach(Observer observer) {
        observersList.add(observer);
    }

    public void detach(String observerName) {
        for (int i = 0; i < observersList.size(); i++) {
            Observer ob = observersList.get(i);
            if (observerName.equals(ob.getObserverName())) {
                observersList.remove(i);
                break;
            }
        }
    }

    /**
     * 触发指定动作的观察者并传递事件对象
     *
     * @param action
     */
    abstract public void fireEvent(String action);
}
