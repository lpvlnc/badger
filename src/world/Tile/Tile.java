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

/**
 *
 * @author Leonardo
 */
public class Tile {
    
    private BufferedImage sprite;
    private int x;
    private int y;
    private int width;
    private int height;
    
    public boolean show = false;
    public boolean solid = false;
    
    public static BufferedImage FLOOR = Game.spritesheet.getSprite(0, 608, World.TILE_SIZE, World.TILE_SIZE);
    
    // WALL
    public static BufferedImage WALL_TOP = Game.spritesheet.getSprite(128, 544, World.TILE_SIZE, World.TILE_SIZE);
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
    
     public void render(Graphics g){
        if(show){
            g.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null);
        } else {
            g.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null); //pra aplicar o fog of war é só remover esse else
        }
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
