package designPatterns.chainResponsibility.inf;

import designPatterns.chainResponsibility.MyFilterChain;
import designPatterns.chainResponsibility.MyRequest;
import designPatterns.chainResponsibility.MyResponse;

public interface MyFilter {
    void doFilter(MyRequest request, MyResponse response, MyFilterChain myFilterChain);
}
