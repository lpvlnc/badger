/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Leonardo
 */
public class Spritesheet {
    private BufferedImage spritesheet;
    
    public Spritesheet(String path) throws IOException{
        spritesheet = ImageIO.read(getClass().getResource(path));
    }
    
    public BufferedImage getSprite(int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }
}
