package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

public class Panda extends Entity{ 

    public Panda(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
    
    @Override
    public void update() {
        if (isColliding(this, Game.player)) {

        }
    }
    
    
    
        public void render(Graphics g){
        
    }

}    
    
    
    

