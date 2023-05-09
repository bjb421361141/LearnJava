package chainResponsibility;

import chainResponsibility.inf.MyFilter;

public class SensitiveFilter implements MyFilter {

    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilterChain myFilterChain) {
        String restr = request.getInstr().replaceAll("996", "965");
        request.setInstr(restr+"deal SensitiveFilter！");
        myFilterChain.doFilter(request,response);  //调用责任链条 执行下一个Filter的操作
        StringBuffer sb = new StringBuffer(response.getOutStr() == null ? "" : response.getOutStr());
        sb.append("deal SensitiveFilter！");
        response.setOutStr(sb.toString());
    }
}
