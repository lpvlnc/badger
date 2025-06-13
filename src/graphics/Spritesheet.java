package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spritesheet {
    private final BufferedImage spritesheet;

    public Spritesheet() throws IOException {
        spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/spritesheet.png")));
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }
}
