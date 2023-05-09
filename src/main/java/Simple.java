public class Simple {
    static {
        System.out.println("simple 初始化");
    }

    public static int x = 10;
    public static final int y = 20;

    public static String xStr = "abc";
    public static final String yStr = "abc";

    public static void main(String[] args) {
        String s1 = "Hello";
        String s5 = new String("Hello");
        System.out.println(s1 == s5);

        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

        String s4 = "H"+new String("ello");
        String s6 = "H"+new String("ello");

        System.out.println(s4 == s6);
        System.out.println(s1 == s9);
    }
}
