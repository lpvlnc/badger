package main;

import audio.Audio;
import audio.AudioPlayer;
import entities.Entity;
import entities.Player;
import graphics.Spritesheet;
import graphics.UI;
import handlers.KeyHandler;
import menu.Menu;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import static world.World.TILE_SIZE;

public class Game extends Canvas implements Runnable {
    private Thread thread;
    private boolean isRunning;
    public static KeyHandler keyHandler;
    AudioPlayer musicPlayer = new AudioPlayer();

    // Graphics
    public static JFrame window;
    public final static int WIDTH = 480;
    public final static int HEIGHT = 320;
    public final static int SCALE =  1;
    private BufferedImage backgroundImage;
    public static Spritesheet spritesheet;
    public static World world;
    public static Menu menu;
    public static GameDimensions gameDimensions;

    // Configuration
    public static boolean showFps = false;
    public static boolean showHitBox = false;

    // UI
    public static UI ui;
    public static InputStream inputStream;

    // Entities
    public static ArrayList<Entity> entities;
    public static Player player = null;

    // Level 1 attributes
    public static int level1Score;
    public static int level1Lives;
    public static int level1Energy;
    public static int level1Steroid;
    public static boolean level1Detector;
    public static int level = 1;
    public int maxLevel = 2;

    public enum State {
        MENU, PAUSE, NORMAL, GAMEOVER, END
    }
    public static State state = State.MENU;

    public Game() throws IOException, FontFormatException {
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        initializeWindow();
        spritesheet = new Spritesheet();
        entities = new ArrayList<>();
        world = new World(level);
        inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("font/prstart.ttf");
        ui = new UI();
        menu = new Menu();
        AudioPlayer.playMusic(Audio.MENU_MUSIC);
        gameDimensions = new GameDimensions();
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        Game game = new Game();
        game.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() throws InterruptedException {
        isRunning = false;
        thread.join();
    }

    public static void restart() throws IOException, InterruptedException {
        changeGameState(State.NORMAL);
        entities.clear();

        if (level == 1)
            player = null;
        else {
            Game.player = new Player(0, 0, TILE_SIZE, TILE_SIZE, null);
            player.life = level1Lives;
            player.energy = level1Energy;
            player.score = level1Score;
            player.steroidCounter = level1Steroid;
            player.hasCrown = true;
            player.hasDetector = level1Detector;
        }
        world = new World(level);
    }

    private void initializeWindow() {
        window = new JFrame("Badger");
        window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.add(this);
        window.setResizable(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
    }

    public static void nextLevel() throws IOException, InterruptedException {
        level1Lives = player.life;
        level1Energy = player.energy;
        level1Score = player.score;
        level1Steroid = player.steroidCounter;
        level1Detector = player.hasDetector;
        entities.clear();
        level++;
        player.direction = Entity.Direction.UP;
        changeGameState(State.NORMAL);
        world = new World(level);
    }

    public static void changeGameState(State state) throws InterruptedException {
        switch (state) {
            case MENU -> {
                AudioPlayer.stop();
                AudioPlayer.playMusic(Audio.MENU_MUSIC);
                menu.loadMainMenuOptions();
                Game.state = state;
            }
            case PAUSE -> {
                AudioPlayer.pause();
                menu.loadPauseMenuOptions();
                Game.state = state;
            }
            case NORMAL -> {
                AudioPlayer.stop();
                if (Game.level == 1)
                    AudioPlayer.playMusic(Audio.LEVEL1_MUSIC);
                else
                    AudioPlayer.playMusic(Audio.LEVEL2_MUSIC);
                Game.state = state;
            }
            case GAMEOVER, END -> {
                AudioPlayer.stop();
                menu.loadGameOverEndMenuOptions();
                Game.state = state;
            }
        }
    }

    public void update() throws IOException, InterruptedException {
        gameDimensions = new GameDimensions();
        if (Objects.requireNonNull(state) == State.NORMAL) {
            world.update();
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = entities.get(i);
                entity.update();
            }

            if (player.chocolateCounter == 100) {
                level++;
                if (level > maxLevel)
                    level = 1;
            }
        } else
            menu.update();
    }

    public void render() {
        backgroundImage =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics graphics = backgroundImage.getGraphics();
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        world.renderFloor(graphics);
        entities.sort(Entity.nodeSorter);
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.render(graphics);
            if (showHitBox)
                entity.showHitBox(graphics);
        }
        world.renderWall(graphics);
        graphics.dispose();
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, window.getWidth(), window.getHeight());
        graphics.drawImage(backgroundImage, gameDimensions.getX(), gameDimensions.getY(), gameDimensions.getWidth(), gameDimensions.getHeight(), null);
        if(level == 2) {
            graphics.setColor(new Color(0, 0, 0, 50));
            graphics.fillRect(0, 0, window.getWidth(), window.getHeight());
        }
        menu.render(graphics);
        ui.render(graphics);
        bufferStrategy.show();
    }

    @Override
    public void run() {
        requestFocus();
        long lastTime = System.nanoTime();
        double amountOfUpdates = 60.0;
        double ns = 1000000000 / amountOfUpdates;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                try {
                    update();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000) {
                if(showFps)
                    UI.frames = frames;
                frames = 0;
                timer = System.currentTimeMillis();
            }
        }
    }
}