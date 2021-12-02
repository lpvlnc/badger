/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import astar.Node;
import astar.Vector2i;
import entity.particle.Particle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import main.Game;
import world.Camera;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Entity {
    protected double x;
    protected double y;
    protected int z;
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
    
    public Entity(double x, double y, int width, int height, BufferedImage sprite){
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
    
    public void setMask(int xMask, int yMask, int wMask, int hMask){
        this.xMask = xMask;
        this.yMask = yMask;
        this.wMask = wMask;
        this.hMask = hMask;
    }
    
    public void update(){
    
    }
    
    public void render(Graphics g){
        g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
    }
    
    public void showHitBox(Graphics g){
        if(hasCollision) {
            g.setColor(new Color(100, 255, 100, 200));
            g.fillRect(this.getX() + this.xMask - Camera.x, this.getY() + this.yMask - Camera.y, this.wMask, this.hMask);
        }
    }
    
    public static boolean isColliding(Entity e1, Entity e2){
        Rectangle e1Rect = new Rectangle(e1.getX() + e1.xMask, e1.getY() + e1.yMask, e1.wMask, e1.hMask);
        Rectangle e2Rect = new Rectangle(e2.getX() + e2.xMask, e2.getY() + e2.yMask, e2.wMask, e2.hMask);
        
        return e1Rect.intersects(e2Rect);
    }
    
    public static double calculateDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }
    
    public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            if(e1.depth > e2.depth){
                return 1;
            }
            if(e1.depth < e2.depth){
                return -1;
            }
            return 0;
        }
    };
    
    public static void addParticle(Entity e){
        Game.entities.add(new Particle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE, null));
    }
    
    public void followPath(List<Node> path) {
        if(path != null){
            if(path.size() > 0){
                Vector2i target = path.get(path.size() - 1).tile;
                if(x < target.x * World.TILE_SIZE){
                    isMoving = true;
                    direction = Direction.RIGHT;
                    x+=speed;
                } else if(x  > target.x * World.TILE_SIZE){
                    isMoving = true;
                    direction = Direction.LEFT;
                    x-=speed;
                }
                
                if(y < target.y * World.TILE_SIZE){
                    isMoving = true;
                    direction = Direction.DOWN;
                    y+=speed;
                }else if (y > target.y * World.TILE_SIZE){
                    isMoving = true;
                    direction = Direction.UP;
                    y-=speed;
                }
                
                if(x == target.x * World.TILE_SIZE && y == target.y * World.TILE_SIZE){
                    path.remove(path.size() - 1);
                }
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
    
    public double getXDouble(){
        return this.x;
    }
    
    public double getYDouble(){
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
