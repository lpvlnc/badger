package entities;

import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Particle extends Entity {
    public BufferedImage[] sparkle;
    public int index = 0;
    public int maxIndex = 5;
    public int maxFrames = 5;
    public int frames = 0;

    public Particle(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.setDepth(-1);
        this.hasCollision = false;
        sparkle = new BufferedImage[7];
        for(int i = 0; i < 7; i++)
            sparkle[i] = Game.spritesheet.getSprite(608, 128 + (i * World.TILE_SIZE), World.TILE_SIZE, World.TILE_SIZE);
    }

    @Override
    public void update() {
        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index == maxIndex)
                Game.entities.remove(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(sparkle[index], this.getX() - Camera.x,  this.getY() - Camera.y, null);
    }
}