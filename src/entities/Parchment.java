package entities;

import audio.Audio;
import audio.AudioPlayer;
import main.Game;
import world.World;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Parchment extends Entity {
    public Parchment(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(544, 0, World.TILE_SIZE, World.TILE_SIZE);
        this.setMask(3, 1, 25, 30);
    }

    public void update() throws InterruptedException {
        if (isColliding(this, Game.player)) {
            AudioPlayer.play(Audio.COLLECT_ITEM, 1);
            Game.player.parchmentCounter++;
            Entity.addParticle(this);
            Game.entities.remove(this);
            if (Game.player.parchmentCounter >= 10){
                Game.changeGameState(Game.State.END);
            }
        }
    }
}
