/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entity.Entity;
import entity.Player;
import graphic.Spritesheet;
import graphic.UI;
import handler.KeyHandler;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import world.World;

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
    public static Spritesheet spritesheet;
    public static World world;
    
    public static boolean gameOver = false;
    public static boolean restart = false;
    
    //Config
    public static boolean showFps = false;
    public static boolean showHitBox = false;
    
    // KEYLISTENER 
    public static KeyHandler kh;
    
    //UI
    public static UI ui;
    
    // Entities
    public static Player player;
    
    // Lists
    public static ArrayList<Entity> entities;
    
    // Constructor
    public Game() throws IOException{
        kh = new KeyHandler();
        addKeyListener(kh);
        
        frame = new InitFrame(this);
        image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        // initializing objects start //
        spritesheet = new Spritesheet("/spritesheet/spritesheet.png");
        entities = new ArrayList<>();
        
        //initialize entity
        player = new Player(0, 0, 16, 16, null);
        entities.add(player);
        
        world = new World("/map/level1.png");
        ui = new UI();
        // initializing objects end //
    }
    
    public static void main(String[] args) throws IOException {
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
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.update();
        }
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
        world.render(g);
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.render(g);
        }
        
        ui.render(g);
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
                if(showFps)
                    UI.frames = frames;
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }
    }
}