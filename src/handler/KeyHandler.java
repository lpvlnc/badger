/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Game;
import main.Game.State;
import static main.Game.player;
import static main.Game.showFps;
import static main.Game.showHitBox;

/**
 *
 * @author Leonardo
 */
public class KeyHandler implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        switch(Game.state) {
            case NORMAL:
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
                    player.up = true;

                if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
                    player.left = true;

                if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
                    player.down = true;

                if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
                    player.right = true;

                if(e.getKeyCode() == KeyEvent.VK_SHIFT)
                    if(player.energy > 0 && player.isMoving)
                        player.startRunning();

                if(e.getKeyCode() == KeyEvent.VK_H)
                    showHitBox = !showHitBox;

                if(e.getKeyCode() == KeyEvent.VK_F)
                    showFps = !showFps;

                if(e.getKeyCode() == KeyEvent.VK_CONTROL)
                    player.useSteroid();

                if(e.getKeyCode() == KeyEvent.VK_SPACE && Game.player.hasCrown)
                    player.xRay = !player.xRay;
                
                if(e.getKeyCode() == KeyEvent.VK_E && Game.player.hasDetector)
                    player.detecting = !player.detecting;
                
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    player.action = true;
                
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    try {
                        Game.changeGameState(State.PAUSE);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(KeyHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
                    Game.menuGameOver.up = true;
                
                if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
                    Game.menuGameOver.down = true;
                
                if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Game.menuGameOver.select = true;
                }
                break;
                
            case MENU:
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
                    Game.menuMain.up = true;
                
                if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
                    Game.menuMain.down = true;
                
                if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Game.menuMain.select = true;
                }
                break;
                
            case PAUSE:
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
                    Game.menuPause.up = true;
                
                if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
                    Game.menuPause.down = true;
                
                if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Game.menuPause.select = true;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         switch(Game.state) {
            case NORMAL:
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
                    player.up = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
                    player.left = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
                    player.down = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
                    player.right = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_SHIFT){
                    player.stopRunning();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    player.action = false;
            break;
        }
    }
}
