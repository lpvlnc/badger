/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.World;

/**
 *
 * @author Leonardo
 */
public class UI {
    public static BufferedImage heart;
    public static boolean updateFps;
    public static int frames = 0;
    
    public UI(){
        heart = Game.spritesheet.getSprite(608, 0, 15, 13);
    }
    
    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.setFont(new Font("arial", Font.BOLD, 10));
        
        if(Game.showFps)
            g.drawString("FPS: " + frames, Game.WIDTH - 41, 10);
        
        g.setColor(Color.white);
        g.drawString("SCORE: " + Game.player.score, 100, 10);
        g.setColor(Color.white);
        g.drawString("LIFE: ", 0, 10);
        g.drawImage(heart, 30, 0, null);
    }
}
