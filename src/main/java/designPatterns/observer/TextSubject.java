package designPatterns.observer;

import designPatterns.observer.inf.Observer;
import designPatterns.observer.inf.Subject;
import designPatterns.observer.inf.SubjectEvent;

/**
 * 可以使用责任链的方式进行事件的处理？
 */
public class TextSubject extends Subject {

    @Override
    public void fireEvent(String action) {
        SubjectEvent actionEvent;
        switch (action) {
            case "keydown":
                actionEvent = new SubjectEvent("action","keydown", this); //获取事件的相关信息
                break;//
            case "keyup":
                actionEvent = new SubjectEvent("action","keyup", this);  //获取事件的相关信息
                break;
            case "showTip":
                actionEvent = new SubjectEvent("view","showTip", this); //获取事件的相关信息
                break;
            default: actionEvent = new SubjectEvent("default","default", this);
        }
        for (Observer observer : observersList) {
            observer.update(actionEvent);
        }
    }
}
