/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Game;
import main.Game.State;

/**
 *
 * @author Leonardo
 */
public class MenuMain extends Menu {
    
    public MenuMain(){
        options = new ArrayList<>();
    	options.add("Start game");
    	options.add("Exit");
    	maxOption = options.size() - 1;
    }
    
    public void update(){
        if(up){
            up = false;
            currentOption--;
            if(currentOption < 0){
                currentOption = maxOption;
            }
        }
        
        if(down){
            down = false;
            currentOption++;
            if(currentOption > maxOption){
                currentOption = 0;
            }
        }
        
        if(select) {
            select = false;
            if(options.get(currentOption).contentEquals("Start game")) {
                try {
                    Game.restart();
                } catch (IOException ex) {
                    Logger.getLogger(MenuGameOver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if(options.get(currentOption).contentEquals("Exit")) {
                System.exit(0);
            }
        }
    }
    
    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(0, 0, 0, 255));
        g2.fillRect(-756, -19, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        g2.setColor(new Color(250, 0, 250));
        for(int i = 0; i < options.size(); i++) {
            if(i == currentOption)
                Game.ui.drawTextCenter(options.get(i),heightPos - heightOffSet + (i * 30), Color.yellow);
                //Game.ui.drawText(options.get(i), widthPos + 35, heightPos - heightOffSet + (i * 30), null);
            else
                Game.ui.drawTextCenter(options.get(i), heightPos - heightOffSet + (i * 30), new Color(100, 100, 100));
                //Game.ui.drawText(options.get(i), widthPos + 35, heightPos - heightOffSet + (i * 30), new Color(100, 100, 100));
        }
    }
}
