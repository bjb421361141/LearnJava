public class Test1 {
    public static void main(String[] args) {
//        Simple[] simples = new Simple[10];

//        new Simple();
//        System.out.println(SubSimple.x);
        System.out.println(System.getProperty("sun.boot.class.path"));
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        System.out.println(parent.getClass().getSimpleName());
    }
}
