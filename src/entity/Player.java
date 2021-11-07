/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

/**
 *
 * @author Leonardo
 */
public class Player extends Entity {
    // PLAYER GRAPHICS
    // sprites
    public BufferedImage[] playerUp;
    public BufferedImage[] playerLeft;
    public BufferedImage[] playerDown;
    public BufferedImage[] playerRight;

    // animation logic
    public boolean isMoving = false;
    public int index = 0;
    public int maxIndex = 5;
    public int frames = 0;
    public int maxFrames = 5;
    
    // PLAYER POSITION
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    
    public int maxLife = 3;
    public int life = maxLife;
    public static int speed = 2;
    public boolean dead = false;
    public boolean damaged = false;
    public boolean canBeDamaged = true;
    public int damagedFrames = 0;
    public int maxDamagedFrames = 60;
    public int score = 0;
    public int highScore = 0;
    
    public Player(double x,double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        
        setDepth(0);
        playerUp = new BufferedImage[4];
        playerLeft = new BufferedImage[6];
        playerDown = new BufferedImage[4];
        playerRight = new BufferedImage[6];
        
        for (int i = 0; i < 4; i++) {
            playerUp[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 6; i++) {
            playerLeft[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 32, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 4; i++){
            playerDown[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 64, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for(int i = 0; i < 6; i++){
            playerRight[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 96, World.TILE_SIZE, World.TILE_SIZE);
        }
    }
    
    public void movement() {
        isMoving = false;
        if(up) {
            this.direction = Direction.UP;
            isMoving = true;
            y -= speed;
        }
        
        if(left) {
            this.direction = Direction.LEFT;
            isMoving = true;
            x -= speed;
        }
        if(down) {
            this.direction = Direction.DOWN;
            isMoving = true;
            y += speed;
        }
        
        if(right) {
            this.direction = Direction.RIGHT;
            isMoving = true;
            x += speed;
        }
    }
    
    public void animation()
    {
        frames++;
        if(frames == maxFrames){
            frames = 0;
            index++;
            if(index == maxIndex){
                index = 0;
            }
        }
    }
    
    @Override
    public void update(){
        
        if(canBeDamaged){
            if(damaged){
                canBeDamaged = false;
                life-=1;
            }
        }
        
        if(!canBeDamaged){
            damaged = false;
            damagedFrames++;
            if(damagedFrames == maxDamagedFrames){
                damagedFrames = 0;
                canBeDamaged = true;
            }
        
        }
        
        if(life <= 0){
            life = 0;
           Game.gameOver = true;
        }
        
        if(score > highScore){
            highScore = score;
        }

        movement();
        animation();
        updateCamera();
    }
    
    @Override
    public void render(Graphics g){
        if(this.direction == Direction.UP) {
            if(isMoving)
            {
                if (index > 3)
                    index = 0;
                g.drawImage(playerUp[index], getX() - Camera.x, getY() - Camera.y, null);
            } else
                g.drawImage(playerUp[0], getX() - Camera.x, getY() - Camera.y, null);
        }
        
        if(this.direction == Direction.LEFT) {
            if(isMoving)
            {
                g.drawImage(playerLeft[index], getX() - Camera.x, getY() - Camera.y, null);
            }
            else
            {
                g.drawImage(playerLeft[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        }
            
        if(this.direction == Direction.DOWN){
            if(isMoving) {
                if (index > 3)
                    index = 0;
                g.drawImage(playerDown[index], getX() - Camera.x, getY() - Camera.y, null);
            } else
                g.drawImage(playerDown[0], getX() - Camera.x, getY() - Camera.y, null);
        }
        
        if(this.direction == Direction.RIGHT){
            if(isMoving)
                g.drawImage(playerRight[index], getX() - Camera.x, getY() - Camera.y, null);
            else
                g.drawImage(playerRight[0], getX() - Camera.x, getY() - Camera.y, null);
        }
    }
    
    public void updateCamera(){
        Camera.x = Camera.clamp(getX() - Game.WIDTH/ 2, 0, World.mapWidth * World.TILE_SIZE - Game.WIDTH);
        Camera.y = Camera.clamp(getY() - Game.HEIGHT/ 2, 0, World.mapHeight * World.TILE_SIZE - Game.HEIGHT);
    }
}
