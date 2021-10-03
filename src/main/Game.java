/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 *
 * @author Leonardo
 */
public class Game extends Canvas implements Runnable {
    
    // Game loop
    private Thread thread;
    private boolean isRunning;
    
    // Window
    private final InitFrame frame;
    public final static int WIDTH = 480;
    public final static int HEIGHT = 320;
    public final  static int SCALE =  3;
    
    // Graphics
    private final BufferedImage image;
    
    // Constructor
    public Game(){
        frame = new InitFrame(this);
        image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
    
    public synchronized void stop() throws InterruptedException{
        isRunning = false;
        thread.join();
    }
    
    public void restart(){
        
    }
    
    public void update(){
        
    }
    
    public void render(){
        BufferStrategy bs =  getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 100, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        /* Game render start */
        
        /* Game render end */
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bs.show();
    }
    
    @Override
    public void run(){
        requestFocus();
        long lastTime = System.nanoTime();
        double amountOfUpdates = 60.0;
        double ns = 1000000000 / amountOfUpdates;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                update();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println(frames);
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }
    }
}
