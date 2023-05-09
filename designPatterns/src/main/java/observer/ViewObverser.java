package observer;

import observer.inf.Observer;
import observer.inf.SubjectEvent;

public class ViewObverser extends Observer {
    ViewObverser(String observerName) {
        super(observerName);
    }

    @Override
    public void update(SubjectEvent event) {
        //如果事件和自身相关则进行处理
        if("view".equals(event.getEventType())) {
            System.out.println("这个是view事件,事件是"+event.getEventName());
        } else {
            System.out.println("非view事件,不做处理");
        }
    }
}
