package tiles;

import main.Camera;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileWall extends Tile {

    public TileWall(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    @Override
    public void render(Graphics graphics) {
        if(Game.player.xRay) {
            Graphics2D g2 = (Graphics2D)graphics;
            Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2.setComposite(comp);
            g2.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null);
        } else {
            graphics.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null);
        }
    }
}