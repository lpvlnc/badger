
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
import sound.AudioPlayer;
import sound.Sound;

/**
 *
 * @author Leonardo
 */
public class MenuEnd extends Menu {
    
    public MenuEnd(){
        options = new ArrayList<>();
    	options.add("Restart");
    	options.add("Main menu");
    	options.add("Exit");
    	maxOption = options.size() - 1;
    }
    
    public void update(){
        if(up){
            up = false;
            currentOption--;
            AudioPlayer.play(Sound.menu_switch, menuVolume);
            if(currentOption < 0){
                currentOption = maxOption;
            }
        }
        
        if(down){
            down = false;
            currentOption++;
            AudioPlayer.play(Sound.menu_switch, menuVolume);
            if(currentOption > maxOption){
                currentOption = 0;
            }
        }
        
        if(select) {
            select = false;
            AudioPlayer.play(Sound.menu_select, menuVolume);
            if(options.get(currentOption).contentEquals("Restart")) {
                try {
                    Game.level=1;
                    Game.restart();
                } catch (IOException ex) {
                    Logger.getLogger(MenuGameOver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(options.get(currentOption).contentEquals("Main menu")) {
                try {
                    Game.level=1; 
                    Game.changeGameState(State.MENU);
                } catch (InterruptedException ex) {
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
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(-756, -19, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        g2.setColor(new Color(250, 0, 250));
        Game.ui.drawTextCenter("END GAME", heightPos - 90, new Color(250, 0, 0));
        for(int i = 0; i < options.size(); i++) {
            if(i == currentOption)
                Game.ui.drawTextCenter(options.get(i), heightPos - heightOffSet + (i * 30), null);
                //Game.ui.drawText(options.get(i), widthPos + 35, heightPos - heightOffSet + (i * 30), null);
            else
                Game.ui.drawTextCenter(options.get(i), heightPos - heightOffSet + (i * 30), new Color(100, 100, 100));
                //Game.ui.drawText(options.get(i), widthPos + 35, heightPos - heightOffSet + (i * 30), new Color(100, 100, 100));
        }
    }
}
