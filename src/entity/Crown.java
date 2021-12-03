/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import static entity.Entity.isColliding;
import java.awt.image.BufferedImage;
import main.Game;
import sound.AudioPlayer;
import sound.Sound;
import sound.Volume;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Crown extends Entity {
    
    public Crown(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setMask(3, 13, 26, 7);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(512, 32, World.TILE_SIZE, World.TILE_SIZE);
    }
    
    @Override
    public void update() {
        if (isColliding(this, Game.player)) {
            AudioPlayer.play(Sound.collect_item, Volume.NORMAL);
            Game.player.hasCrown = true;
            Game.player.score+=250;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
}
