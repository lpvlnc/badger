/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.awt.Graphics;
import java.util.ArrayList;
import main.Game;

/**
 *
 * @author Leonardo
 */
public class Menu {
    public ArrayList<String> options;
    public int currentOption = 0;
    public int maxOption;
    public boolean up, down;
    public boolean select = false;
    public int widthPos = ((Game.WIDTH * Game.SCALE) / 2) - 80;
    public int heightPos = ((Game.HEIGHT * Game.SCALE) / 2);
    public int heightOffSet = 60;
    public int arrowPos = ((Game.HEIGHT * Game.SCALE) / 2) + 30;
    public int arrowOffSet = 60;
    
    public void update() {
        
    }
    
    public void render(Graphics g) {
        
    }
}
