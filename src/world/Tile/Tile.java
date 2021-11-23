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

    // Wall
    public static BufferedImage WALL_TOP = Game.spritesheet.getSprite(128, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL_TOP_SOLID = Game.spritesheet.getSprite(128, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL_TOP_DOOR = Game.spritesheet.getSprite(96, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL_LEFT = Game.spritesheet.getSprite(64, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL = Game.spritesheet.getSprite(96, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL_RIGHT = Game.spritesheet.getSprite(128, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL_BOTTOM_LEFT_CORNER = Game.spritesheet.getSprite(64, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL_BOTTOM_CENTER = Game.spritesheet.getSprite(96, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static BufferedImage WALL_BOTTOM_RIGHT_CORNER = Game.spritesheet.getSprite(128, 608, World.TILE_SIZE, World.TILE_SIZE);
    
    public Tile(int x, int y, int width, int height, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        
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
