package entities;

import audio.Audio;
import audio.AudioPlayer;
import main.Game;
import world.World;

import java.awt.image.BufferedImage;

public class Steroid extends Entity {

    public Steroid(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(512, 0, World.TILE_SIZE, World.TILE_SIZE);
        setMask(5, 5, 22, 23);
    }

    @Override
    public void update() {
        if (isColliding(this, Game.player)) {
            AudioPlayer.play(Audio.COLLECT_ITEM, 1);
            Game.player.steroidCounter++;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
}