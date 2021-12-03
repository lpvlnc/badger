/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.particle.Particle;
import entity.particle.PoisonedParticle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Game;
import main.Game.State;
import sound.AudioPlayer;
import sound.Sound;
import sound.Volume;
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
    public BufferedImage[] playerCrownUp;
    public BufferedImage[] playerCrownLeft;
    public BufferedImage[] playerCrownDown;
    public BufferedImage[] playerCrownRight;
    public BufferedImage[] playerCrownUpDamaged;
    public BufferedImage[] playerCrownLeftDamaged;
    public BufferedImage[] playerCrownDownDamaged;
    public BufferedImage[] playerCrownRightDamaged;

    // movement animation logic
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public int index = 0;
    public int maxIndex = 6;
    public int frames = 0;
    public int defaultMaxFrames = 5;
    public int steroidMaxFrames =  3;
    public int weakMaxFrames = 7;
    public int maxFrames = defaultMaxFrames;
    public boolean maxFramesChanged = false;
    
    
    // damage animation logic
    public boolean damaged = false;
    public boolean canBeDamaged = true;
    public int damagedFrames = 0;
    public int maxDamagedFrames = 60;
    public int damageReceived = 0;

    // general attributes
    public int maxLife = 5;
    public int life = maxLife;
    public int defaultSpeed = 2;
    public int runningSpeed = 4;
    public boolean dead = false;
    public int score = 0;
    public int highScore = 0;
    public int chocolateCounter = 0;
    public boolean isRunning = false;
    public int maxEnergy = 100;
    public int energy = maxEnergy;
    public int parchmentCounter = 0;
    public boolean hasCrown = false;
    public boolean xRay;
    public boolean action = false;
    
    public int energyFrames = 0;
    public int energyMaxFrames = 60;
    
    // on steroid attributes
    public int steroidCounter = 0;
    public boolean onSteroid = false;
    public int steroidMaxTime = 400;
    public int steroidTime = 0;
    
    // running attributes
    public int runningMaxFrames = 20;
    public int runningFrames = 0;
    public Particle runningParticle;
    
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
        speed = defaultSpeed;
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
        
        playerCrownUp = new BufferedImage[4];
        playerCrownLeft = new BufferedImage[6];
        playerCrownDown = new BufferedImage[4];
        playerCrownRight = new BufferedImage[6];
        playerCrownUpDamaged = new BufferedImage[4];
        playerCrownLeftDamaged = new BufferedImage[6];
        playerCrownDownDamaged = new BufferedImage[4];
        playerCrownRightDamaged = new BufferedImage[6];
        
        for (int i = 0; i < 4; i++) {
            playerUp[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 6; i++) {
            playerLeft[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 34, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        for (int i = 0; i < 4; i++){
            playerDown[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 66, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for(int i = 0; i < 6; i++){
            playerRight[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 98, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        for (int i = 0; i < 4; i++) {
            playerUpDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 128, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 6; i++) {
            playerLeftDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 162, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        for (int i = 0; i < 4; i++){
            playerDownDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 194, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for(int i = 0; i < 6; i++){
            playerRightDamaged[i] = Game.spritesheet.getSprite(i * World.TILE_SIZE, 226, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        for (int i = 0; i < 4; i++) {
            playerCrownUp[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 0, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 6; i++) {
            playerCrownLeft[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 34, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        for (int i = 0; i < 4; i++){
            playerCrownDown[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 66, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for(int i = 0; i < 6; i++){
            playerCrownRight[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 98, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        for (int i = 0; i < 4; i++) {
            playerCrownUpDamaged[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 128, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for (int i = 0; i < 6; i++) {
            playerCrownLeftDamaged[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 162, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        for (int i = 0; i < 4; i++){
            playerCrownDownDamaged[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 194, World.TILE_SIZE, World.TILE_SIZE);
        }
        
        for(int i = 0; i < 6; i++){
            playerCrownRightDamaged[i] = Game.spritesheet.getSprite(192 + (i * World.TILE_SIZE), 226, World.TILE_SIZE, World.TILE_SIZE - 2);
        }
        
        poisonedParticle = null;
        runningParticle = null;
    }
    
    public void addPlayer(){
        Game.player = new Player(0, 0, 16, 16, null);
        Game.entities.add(Game.player);
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
        if(frames >= maxFrames){
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
            AudioPlayer.play(Sound.weak, Volume.NORMAL);
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
            poisonedParticle = null;
            weak = false;
            weakTime = 0;
            speed = defaultSpeed;
            maxFramesChanged = false;
            maxFrames = defaultMaxFrames;
        }
    }
    
    public void useSteroid(){
        if(steroidCounter > 0 && !onSteroid && !weak){
            AudioPlayer.play(Sound.use_steroid, Volume.NORMAL);
            steroidCounter--;
            startRunning();
            onSteroid = true;
        }
    }

    public void startRunning(){
        energyFrames = 0;
        isRunning = true;
        maxFrames = steroidMaxFrames;
        speed = runningSpeed;
    }
    
    public void stopRunning(){
        energyFrames = 0;
        isRunning = false;
        frames = 0;
        maxFrames = defaultMaxFrames;
        speed = defaultSpeed;
    }
    
    public void steroid() {
        canBeDamaged = false;
        steroidTime++;
        if(steroidTime == steroidMaxTime) {
            onSteroid = false;
            steroidTime = 0;
            stopRunning();
            weak = true;
        }
    }
    
    public void takeDamage(int damage) {
        if(canBeDamaged){
            AudioPlayer.play(Sound.player_hurt, Volume.NORMAL);
            if(weak) {
                life = 0;
                AudioPlayer.play(Sound.game_over, Volume.NORMAL);
                try {
                    Game.changeGameState(State.GAMEOVER);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                life -= damage;
                canBeDamaged = false;
            }
        }
    }
    
    public void damage() {
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
            AudioPlayer.play(Sound.game_over, Volume.NORMAL);
            try {
                Game.changeGameState(State.GAMEOVER);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void renderPlayerUp(Graphics g) {
        if(hasCrown){
            if(canBeDamaged || onSteroid) {
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerCrownUp[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerCrownUp[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerCrownUpDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerCrownUpDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        } else {
            if(canBeDamaged || onSteroid) {
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerUp[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerUp[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerUpDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerUpDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        }
        
    }
    
    public void renderPlayerLeft(Graphics g) {
        if(hasCrown) {
            if(canBeDamaged || onSteroid){
                if(isMoving)
                    g.drawImage(playerCrownLeft[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerCrownLeft[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving)
                    g.drawImage(playerCrownLeftDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerCrownLeftDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        } else {
            if(canBeDamaged || onSteroid){
                if(isMoving)
                    g.drawImage(playerLeft[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerLeft[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving)
                    g.drawImage(playerLeftDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerLeftDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        }
    }
    
    public void renderPlayerDown(Graphics g) {
        if(hasCrown){
            if(canBeDamaged  || onSteroid){
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerCrownDown[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerCrownDown[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerCrownDownDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerCrownDownDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        } else {
            if(canBeDamaged  || onSteroid){
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerDown[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerDown[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving) {
                    if (index > 3)
                        index = 0;
                    g.drawImage(playerDownDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                } else
                    g.drawImage(playerDownDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        }
    }
    
    public void renderPlayerRight(Graphics g) {
        if(hasCrown){
            if(canBeDamaged || onSteroid){
                if(isMoving)
                    g.drawImage(playerCrownRight[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerCrownRight[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving)
                    g.drawImage(playerCrownRightDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerCrownRightDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        } else {
            if(canBeDamaged || onSteroid){
                if(isMoving)
                    g.drawImage(playerRight[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerRight[0], getX() - Camera.x, getY() - Camera.y, null);
            } else {
                if(isMoving)
                    g.drawImage(playerRightDamaged[index], getX() - Camera.x, getY() - Camera.y, null);
                else
                    g.drawImage(playerRightDamaged[0], getX() - Camera.x, getY() - Camera.y, null);
            }
        }
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
        
        if(isRunning && !weak && energy > 0) {
            if(!onSteroid){
                if(energy <= 0) {
                    stopRunning();
                    return;
                }
                runningFrames++;
                if(runningFrames == runningMaxFrames) {
                    runningFrames = 0;
                    energy-=10;
                    if(energy <= 0) {
                        stopRunning();
                    }
                }
            }
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
