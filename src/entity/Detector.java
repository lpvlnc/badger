/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.image.BufferedImage;
import main.Game;
import sound.AudioPlayer;
import sound.Sound;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Detector extends Entity {
    public Detector(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(512, 96, World.TILE_SIZE, World.TILE_SIZE);
        this.setMask(3, 1, 25, 30);
    }
    
    public void update()
    {
        if(isColliding(this, Game.player))
        {
            AudioPlayer.play(Sound.collect_item, 1);
            Game.player.hasDetector = true;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
}
