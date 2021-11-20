package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import main.Game;
import world.Camera;
import world.World;

public class Panda extends Entity{ 

    public BufferedImage[] pandaLeft;
    public BufferedImage[] pandaRight;
    
    public boolean up;
    public boolean right;
    public int speed = 1;
    public int frames = 0;
    public int maxFrames = 15;
    public int index = 0;
    public int maxIndex = 3;
    private int initialY;
    
    public Panda(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        pandaLeft = new BufferedImage[3];
        for(int i = 0; i < 3; i++) {
            pandaLeft[i] = Game.spritesheet.getSprite(96 + (i * World.TILE_SIZE), 288, 32, 32);
        }
        
        pandaRight = new BufferedImage[3];
        for(int i = 0; i < 3; i++) {
            pandaRight[i] = Game.spritesheet.getSprite(0 + (i * World.TILE_SIZE), 288, 32, 32);
        }
        
        if(getY() <= 64)
            setY(65);
        
        initialY = getY();
        right = ThreadLocalRandom.current().nextInt(0, 2) != 0;
        setMask(6, 2, 20, 30);
    }
    
    @Override
    public void update() {
        if(getX() <= 0 || !World.isFreeDynamic(getX() + xMask - speed, getY() + yMask, wMask, hMask))
            right = true;
        
        if(getX() >= World.mapWidth * World.TILE_SIZE - World.TILE_SIZE - 2 || !World.isFreeDynamic(getX() + xMask + speed, getY() + yMask, wMask, hMask))
            right = false;
        
        if(getY() <= 0 || !World.isFreeDynamic(getX() + xMask, getY() + yMask - speed, wMask, hMask)) {
            up = false;
            initialY = getY() + (2*World.TILE_SIZE);
        }  
        
        if(getY() + World.TILE_SIZE > World.mapHeight * World.TILE_SIZE - 12 || !World.isFreeDynamic(getX() + xMask, getY() + yMask + speed, wMask, hMask))
            up = true;
        
        if(right)
            x += speed;
        else
            x -= speed;
        if(up) {
            if(y >= initialY - (2 * World.TILE_SIZE))
                y-=speed;
            else
                up = false;
        } else {
            if(y <= initialY)
                y+=speed;
            else
                up = true;
        }
        
                
        frames++;
        if(frames >= maxFrames){
            frames = 0;
            index++;
            if(index == maxIndex)
            {
                index = 0;
            }
        }
    }
    
    @Override
    public void render(Graphics g){
        if(right) {
            setMask(7, 2, 20, 30);
            g.drawImage(pandaRight[index], getX() - Camera.x, getY() - Camera.y, null);
        } else {
            setMask(6, 2, 20, 30);
            g.drawImage(pandaLeft[index], getX() - Camera.x, getY() - Camera.y, null);
        }
    }
}    
    
    
    

