package observer.inf;

/**
 * subject 事件类
 */
public class SubjectEvent {
    String eventType; //事件类型
    String eventName; //事件名称
    Subject target; //目标对象

    public SubjectEvent(String eventType, String eventName, Subject target) {
        this.eventType = eventType;
        this.eventName = eventName;
        this.target = target;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public Subject getTarget() {
        return target;
    }
}
