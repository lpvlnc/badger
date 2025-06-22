package entities;

import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PoisonedParticle extends Entity {

    public BufferedImage[] playerWeakParticle;
    public int index = 0;
    public int maxIndex = 4;
    public int maxFrames = 10;
    public int frames = 0;

    public PoisonedParticle(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.hasCollision = false;
        playerWeakParticle = new BufferedImage[5];
        for (int i = 0; i < 5; i ++)
            playerWeakParticle[i] = Game.spritesheet.getSprite(480, (i * World.TILE_SIZE), World.TILE_SIZE, World.TILE_SIZE);
    }

    @Override
    public void update() {
        frames++;
        if(frames == maxFrames) {
            frames = 0;
            index++;
            if(index == maxIndex)
                index = 0;
        }
    }

    @Override
    public void render(Graphics graphics) {
        if(Game.player.direction == Direction.RIGHT)
            graphics.drawImage(playerWeakParticle[index], Game.player.getX() + 11 - Camera.x, Game.player.getY() - Camera.y, null);

        if(Game.player.direction == Direction.LEFT)
            graphics.drawImage(playerWeakParticle[index], Game.player.getX() - 11 - Camera.x, Game.player.getY() - Camera.y, null);

        if(Game.player.direction == Direction.DOWN)
            graphics.drawImage(playerWeakParticle[index], Game.player.getX() - Camera.x, Game.player.getY() + 7 - Camera.y, null);

        if(Game.player.direction == Direction.UP)
            graphics.drawImage(playerWeakParticle[index], Game.player.getX() - Camera.x, Game.player.getY() - 10 - Camera.y, null);
    }
}
