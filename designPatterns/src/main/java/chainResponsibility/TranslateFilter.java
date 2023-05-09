package chainResponsibility;

import chainResponsibility.inf.MyFilter;

public class TranslateFilter implements MyFilter {
    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilterChain myFilterChain) {
        String restr = request.getInstr().replaceAll("我不是", "I'm not ");
        request.setInstr(restr+"deal TranslateFilter！");
        myFilterChain.doFilter(request,response);  //调用责任链条 执行下一个Filter的操作
        StringBuffer sb = new StringBuffer(response.getOutStr() == null ? "" : response.getOutStr());
        sb.append("deal TranslateFilter！");
        response.setOutStr(sb.toString());
    }
}
