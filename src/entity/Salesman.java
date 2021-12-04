package entity;

import astar.AStar;
import astar.Vector2i;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import main.Game;
import world.Camera;
import world.World;

public class Salesman extends Entity {
    
    public BufferedImage[] salesManUp;
    public BufferedImage[] salesManLeft;
    public BufferedImage[] salesManDown;
    public BufferedImage[] salesManRight;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    
    public int frames = 0;
    public int maxFrames = 15;
    public int index = 0;
    public int maxIndex = 4;
    
    public int visionCenterX;
    public int visionCenterY;
    public int visionRadius = 300;
    Random random = new Random();
    Vector2i spawnPos;

    public Salesman(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(-1);
        //setMask(8, 3, 16, 29);
        visionCenterX = xMask + (wMask / 2);
        visionCenterY = yMask + (hMask / 2);
        spawnPos = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
        speed = 1;
        
        //salesMan = Game.spritesheet.getSprite(0, 417, World.TILE_SIZE, World.TILE_SIZE);
        
        //pronta
        salesManUp = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManUp[i] = Game.spritesheet.getSprite(0 + (i * World.TILE_SIZE), 417, 32, 32);
        }
        //pronta
        salesManLeft = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManLeft[i] = Game.spritesheet.getSprite(0 + (i * World.TILE_SIZE), 480, 32, 32);
        }
        //pronta
        salesManDown = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManDown[i] = Game.spritesheet.getSprite(0 + (i * World.TILE_SIZE), 385, 32, 32);
        }
        //pronta
        salesManRight = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
           salesManRight[i] = Game.spritesheet.getSprite(0 + (i * World.TILE_SIZE), 449, 32, 32);
        }
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
        
        if(isSeeingPlayer()){
            if(random.nextInt(100) < 90){
                if(path == null || path.isEmpty()){
                    Vector2i start = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
                    Vector2i end = new Vector2i(Game.player.getX() / World.TILE_SIZE, Game.player.getY() / World.TILE_SIZE);
                    path = AStar.findPath(Game.world, start, end);
                }
            }
            else{
                Vector2i start = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
                Vector2i end = new Vector2i(Game.player.getX() / World.TILE_SIZE, Game.player.getY() / World.TILE_SIZE);
                path = AStar.findPath(Game.world, start, end);
            }
        }
        else{
            Vector2i start = new Vector2i(getX() / World.TILE_SIZE, getY() / World.TILE_SIZE);
            Vector2i end = spawnPos;
            path = AStar.findPath(Game.world, start, end);
        }
        
        //implementar true das posições que está manual
        //up=true;
        down=true;
        //left=true;
        //right=true;
        
        frames++;
        if(frames >= maxFrames){
            frames = 0;
            index++;
            if(index == maxIndex)
            {
                index = 0;
            }
        }

        if(random.nextInt(100) < 95)
            followPath(path);
        
        if(isColliding(this, Game.player)) {
            Game.player.takeDamage(1);
        }
    }
    
    @Override
    public void render(Graphics g) {
        
        g.setColor(new Color(100, 100, 200, 200));
        
        //pronta
        if(right) {
        setMask(8, 1, 16, 29);
        g.drawImage(salesManRight[index], getX() - Camera.x, getY() - Camera.y, null);
        }
        
        //pronta
         if(left) {
        setMask(8, 3, 16, 29);
        g.drawImage(salesManLeft[index], getX() - Camera.x, getY() - Camera.y, null);
        }
    
        //pronta
        if(up) {
        setMask(7, 0, 16, 30);
        g.drawImage(salesManUp[index], getX() - Camera.x, getY() - Camera.y, null);
        }
        
        //pronta
        if(down) {
        setMask(7, 0, 16, 30);
        g.drawImage(salesManDown[index], getX() - Camera.x, getY() - Camera.y, null);
        }
       
        //g.fillOval(getX() + visionCenterX - visionRadius - Camera.x, getY() + visionCenterY - visionRadius - Camera.y, visionRadius * 2, visionRadius * 2);
    }
}

