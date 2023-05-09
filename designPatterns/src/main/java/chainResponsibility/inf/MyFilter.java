package chainResponsibility.inf;

import chainResponsibility.MyFilterChain;
import chainResponsibility.MyRequest;
import chainResponsibility.MyResponse;

public interface MyFilter {
    void doFilter(MyRequest request, MyResponse response, MyFilterChain myFilterChain);
}
