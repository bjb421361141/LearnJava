package bridge.inf;

public abstract class Image {
   protected Painter painter;

    protected Image(Painter painter) {
        this.painter = painter;
    }

    public abstract void parseFile(String imageFilePath);
}
