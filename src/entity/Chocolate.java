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
public class Chocolate extends Entity {
    public int chocolateScore = 250;
    public Chocolate(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.sprite = Game.spritesheet.getSprite(576, 0, World.TILE_SIZE, World.TILE_SIZE);
        setMask(3, 3, 26, 26);
    }
    
    @Override
    public void update()
    {
        if(isColliding(this, Game.player))
        {
            if(Game.player.chocolateCounter < 5) {
                Game.player.chocolateCounter++;
                Game.player.score += chocolateScore;
                Game.player.energy = 100;
                Game.player.energyFrames = 0;
                Entity.addParticle(this);
                Game.entities.remove(this);
            }
        }
    }
}
