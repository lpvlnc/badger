/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import static entity.Entity.isColliding;
import java.awt.image.BufferedImage;
import main.Game;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Crown extends Entity {
    
    public Crown(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.sprite = Game.spritesheet.getSprite(512, 32, World.TILE_SIZE, World.TILE_SIZE);
    }
    
    @Override
    public void update() {
        if (isColliding(this, Game.player)) {
            Game.player.hasCrown = true;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
}
