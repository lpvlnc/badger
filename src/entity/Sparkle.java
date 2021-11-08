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
import world.World;

/**
 *
 * @author Leonardo
 */
public class Sparkle extends Entity {
    
    public BufferedImage[] sparkle;
    public int index = 0;
    public int maxIndex = 5;
    public int maxFrames = 5;
    public int frames = 0;
    
    public Sparkle(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.setDepth(-1);
        sparkle = new BufferedImage[7];
        for(int i = 0; i < 7; i++) {
            sparkle[i] = Game.spritesheet.getSprite(608, 128 + (i * World.TILE_SIZE), World.TILE_SIZE, World.TILE_SIZE);
        }
    }
    
    public void update() {
        frames++;
        if(frames == maxFrames){
            frames = 0;
            index++;
            if(index == maxIndex){
                Game.entities.remove(this);
            }
        }
    }
    
    public void render(Graphics g) {
        g.drawImage(sparkle[index], this.getX() - Camera.x,  this.getY() - Camera.y, null);
    }
}