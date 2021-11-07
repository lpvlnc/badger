/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;

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
        g.drawImage(TILE_WALL, getX() - Camera.x, getY() - Camera.y, null);
    }
}
