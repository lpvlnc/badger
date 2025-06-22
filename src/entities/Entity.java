package entities;

import astar.Node;
import astar.Vector2i;
import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Entity {

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected int xMask;
    protected int yMask;
    protected int wMask;
    protected int hMask;
    BufferedImage sprite;
    protected boolean hasCollision = true;
    public enum Direction {
        UP, LEFT, DOWN, RIGHT
    }
    public Direction direction = Direction.RIGHT;
    public boolean isMoving = false;
    public int depth;
    protected List<Node> path;
    public int speed = 1;

    public Entity(double x, double y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.xMask = 0;
        this.yMask = 0;
        this.wMask = World.TILE_SIZE;
        this.hMask = World.TILE_SIZE;
    }

    public void setMask(int xMask, int yMask, int wMask, int hMask) {
        this.xMask = xMask;
        this.yMask = yMask;
        this.wMask = wMask;
        this.hMask = hMask;
    }

    public void update() throws IOException, InterruptedException {

    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
    }

    public void showHitBox(Graphics graphics) {
        if (hasCollision) {
            graphics.setColor(new Color(100, 255, 100, 200));
            graphics.fillRect(this.getX() + this.xMask - Camera.x, this.getY() + this.yMask - Camera.y, this.wMask, this.hMask);
        }
    }

    public static boolean isColliding(Entity e1, Entity e2) {
        Rectangle e1Rect = new Rectangle(e1.getX() + e1.xMask, e1.getY() + e1.yMask, e1.wMask, e1.hMask);
        Rectangle e2Rect = new Rectangle(e2.getX() + e2.xMask, e2.getY() + e2.yMask, e2.wMask, e2.hMask);
        return e1Rect.intersects(e2Rect);
    }

    public static double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            return Integer.compare(e1.depth, e2.depth);
        }
    };

    public static void addParticle(Entity e) {
        Game.entities.add(new Particle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE, null));
    }

    public void followPath(List<Node> path) {
        if (path != null) {
            if (!path.isEmpty()) {
                Vector2i target = path.get(path.size() - 1).tile;
                if (x < target.x * World.TILE_SIZE) {
                    isMoving = true;
                    direction = Direction.RIGHT;
                    x+=speed;
                } else if (x  > target.x * World.TILE_SIZE) {
                    isMoving = true;
                    direction = Direction.LEFT;
                    x-=speed;
                }
                if (y < target.y * World.TILE_SIZE) {
                    isMoving = true;
                    direction = Direction.DOWN;
                    y+=speed;
                } else if (y > target.y * World.TILE_SIZE) {
                    isMoving = true;
                    direction = Direction.UP;
                    y-=speed;
                }
                if (x == target.x * World.TILE_SIZE && y == target.y * World.TILE_SIZE)
                    path.removeLast();
            }
        }
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getX() {
        return (int)this.x;
    }

    public double getXDouble() {
        return this.x;
    }

    public double getYDouble() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return (int)this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
