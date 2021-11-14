/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.image.BufferedImage;
import main.Game;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Parchment extends Entity {
    
    public Parchment(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.sprite = Game.spritesheet.getSprite(544, 0, World.TILE_SIZE, World.TILE_SIZE);
        setMask(3, 0, 26, 32);
    }
    
    public void update()
    {
        
        if(isColliding(this, Game.player))
        {
            Game.player.parchmentCounter++;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
}
