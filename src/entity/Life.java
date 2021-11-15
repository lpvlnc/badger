/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.Entity;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Life extends Entity {
    
    public BufferedImage[] life;
    public int index = 0;
    public int maxIndex = 4;
    public int maxFrames = 6;
    public int frames = 0;
    public boolean increment = true;
    
    public Life(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        life = new BufferedImage[4];
        for(int i = 0; i < 4; i++){
            life[i] = Game.spritesheet.getSprite(448, 0 + (i * World.TILE_SIZE), World.TILE_SIZE, World.TILE_SIZE);
            setMask(10, 11, 11, 10);
        }
    }
    
    public void update(){
        frames++;
        if(frames == maxFrames){
            frames = 0;
            index++;
            if(index == maxIndex){
                index = 0;
            }
        }
        
        if(isColliding(this, Game.player) && Game.player.life < 5)
        {
            Game.player.life++;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
    
    public void render(Graphics g){
        g.drawImage(life[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
