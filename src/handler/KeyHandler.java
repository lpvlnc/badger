/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Game;
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
        
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
            player.up = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            if(player.energy > 0)
                player.isRunning = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            Game.restart = true;
        }
       
        if(e.getKeyCode() == KeyEvent.VK_H){
            showHitBox = !showHitBox;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_F)
            showFps = !showFps;
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
            player.isRunning = false;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            player.useSteroid();
        }
    }
}
