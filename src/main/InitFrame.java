/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Dimension;
import javax.swing.JFrame;
import static main.Game.WIDTH;
import static main.Game.HEIGHT;
import static main.Game.SCALE;

/**
 *
 * @author Leonardo
 */
public class InitFrame {
    
    private JFrame frame;
    
    public InitFrame(Game game){
        frame = new JFrame("Game #1");
        frame.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        frame.add(game);
        frame.setResizable(false);
        frame.pack();

        // TODO: ADD WINDOW ICON
        /*
        Image window_icon = null;
        try {
            window_icon = ImageIO.read(getClass().getResource("/icons/window_icon.png"));

        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setIconImage(window_icon);
        */

        /* TODO: ADD CURSOR
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursor_icon = toolkit.getImage(getClass().getResource("/icons/cursor_icon.png"));
        Cursor c = toolkit.createCustomCursor(cursor_icon, new Point(0, 0), "img");
        //frame.setCursor(c);
        */
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
