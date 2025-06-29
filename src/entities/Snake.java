package entities;

import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Snake extends Entity{

    public BufferedImage[] snakeLeft;
    public BufferedImage[] snakeRight;

    public boolean up;
    public boolean right;
    public int frames = 0;
    public int maxFrames = 15;
    public int index = 0;
    public int maxIndex = 4;
    private int initialY;

    public Snake(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setMask(9, 12, 16, 10);
        setDepth(-1);

        snakeLeft = new BufferedImage[6];
        for (int i = 0; i < 6; i++)
            snakeLeft[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 288, 32, 32);

        snakeRight = new BufferedImage[6];
        for (int i = 0; i < 6; i++)
            snakeRight[i] = Game.spritesheet.getSprite((i * World.TILE_SIZE), 288, 32, 32);

        if (getY() <= 64)
            setY(65);

        initialY = getY();
        right = ThreadLocalRandom.current().nextInt(0, 2) != 0;
    }

    @Override
    public void update() {
        setMask(9, 12, 16, 10);
        if (getX() <= 0 || !World.isFreeDynamic(getX() + xMask - speed, getY() + yMask, wMask, hMask))
            right = true;

        if (getX() >= World.mapWidth * World.TILE_SIZE - World.TILE_SIZE - 2 || !World.isFreeDynamic(getX() + xMask + speed, getY() + yMask, wMask, hMask))
            right = false;

        if (getY() <= (World.TILE_SIZE * 4) - yMask || !World.isFreeDynamic(getX() + xMask, getY() + yMask - speed, wMask, hMask)) {
            up = false;
            initialY = getY() + (2*World.TILE_SIZE);
        }

        if (getY() + World.TILE_SIZE > World.mapHeight * World.TILE_SIZE - 12 || !World.isFreeDynamic(getX() + xMask, getY() + yMask + speed, wMask, hMask))
            up = true;

        if (right)
            x += speed;
        else
            x -= speed;
        if (up) {
            if (y >= initialY - (2 * World.TILE_SIZE))
                y-=speed;
            else
                up = false;
        } else {
            if (y <= initialY)
                y+=speed;
            else
                up = true;
        }

        frames++;
        if (frames >= maxFrames) {
            frames = 0;
            index++;
            if (index == maxIndex)
                index = 0;
        }

        if (isColliding(this, Game.player))
            Game.player.takeDamage(Game.player.life);
    }

    @Override
    public void render(Graphics g){
        if (right) {
            setMask(4, 2, 21, 30);
            g.drawImage(snakeRight[index], getX() - Camera.x, getY() - Camera.y, null);
        } else {
            setMask(4, 2, 21, 30);
            g.drawImage(snakeLeft[index], getX() - Camera.x, getY() - Camera.y, null);
        }
    }
}