package designPatterns.bridge;

/**
 * 桥接模式是使用抽象对象中放置一个具体的实现类来防止通过继承引起的类爆炸问题
 */
public class Main {
    public static void main(String[] args){
        WindowsPainter windowsPainter = new WindowsPainter();
        LinuxPainter linuxPainter = new LinuxPainter();
        JPGImage jpgImage = new JPGImage(windowsPainter);
        JPGImage jpgImage1 = new JPGImage(linuxPainter);

        //通过继承的方式JPGImage 底下可能有子类 JPGImageWindowsPainter 和  JPGImageLinuxPainter 等等
    }
}
