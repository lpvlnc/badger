/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.particle.PoisonedParticle;
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
    // sprites
    public BufferedImage[] playerUp;
    public BufferedImage[] playerLeft;
    public BufferedImage[] playerDown;
    public BufferedImage[] playerRight;
    public BufferedImage[] playerUpDamaged;
    public BufferedImage[] playerLeftDamaged;
    public BufferedImage[] playerDownDamaged;
    public BufferedImage[] playerRightDamaged;

    // movement animation logic
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean isMoving = false;
    public int index = 0;
    public int maxIndex = 6;
    public int frames = 0;
    public int defaultMaxFrames = 5;
    public int steroidMaxFrames =  3;
    public int weakMaxFrames = 7;
    public int maxFrames = defaultMaxFrames;
    public boolean maxFramesChanged = false;
    
    
    // damage animation logic
    public boolean damaged = true;
    public boolean canBeDamaged = true;
    public int damagedFrames = 0;
    public int maxDamagedFrames = 60;

    // general attributes
    public int maxLife = 2;
    public int life = maxLife;
    public int defaultSpeed = 2;
    public int runningSpeed = 4;
    public int speed = defaultSpeed;
    public boolean dead = false;
    public int score = 0;
    public int highScore = 0;
    public int chocolateCounter = 0;
    public boolean isRunning = false;
    public int maxEnergy = 100;
    public int energy = maxEnergy;
    public int parchmentCounter = 0;
    
    public int energyFrames = 0;
    public int energyMaxFrames = 240;
    
    // on steroid attributes
    public int steroidCounter = 0;
    public boolean onSteroid = false;
    public int steroidMaxTime = 400;
    public int steroidTime = 0;
    
    // running attributes
    public int runningMaxFrames = 10;
    public int runningFrames = 0;
    
    // weak attributes
    public PoisonedParticle poisonedParticle;
    public boolean weak = false;
    public int weakSpeed = 1;
    public int weakMaxTime = steroidMaxTime;
    public int weakTime = 0;
    
    public Player(double x,double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(0);
        setMask(8, 6, 24, 17);
        initSprites();
    }
    
    private void initSprites() {
        playerUp = new BufferedImage[4];
        playerLeft = new BufferedImage[6];
        playerDown = new BufferedImage[4];
        playerRight = new BufferedImage[6];
        playerUpDamaged = new BufferedImage[4];
        playerLeftDamaged = new BufferedImage[6];
        playerDownDamaged = new BufferedImage[4];
        playerRightDamaged = new BufferedImage[6];
        
        for (int i = 0; i < 4; i++) {
            playerUp[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 6; i++) {
            playerLeft[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 34, World.TILE_SIZE, World.TILE_SIZE -2);
        }
        
        for (int i = 0; i < 4; i++){
            playerDown[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 66, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for(int i = 0; i < 6; i++){
            playerRight[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 98, World.TILE_SIZE, World.TILE_SIZE -2);
        }
        
        for (int i = 0; i < 4; i++) {
            playerUpDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 128, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 6; i++) {
            playerLeftDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 160, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 4; i++){
            playerDownDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 192, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for(int i = 0; i < 6; i++){
            playerRightDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 224, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        poisonedParticle = null;
    }
    
    public void movement() {
        if(getX() <= 0)
            setX(0);
        
        if(getX() >= World.mapWidth * World.TILE_SIZE - World.TILE_SIZE - 2)
            setX(World.mapWidth * World.TILE_SIZE - World.TILE_SIZE - 2);
        
        if(getY() <= 0)
            setY(0);
        
        if(getY() + World.TILE_SIZE > World.mapHeight * World.TILE_SIZE - 12 )
            setY(World.mapHeight * World.TILE_SIZE - World.TILE_SIZE - 12);
        
        isMoving = false;
        
        if(up) {
            this.direction = Direction.UP;
            setMask(9, 0, 13, 28);
            if(World.isFreeDynamic(getX() + xMask, getY() + yMask - speed, wMask, hMask)) {
                isMoving = true;
                y -= speed;
            }
        }
        
        if(down) {
            this.direction = Direction.DOWN;
            setMask(10, 1, 14, 29);
            if(World.isFreeDynamic(getX() + xMask, getY() + yMask + speed, wMask, hMask)){
                isMoving = true;
                y += speed;
            }
        } 
        
        if(left) {
            this.direction = Direction.LEFT;
            setMask(0, 6, 24, 17);
            if(World.isFreeDynamic(getX() + xMask - speed, getY() + yMask, wMask, hMask)) {
                isMoving = true;
                x -= speed;
            }
        }
        
        
        if(right) {
            this.direction = Direction.RIGHT;
            setMask(8, 6, 24, 17);
            if(World.isFreeDynamic(getX() + xMask + speed, getY() + yMask, wMask, hMask)) {
                isMoving = true;
                x += speed;
            }
        }
        
        if(right&left) {
            this.direction = Direction.RIGHT;
            setMask(8, 6, 24, 17);
            if(World.isFreeDynamic(getX() + xMask + speed, getY() + yMask, wMask, hMask)) {
                isMoving = true;
                x += speed;
            }
        }
    
        if(down&up){
            this.direction = Direction.DOWN;
            setMask(10, 1, 14, 29);
            if(World.isFreeDynamic(getX() + xMask, getY() + yMask + speed, wMask, hMask)){
                isMoving = true;
                y += speed;
            }
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
    
    public void updateCamera(){
        Camera.x = Camera.clamp(getX() - Game.WIDTH/ 2, 0, World.mapWidth * World.TILE_SIZE - Game.WIDTH);
        Camera.y = Camera.clamp(getY() - Game.HEIGHT/ 2, 0, World.mapHeight * World.TILE_SIZE - Game.HEIGHT);
    }
    
    public void weak() {
        
        if(poisonedParticle == null) {
            poisonedParticle = new PoisonedParticle(this.getX(), this.getY(), World.TILE_SIZE, World.TILE_SIZE, null);
            Game.entities.add(poisonedParticle);
        }
        
        maxFrames = weakMaxFrames;
        if(maxFramesChanged == false) {
            frames = 0;
            maxFramesChanged = true;
        }
        
        canBeDamaged = true;
        speed = weakSpeed;
        weakTime++;
        if(weakTime == weakMaxTime) {
            Game.entities.remove(poisonedParticle);
            weak = false;
            weakTime = 0;
            speed = defaultSpeed;
            maxFramesChanged = false;
            maxFrames = defaultMaxFrames;
        }
    }
    
    public void useSteroid(){
        if(steroidCounter > 0 && !onSteroid && !weak){
            steroidCounter--;
            onSteroid = true;
        }
    }
    
    public void running(){
        if (energy <= 0) {
            isRunning = false;
            frames = 0;
            return;
        }
        if(!onSteroid){
            runningFrames++;
            if(runningFrames == runningMaxFrames) {
                runningFrames = 0;
                energy-=10;
                if(energy <= 0) {
                    isRunning = false;
                    maxFrames = defaultMaxFrames;
                    maxFramesChanged = false;
                    return;
                }
            }
        }
            
        maxFrames = steroidMaxFrames;
        if(maxFramesChanged == false) {
            frames = 0;
            maxFramesChanged = true;
        }
        speed = runningSpeed;
    }
    
    public void steroid() {
        isRunning = true;
        running();
        canBeDamaged = false;
        steroidTime++;
        if(steroidTime == steroidMaxTime) {
            onSteroid = false;
            steroidTime = 0;
            speed = defaultSpeed;
            weak = true;
            isRunning = false;
            maxFramesChanged = false;
        }
    }
    
    public void damage() {
        
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
    }
    
    public void renderPlayerUp(Graphics g) {
        if(isMoving) {
            if (index > 3)
                index = 0;
            g.drawImage(playerUp[index], getX() - Camera.x, getY() - Camera.y, null);
        } else
            g.drawImage(playerUp[0], getX() - Camera.x, getY() - Camera.y, null);
    }
    
    public void renderPlayerLeft(Graphics g) {
        if(isMoving)
            g.drawImage(playerLeft[index], getX() - Camera.x, getY() - Camera.y, null);
        else
            g.drawImage(playerLeft[0], getX() - Camera.x, getY() - Camera.y, null);
    }
    
    public void renderPlayerDown(Graphics g) {
        if(isMoving) {
            if (index > 3)
                index = 0;
            g.drawImage(playerDown[index], getX() - Camera.x, getY() - Camera.y, null);
        } else
            g.drawImage(playerDown[0], getX() - Camera.x, getY() - Camera.y, null);
    }
    
    public void renderPlayerRight(Graphics g) {
        if(isMoving)
            g.drawImage(playerRight[index], getX() - Camera.x, getY() - Camera.y, null);
        else
            g.drawImage(playerRight[0], getX() - Camera.x, getY() - Camera.y, null);
    }
    
    @Override
    public void update(){
        if(energy < maxEnergy && !isRunning && !weak && !onSteroid)
        {
            energyFrames++;
            if(energyFrames == energyMaxFrames)
            {
                energy+=10;
                energyFrames = 0;
            }
        }
        
        if(score > highScore){
            highScore = score;
        }
        if(isRunning && !weak && energy > 0)
            running();
        else {
            speed = defaultSpeed;
            maxFrames = defaultMaxFrames;
        }
        
        if(onSteroid) {
            steroid();
        }else {
            damage();
        }
        
        if(weak) {
            weak();
        }
        
        movement();
        animation();
        updateCamera();
    }
    
    @Override
    public void render(Graphics g) {
        if(this.direction == Direction.UP)
            renderPlayerUp(g);
        
        if(this.direction == Direction.LEFT)
            renderPlayerLeft(g);
            
        if(this.direction == Direction.DOWN) {
            renderPlayerDown(g);
        }
        
        if(this.direction == Direction.RIGHT) {
            renderPlayerRight(g);
        }
    }
}
