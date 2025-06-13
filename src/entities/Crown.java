package entities;

import audio.Audio;
import audio.AudioPlayer;
import audio.Volume;
import main.Game;
import world.World;

import java.awt.image.BufferedImage;

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
            AudioPlayer.play(Audio.COLLECT_ITEM, Volume.NORMAL);
            Game.player.hasCrown = true;
            Game.player.score+=250;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
}