package entities;

import astar.AStar;
import astar.Vector2i;
import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Scarab extends Entity {
    public BufferedImage[] scarabUp;
    public BufferedImage[] scarabLeft;
    public BufferedImage[] scarabDown;
    public BufferedImage[] scarabRight;

    public int frames = 0;
    public int maxFrames = 10;
    public int index = 0;
    public int maxIndex = 4;

    public int visionCenterX;
    public int visionCenterY;
    public int visionRadius = 200;
    Random random = new Random();
    Vector2i spawnPos;

    public Scarab(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-1);
        direction = Direction.DOWN;
        visionCenterX = xMask + (wMask / 2);
        visionCenterY = yMask + (hMask / 2);
        spawnPos = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
        speed = 1;

        scarabUp = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            scarabUp[i] = Game.spritesheet.getSprite((i * World.TILE_SIZE), 417, 32, 32);

        scarabLeft = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            scarabLeft[i] = Game.spritesheet.getSprite((i * World.TILE_SIZE), 480, 32, 32);

        scarabDown = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            scarabDown[i] = Game.spritesheet.getSprite((i * World.TILE_SIZE), 385, 32, 32);

        scarabRight = new BufferedImage[4];
        for(int i = 0; i < 4; i++)
            scarabRight[i] = Game.spritesheet.getSprite((i * World.TILE_SIZE), 449, 32, 32);
    }

    public boolean isSeeingPlayer() {
        int playerCenterX = Game.player.getX() + Game.player.xMask + (Game.player.wMask / 2);
        int playerCenterY = Game.player.getY() + Game.player.yMask + (Game.player.hMask / 2);
        int playerRadius = World.TILE_SIZE;

        int distSq = (getX() + visionCenterX - playerCenterX) * (getX() + visionCenterX - playerCenterX) +
                     (getY() + visionCenterY - playerCenterY) * (getY() + visionCenterY - playerCenterY);
        int radSumSq = (visionRadius + playerRadius) * (visionRadius + playerRadius);

        return distSq < radSumSq;
    }

    @Override
    public void update() {
        isMoving = false;
        if(isSeeingPlayer() && !Game.player.invisible) {
            if(random.nextInt(100) < 90) {
                if(path == null || path.isEmpty() && AStar.clear()) {
                    Vector2i start = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
                    Vector2i end = new Vector2i(Game.player.getX() / World.TILE_SIZE, Game.player.getY() / World.TILE_SIZE);
                    path = AStar.findPath(Game.world, start, end);
                }
            }
            else {
                Vector2i start = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
                Vector2i end = new Vector2i(Game.player.getX() / World.TILE_SIZE, Game.player.getY() / World.TILE_SIZE);
                path = AStar.findPath(Game.world, start, end);
            }
        }
        else {
            Vector2i start = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
            Vector2i end = spawnPos;
            path = AStar.findPath(Game.world, start, end);
        }

        frames++;
        if(frames >= maxFrames) {
            frames = 0;
            index++;
            if(index == maxIndex)
                index = 0;
        }

        if(random.nextInt(100) < 95)
            followPath(path);

        if(isColliding(this, Game.player))
            Game.player.takeDamage(1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(100, 100, 200, 200));
        if(direction == Direction.RIGHT) {
            setMask(8, 1, 16, 29);
            g.drawImage(scarabRight[index], getX() - Camera.x, getY() - Camera.y, null);
        }

        if(direction == Direction.LEFT) {
            setMask(8, 3, 16, 29);
            g.drawImage(scarabLeft[index], getX() - Camera.x, getY() - Camera.y, null);
        }

        if(direction == Direction.UP) {
            setMask(7, 0, 16, 30);
            g.drawImage(scarabUp[index], getX() - Camera.x, getY() - Camera.y, null);
        }

        if(direction == Direction.DOWN) {
            setMask(7, 0, 16, 30);
            g.drawImage(scarabDown[index], getX() - Camera.x, getY() - Camera.y, null);
        }

        //g.fillOval(getX() + visionCenterX - visionRadius - Camera.x, getY() + visionCenterY - visionRadius - Camera.y, visionRadius * 2, visionRadius * 2);
    }
}