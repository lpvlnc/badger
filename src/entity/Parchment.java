/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Game;
import main.Game.State;
import sound.AudioPlayer;
import sound.Sound;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Parchment extends Entity {
    public Parchment(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(544, 0, World.TILE_SIZE, World.TILE_SIZE);
        this.setMask(3, 1, 25, 30);
    }
    
    public void update()
    {
        
        if(isColliding(this, Game.player))
        {
            AudioPlayer.play(Sound.collect_item, 1);
            Game.player.parchmentCounter++;
            Entity.addParticle(this);
            Game.entities.remove(this);
            if(Game.player.parchmentCounter >= 10){
                try {
                    Game.changeGameState(State.END);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Parchment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
