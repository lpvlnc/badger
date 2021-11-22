/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.Tile;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

/**
 *
 * @author Leonardo
 */
public class TileWall extends Tile {
    
    public TileWall(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
    
    @Override
    public void render(Graphics g){
        
        if(Game.player.xRay) {
            Graphics2D g2 = (Graphics2D)g;
            Composite comp;
            comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2.setComposite(comp);
            g2.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null);
        } else {
            g.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null);
        }
    }
}
