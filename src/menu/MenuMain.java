/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import main.Game;

/**
 *
 * @author Leonardo
 */
public class MenuMain extends Menu {
    public MenuMain() {
        options = new ArrayList<>();
    	options.add("New game");
    	options.add("Load game");
    	options.add("Exit");
    	maxOption = options.size() - 1;
    }
    
    public void update() {
        if(up){
            up = false;
            currentOption--;
            if(currentOption < 0){
                currentOption = maxOption;
                arrowOffSet = 100 - (maxOption * 30);
            }
        }
        
        if(down){
            down = false;
            currentOption++;
            if(currentOption > maxOption){
                currentOption = 0;
                arrowOffSet = 100;
            }
        }
        
        arrowOffSet = 100 - (currentOption * 30);
        
        if(select) {
            select = false;
            if(options.get(currentOption).contentEquals("New game")) {
                try {
                    Game.restart();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if(options.get(currentOption).contentEquals("Load game")) {
                
            }

            if(options.get(currentOption).contentEquals("Exit")) {
                System.exit(0);
            }
        }
    }
    
    public void render(Graphics g) {
        //drawText("Novo jogo", 100, 100);
        g.drawString("Game Name", ((Game.WIDTH * Game.SCALE) / 2) - 100, ((Game.HEIGHT * Game.SCALE) / 2) - 200);
        
        for(int i = 0; i < options.size(); i++) {
            g.drawString(options.get(i), widthPos, heightPos - heightOffSet + (i * 30));
        }
        
        g.drawString(">", ((Game.WIDTH * Game.SCALE) / 2) - 100, arrowPos - arrowOffSet);
    }
}
