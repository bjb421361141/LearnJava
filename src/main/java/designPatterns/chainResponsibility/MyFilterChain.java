package designPatterns.chainResponsibility;

import designPatterns.chainResponsibility.inf.MyFilter;

import java.util.ArrayList;

/**
 *
 * 如果有动态增加固定的过滤器可以通过继承 MyFilter 来实现拓展
 */
public class MyFilterChain implements MyFilter {   //implements MyFilter
    private ArrayList<MyFilter> myFilters = new ArrayList<>();
    private MyTarget target;
    private int curindx = 0;

    void MyFilterChain(MyTarget target){
        this.target = target;
    }

    MyFilterChain add(MyFilter filter) {
        myFilters.add(filter);
        return this;
    }

    public void doFilter(MyRequest request, MyResponse response) {
        if(curindx >= myFilters.size()) {
            if(target != null) {
                target.execute(); //执行业务方法后返回
            }
            return;
        };
        MyFilter myFilter = myFilters.get(curindx);
        curindx++;
        myFilter.doFilter(request,response,this);
    }

    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilterChain myFilterChain) {
        request.setInstr(request.getInstr()+" deal MyFilterChain："+this.getClass().getName()+"!");
        this.doFilter(request, response);
        request.setInstr(request.getInstr()+" end deal MyFilterChain："+this.getClass().getName()+"!");
        myFilterChain.doFilter(request,response); //继续调用下一个链条
    }
}
