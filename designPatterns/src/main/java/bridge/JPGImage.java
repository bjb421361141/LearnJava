package bridge;

import bridge.inf.Image;
import bridge.inf.Painter;

public class JPGImage extends Image {
    JPGImage(Painter painter) {
        super(painter);
    }

    @Override
    public void parseFile(String imageFilePath) {
        this.painter.draw();    //直接调用draw方法
        //this.painter.accept();  //或者按要求传递参数到paint对象上
    }
}
