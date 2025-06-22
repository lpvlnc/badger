package entities;

import main.Camera;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Door extends Entity {

    public BufferedImage doorClosed = Game.spritesheet.getSprite(448, 512, 96, 128);
    public BufferedImage doorOpened = Game.spritesheet.getSprite(544, 512, 96, 128);
    public boolean closed = true;
    public boolean canEnter = false;

    public Door(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-1);
    }

    public void isCollidingWithPlayer() {
        Rectangle playerRect = new Rectangle(Game.player.getX() + Game.player.xMask, Game.player.getY() + Game.player.yMask, Game.player.wMask, Game.player.hMask);
        setMask(0, 0, 32, 127);
        Rectangle doorRect = new Rectangle(getX() + xMask, getY() + yMask, wMask, hMask);
        if (playerRect.intersects(doorRect)) {
            Game.player.x+=Game.player.speed;
        }
        setMask(63, 0, 32, 128);
        doorRect = new Rectangle(getX() + xMask, getY() + yMask, wMask, hMask);
        if (playerRect.intersects(doorRect)) {
            Game.player.x-=Game.player.speed;
        }
        setMask(32, 0, 32, 96);
        doorRect = new Rectangle(getX() + xMask, getY() + yMask, wMask, hMask);
        if (playerRect.intersects(doorRect)) {
            Game.player.y+=Game.player.speed;
        }
    }

    public void playerIsOnEntrance() {
        setMask(0, 98, 96, 30);
        if (closed) {
            if (isColliding(this, Game.player))
                Game.player.y += Game.player.speed;
        } else {
            canEnter = isColliding(this, Game.player);
        }
    }

    public void doorState() {
        if (Game.player.chocolateCounter == 5 && Game.player.hasCrown && Game.player.score >= 1000)
            closed = false;
    }

    @Override
    public void update() throws IOException, InterruptedException {
        isCollidingWithPlayer();
        playerIsOnEntrance();
        doorState();
        if (!closed && canEnter && Game.player.action) {
            Game.nextLevel();
        }
    }

    @Override
    public void render(Graphics g) {
        if (closed)
            g.drawImage(doorClosed, getX() - Camera.x, getY() - Camera.y, null);
        else
            g.drawImage(doorOpened, getX() - Camera.x, getY() - Camera.y, null);
    }
}
