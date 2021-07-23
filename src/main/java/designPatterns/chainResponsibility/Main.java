package designPatterns.chainResponsibility;

public class Main {
    public static void main(String[] args) {
        MyFilterChain midFilterChain = new MyFilterChain();
        midFilterChain.add(new TranslateFilter());

        MyFilterChain myFilterChain = new MyFilterChain();
        myFilterChain.add(new HtmlFilter()).add(midFilterChain).add(new SensitiveFilter());


        MyRequest request = new MyRequest();
        request.setInstr("苦逼人生 <script> 脚本 </script>  我不是996");
        MyResponse response = new MyResponse();
        myFilterChain.doFilter(request, response);
        System.out.println(request.getInstr());
        System.out.println(response.getOutStr());
    }
}
