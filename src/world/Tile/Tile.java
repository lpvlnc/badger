/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world.Tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;
import static world.World.mapWidth;
import static world.World.tiles;

/**
 *
 * @author Leonardo
 */
public class Tile {
    
    BufferedImage sprite;
    int x;
    int y;
    private int width;
    private int height;
    
    public boolean show = true;
    public boolean solid = true;
    public int depth = 0;
    
    // Floor
    public static BufferedImage FLOOR = Game.spritesheet.getSprite(0, 608, World.TILE_SIZE, World.TILE_SIZE);

   
    // Outside Wall
    public static final BufferedImage OUTSIDE_WALL_TOP = Game.spritesheet.getSprite(128, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_TOP_SOLID = Game.spritesheet.getSprite(128, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_TOP_DOOR = Game.spritesheet.getSprite(96, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_LEFT = Game.spritesheet.getSprite(64, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL = Game.spritesheet.getSprite(96, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_RIGHT = Game.spritesheet.getSprite(128, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_BOTTOM_LEFT_CORNER = Game.spritesheet.getSprite(64, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_BOTTOM_CENTER = Game.spritesheet.getSprite(96, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_BOTTOM_RIGHT_CORNER = Game.spritesheet.getSprite(128, 608, World.TILE_SIZE, World.TILE_SIZE);
    
    // Pyramid wall
    public static final BufferedImage PYRAMID_WALL_TOP = Game.spritesheet.getSprite(416, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_TOP_SOLID = Game.spritesheet.getSprite(416, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_TOP_DOOR = Game.spritesheet.getSprite(384, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_LEFT = Game.spritesheet.getSprite(352, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL = Game.spritesheet.getSprite(384, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_RIGHT = Game.spritesheet.getSprite(416, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_BOTTOM_LEFT_CORNER = Game.spritesheet.getSprite(352, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_BOTTOM_CENTER = Game.spritesheet.getSprite(384, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_BOTTOM_RIGHT_CORNER = Game.spritesheet.getSprite(416, 608, World.TILE_SIZE, World.TILE_SIZE);
    
    // wall
    public static BufferedImage WALL_TOP = OUTSIDE_WALL_TOP;
    public static BufferedImage WALL_TOP_SOLID = OUTSIDE_WALL_TOP_SOLID;
    public static BufferedImage WALL_TOP_DOOR = OUTSIDE_WALL_TOP_DOOR;
    public static BufferedImage WALL_LEFT = OUTSIDE_WALL_LEFT;
    public static BufferedImage WALL = OUTSIDE_WALL;
    public static BufferedImage WALL_RIGHT = OUTSIDE_WALL_RIGHT;
    public static BufferedImage WALL_BOTTOM_LEFT_CORNER = OUTSIDE_WALL_BOTTOM_LEFT_CORNER;
    public static BufferedImage WALL_BOTTOM_CENTER = OUTSIDE_WALL_BOTTOM_CENTER;
    public static BufferedImage WALL_BOTTOM_RIGHT_CORNER = OUTSIDE_WALL_BOTTOM_RIGHT_CORNER;
    
    public Tile(int x, int y, int width, int height, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        
    }
    
    public static void changeTilesToOutside(){
        WALL_TOP = OUTSIDE_WALL_TOP;
        WALL_TOP_SOLID = OUTSIDE_WALL_TOP_SOLID;
        WALL_TOP_DOOR = OUTSIDE_WALL_TOP_DOOR;
        WALL_LEFT = OUTSIDE_WALL_LEFT;
        WALL = OUTSIDE_WALL;
        WALL_RIGHT = OUTSIDE_WALL_RIGHT;
        WALL_BOTTOM_LEFT_CORNER = OUTSIDE_WALL_BOTTOM_LEFT_CORNER;
        WALL_BOTTOM_CENTER = OUTSIDE_WALL_BOTTOM_CENTER;
        WALL_BOTTOM_RIGHT_CORNER = OUTSIDE_WALL_BOTTOM_RIGHT_CORNER;
    }
    
    public static void changeTilesToPyramid(){
        WALL_TOP = PYRAMID_WALL_TOP;
        WALL_TOP_SOLID = PYRAMID_WALL_TOP_SOLID;
        WALL_TOP_DOOR = PYRAMID_WALL_TOP_DOOR;
        WALL_LEFT = PYRAMID_WALL_LEFT;
        WALL = PYRAMID_WALL;
        WALL_RIGHT = PYRAMID_WALL_RIGHT;
        WALL_BOTTOM_LEFT_CORNER = PYRAMID_WALL_BOTTOM_LEFT_CORNER;
        WALL_BOTTOM_CENTER = PYRAMID_WALL_BOTTOM_CENTER;
        WALL_BOTTOM_RIGHT_CORNER = PYRAMID_WALL_BOTTOM_RIGHT_CORNER;
    }
    
    public void update(){
        solid = sprite == Tile.WALL_BOTTOM_LEFT_CORNER || sprite == Tile.WALL_BOTTOM_CENTER || sprite == Tile.WALL_BOTTOM_RIGHT_CORNER || sprite == Tile.WALL_TOP_SOLID || tiles[(x / World.TILE_SIZE) + (((y - 1) / World.TILE_SIZE) * mapWidth)].sprite == Tile.WALL_TOP_SOLID;
    }
    
    public void render(Graphics g){
        if(show)
            g.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null);
    }
     
     public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
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
