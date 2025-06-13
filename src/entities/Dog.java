package entities;

import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Dog extends Entity{
    public BufferedImage[] dogUp;
    public BufferedImage[] dogDown;
    public boolean up;
    public boolean down;
    public int frames = 0;
    public int maxFrames = 10;
    public int index = 0;
    public int maxIndex = 4;
    public int topOffSet = Game.level == 1 ? (World.TILE_SIZE * 4) : 0;

    public Dog(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-1);

        dogUp = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            dogUp[i] = Game.spritesheet.getSprite(0 + (i * World.TILE_SIZE), 350, 32, 32);

        dogDown = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            dogDown[i] = Game.spritesheet.getSprite(0 + (i * World.TILE_SIZE), 319, 32, 32);

        if (getY() <= 64)
            setY(65);

        down = ThreadLocalRandom.current().nextInt(0, 2) != 0;
    }

    @Override
    public void update() {
        if (getY() <= topOffSet - yMask || !World.isFreeDynamic(getX() + xMask, getY() + yMask - speed, wMask, hMask))
            up = false;

        if (getY() >= World.mapWidth * World.TILE_SIZE - World.TILE_SIZE || !World.isFreeDynamic(getX() + xMask, getY() + yMask + speed, wMask, hMask))
            up = true;

        if (up)
            y -= speed;
        else
            y += speed;

        if (getY() + hMask <= 0)
            up = false;

        if (getY() + World.TILE_SIZE > World.mapHeight * World.TILE_SIZE - hMask)
            up = true;

        frames++;
        if (frames >= maxFrames) {
            frames = 0;
            index++;
            if (index == maxIndex)
                index = 0;
        }

        if (isColliding(this, Game.player))
            Game.player.takeDamage(2);
    }

    @Override
    public void render(Graphics g) {
        if (up) {
            setMask(9, 9, 13, 22);
            g.drawImage(dogUp[index], getX() - Camera.x, getY() - Camera.y, null);
        } else {
            setMask(10, 9, 12, 19);
            g.drawImage(dogDown[index], getX() - Camera.x, getY() - Camera.y, null);
        }
    }
}