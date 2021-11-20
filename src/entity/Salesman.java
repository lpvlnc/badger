/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import astar.AStar;
import astar.Node;
import astar.Vector2i;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import main.Game;
import world.Camera;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Salesman extends Entity {
    public BufferedImage salesMan;
    public BufferedImage[] salesManUp;
    public BufferedImage[] salesManLeft;
    public BufferedImage[] salesManDown;
    public BufferedImage[] salesManRight;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public double speed;

    public Salesman(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        speed = ThreadLocalRandom.current().nextDouble(0.5, 2);
        System.out.println(speed);
        salesMan = Game.spritesheet.getSprite(0, 352, World.TILE_SIZE, World.TILE_SIZE);
        salesManUp = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManUp[0] = Game.spritesheet.getSprite(32, 352 + (i * 32), World.TILE_SIZE, World.TILE_SIZE);
        }
        
        salesManLeft = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManLeft[0] = Game.spritesheet.getSprite(96, 352 + (i * 32), World.TILE_SIZE, World.TILE_SIZE);
        }
        
        salesManDown = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManDown[0] = Game.spritesheet.getSprite(0, 352 + (i * 32), World.TILE_SIZE, World.TILE_SIZE);
        }
        
        salesManRight = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManRight[0] = Game.spritesheet.getSprite(64, 352 + (i * 32), World.TILE_SIZE, World.TILE_SIZE);
        }
    }
     
    @Override
    public void update() {
        if(path == null || path.isEmpty()){
            Vector2i start = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
            Vector2i end = new Vector2i(Game.player.getX() / World.TILE_SIZE, Game.player.getY() / World.TILE_SIZE);
            path = AStar.findPath(Game.world, start, end);
        }
        if(new Random().nextInt(100) < 98)
            followPath(path);
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(salesMan, getX() - Camera.x, getY() - Camera.y, null);
    }
}
