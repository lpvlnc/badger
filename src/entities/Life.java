package entities;

import audio.Audio;
import audio.AudioPlayer;
import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Life extends Entity {
    public BufferedImage[] life;
    public int index = 0;
    public int maxIndex = 4;
    public int maxFrames = 6;
    public int frames = 0;
    public boolean increment = true;

    public Life(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-2);
        life = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
            life[i] = Game.spritesheet.getSprite(448, (i * World.TILE_SIZE), World.TILE_SIZE, World.TILE_SIZE);
            setMask(10, 11, 11, 10);
        }
    }

    public void update() {
        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index == maxIndex)
                index = 0;
        }

        if (isColliding(this, Game.player) && Game.player.life < 5) {
            AudioPlayer.play(Audio.COLLECT_ITEM, 1);
            Game.player.life++;
            Entity.addParticle(this);
            Game.entities.remove(this);
        }
    }

    public void render(Graphics g) {
        g.drawImage(life[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}