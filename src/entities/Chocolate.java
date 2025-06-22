package entities;

import audio.Audio;
import audio.AudioPlayer;
import audio.Volume;
import main.Game;
import world.World;

import java.awt.image.BufferedImage;

public class Chocolate extends Entity {
    public int chocolateScore = 250;
    public Chocolate(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        this.sprite = Game.spritesheet.getSprite(576, 32, World.TILE_SIZE, World.TILE_SIZE);
        setMask(3, 4, 26, 25);
    }

    @Override
    public void update() {
        if (isColliding(this, Game.player)) {
            if (Game.player.chocolateCounter < 5) {
                AudioPlayer.play(Audio.COLLECT_ITEM, Volume.NORMAL);
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