package entities;

import audio.Audio;
import audio.AudioPlayer;
import main.Game;
import world.World;

import java.awt.image.BufferedImage;

public class InvisibilityGadget extends Entity {
    public InvisibilityGadget(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(512, 96, World.TILE_SIZE, World.TILE_SIZE);
        this.setMask(3, 1, 25, 30);
    }

    public void update()
    {
        if (isColliding(this, Game.player)) {
            AudioPlayer.play(Audio.COLLECT_ITEM, 1);
            Game.player.hasInvisibilityGadget = true;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }
}