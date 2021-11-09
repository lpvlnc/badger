/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.Particle;

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
public class playerWeakParticle extends Entity {
    
    public BufferedImage[] playerWeakParticle;
    public int index = 0;
    public int maxIndex = 4;
    public int maxFrames = 10;
    public int frames = 0;
    
    public playerWeakParticle(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        playerWeakParticle = new BufferedImage[5];
        for (int i = 0; i < 5; i ++) {
            playerWeakParticle[i] = Game.spritesheet.getSprite(480, 0 + (i * World.TILE_SIZE), World.TILE_SIZE, World.TILE_SIZE);
        }
    }
    
    @Override
    public void update() {
        frames++;
        if(frames == maxFrames){
            frames = 0;
            index++;
            if(index == maxIndex){
                index = 0;
            }
        }
    }
    
    @Override
    public void render(Graphics g) {
        if(Game.player.weak) {
            if(Game.player.direction == Direction.RIGHT)
                g.drawImage(playerWeakParticle[index], Game.player.getX() + 7 - Camera.x, Game.player.getY() - Camera.y, null);
        
            if(Game.player.direction == Direction.LEFT)
                g.drawImage(playerWeakParticle[index], Game.player.getX() - 7 - Camera.x, Game.player.getY() - Camera.y, null);

            if(Game.player.direction == Direction.DOWN)
                g.drawImage(playerWeakParticle[index], Game.player.getX() - Camera.x, Game.player.getY() + 7 - Camera.y, null);

            if(Game.player.direction == Direction.UP)
                g.drawImage(playerWeakParticle[index], Game.player.getX() - Camera.x, Game.player.getY() - 10 - Camera.y, null);
        }
    }
}
