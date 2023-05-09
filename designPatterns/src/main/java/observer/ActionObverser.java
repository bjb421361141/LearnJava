package observer;

import observer.inf.Observer;
import observer.inf.SubjectEvent;

public class ActionObverser extends Observer {

    ActionObverser(String obverser) {
        super(obverser);
    }

    @Override
    public void update(SubjectEvent event) {  //这边可以用一个事件对象来丰富操作 例如记录 事件的名称等
        //如果事件和自身相关则进行处理
        if("action".equals(event.getEventType())) {
            System.out.println("这个是action事件,事件是"+event.getEventName());
        } else {
            System.out.println("非action事件,不做处理");
        }
    }
}
