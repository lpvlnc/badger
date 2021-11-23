/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;

/**
 *
 * @author Leonardo
 */
public class Door extends Entity {
    
    public BufferedImage doorClosed = Game.spritesheet.getSprite(448, 512, 96, 128);
    public BufferedImage doorOpened = Game.spritesheet.getSprite(544, 512, 96, 128);
    
    public boolean closed = true;
    
    public Door(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-1);
    }
    
    public void update(){
        
    }
    
    public void render(Graphics g){
        if(closed)
            g.drawImage(doorClosed, getX() - Camera.x, getY() - Camera.y, null);
        else
            g.drawImage(doorOpened, getX() - Camera.x, getY() - Camera.y, null);
    }
}
