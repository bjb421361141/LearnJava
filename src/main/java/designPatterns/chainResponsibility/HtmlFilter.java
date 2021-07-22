package designPatterns.chainResponsibility;

import designPatterns.chainResponsibility.inf.MyFilter;

public class HtmlFilter implements MyFilter {

    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilterChain myFilterChain) {
        String restr = request.getInstr().replaceAll("<","[").replaceAll(">","]");
        request.setInstr(restr+"--- deal HtmlFilter！");
        myFilterChain.doFilter(request,response);  //调用责任链条 执行下一个Filter的操作
        StringBuffer sb = new StringBuffer(response.getOutStr() == null?"":response.getOutStr());
        sb.append(" --- deal HtmlFilter！");
        response.setOutStr(sb.toString());
    }
}
