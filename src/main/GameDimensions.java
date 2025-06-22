package main;

public class GameDimensions {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GameDimensions() {
        int windowWidth = Game.window.getWidth();
        int windowHeight = Game.window.getHeight();
        double scaleX = windowWidth / (double)Game.WIDTH;
        double scaleY = windowHeight / (double)Game.HEIGHT;
        double scale = Math.min(scaleX, scaleY); // Keep aspect ratio

        setWidth((int)(Game.WIDTH * scale));
        setHeight((int)(Game.HEIGHT * scale));
        setX((windowWidth - getWidth()) / 2);
        setY((windowHeight - getHeight()) / 2);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
