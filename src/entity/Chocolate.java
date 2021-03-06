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
import sound.Volume;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Chocolate extends Entity {
    public int chocolateScore = 250;
    public Chocolate(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(576, 0, World.TILE_SIZE, World.TILE_SIZE);
        setMask(3, 4, 26, 25);
    }
    
    @Override
    public void update()
    {
        if(isColliding(this, Game.player))
        {
            if(Game.player.chocolateCounter < 5) {
                AudioPlayer.play(Sound.collect_item, Volume.NORMAL);
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
