/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import main.Game;
import world.Camera;
import world.World;
/**
 *
 * @author thaia
 */
public class Dog extends Entity {

    public BufferedImage dog;
    public boolean up;

    public Dog(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        dog = Game.spritesheet.getSprite(0, 320, World.TILE_SIZE, World.TILE_SIZE);
        setMask(9, 10, 13, 20);
        up = ThreadLocalRandom.current().nextInt(0, 2) != 0;
    }

    public void update() {
        if(getY() <= 0 || !World.isFreeDynamic(getX() + xMask, getY() + yMask - speed, wMask, hMask))
            up = false;
        
        if(getY() >= World.mapWidth * World.TILE_SIZE - World.TILE_SIZE || !World.isFreeDynamic(getX() + xMask, getY() + yMask + speed, wMask, hMask))
            up = true;
            
        if(up)
            y -= speed;
        else
            y += speed;
        
        if(getY() + hMask <= 0)
            up = false;
        
        if(getY() + World.TILE_SIZE > World.mapHeight * World.TILE_SIZE - hMask)
            up = true;
    }

    public void render (Graphics g) {
        g.drawImage(dog, getX() - Camera.x, getY() - Camera.y, null);
    }
}
