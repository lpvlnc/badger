package graphics;

import audio.Audio;
import audio.AudioPlayer;
import main.Game;
import audio.Volume;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Menu {
    private ArrayList<String> options;
    private int currentOption = 0;
    private int maxOption;
    public boolean up, down;
    public boolean select = false;
    private final int heightPos = (Game.window.getHeight() / 2);
    private final int heightOffSet = 60;
    private final BufferedImage mainMenuBackground;

    public Menu() throws IOException {
        mainMenuBackground = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/main_menu_background.png")));
        loadMainMenuOptions();
    }

    public void loadMainMenuOptions() {
        options = new ArrayList<>();
        options.add("Start game");
        options.add("Exit");
        maxOption = options.size() - 1;
        currentOption = 0; // Adicione esta linha
    }

    public void loadPauseMenuOptions() {
        options = new ArrayList<>();
        options.add("Continue");
        options.add("Restart");
        options.add("New game");
        options.add("Main menu");
        options.add("Exit");
        maxOption = options.size() - 1;
    }

    public void loadGameOverEndMenuOptions() {
        options = new ArrayList<>();
        options.add("Restart");
        options.add("Main menu");
        options.add("Exit");
        maxOption = options.size() - 1;
    }

    public void update() throws IOException, InterruptedException {
        double menuVolume = Volume.NORMAL;
        if (up) {
            up = false;
            currentOption--;
            AudioPlayer.play(Audio.MENU_SWITCH, menuVolume);
            if(currentOption < 0)
                currentOption = maxOption;
        }

        if (down) {
            down = false;
            currentOption++;
            AudioPlayer.play(Audio.MENU_SWITCH, menuVolume);
            if(currentOption > maxOption)
                currentOption = 0;
        }

        if (select) {
            select = false;
            AudioPlayer.play(Audio.MENU_SELECT, menuVolume);
            if (currentOption > options.size())
                currentOption = 0;

            if(options.get(currentOption).contentEquals("Start game"))
                Game.restart();

            if(options.get(currentOption).contentEquals("Continue")) {
                AudioPlayer.resume();
                Game.state = Game.State.NORMAL;
            }

            if(options.get(currentOption).contentEquals("Main menu")) {
                Game.level=1;
                Game.changeGameState(Game.State.MENU);
            }

            if(options.get(currentOption).contentEquals("Restart"))
                Game.restart();

            if(options.get(currentOption).contentEquals("New game")) {
                Game.level=1;
                Game.restart();
            }

            if(options.get(currentOption).contentEquals("Exit"))
                System.exit(0);
        }
    }

    public void render(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D)graphics;
        if (Game.state != Game.State.NORMAL) {
            if (Game.state == Game.State.MENU) {
                graphics2d.drawImage(mainMenuBackground, Game.gameDimensions.getX(), Game.gameDimensions.getY(), Game.gameDimensions.getWidth(), Game.gameDimensions.getHeight(), null);
            }
            graphics2d.setColor(new Color(0, 0, 0, 150));
            graphics2d.fillRect(Game.gameDimensions.getX(), Game.gameDimensions.getY(), Game.gameDimensions.getWidth(), Game.gameDimensions.getHeight());
        }
        switch (Game.state) {
            case MENU -> renderMainMenu(graphics2d);
            case PAUSE -> renderPauseMenu(graphics2d);
            case GAMEOVER -> renderGameOverMenu(graphics2d);
            case END -> renderEndMenu(graphics2d);
        }
    }

    public int calculateMenuYStart() {
        int totalOptions = options.size() + 1;
        int menuSize = (totalOptions * UI.TEXT_SIZE) + (options.size() * UI.LINE_HEIGHT);
        return (Game.window.getHeight() / 2) - (menuSize / 2);
    }
    
    public void renderMainMenu(Graphics2D graphics2d) {

        int menuStart = calculateMenuYStart();
        Game.ui.drawTextCenter("MAIN MENU", menuStart, new Color(250, 0, 0));
        menuStart += (UI.LINE_HEIGHT);
        for (int i = 0; i < options.size(); i++)
            Game.ui.drawTextCenter(options.get(i),menuStart + (i * 30), i == currentOption ? null : new Color(100, 100, 100));
    }

    public void renderPauseMenu(Graphics2D graphics2d) {
        graphics2d.setColor(new Color(250, 0, 250));
//        Game.ui.drawTextCenter("PAUSE", heightPos - 90, new Color(250, 0, 0));
//        Game.ui.drawTextCenter("KEYBOARD TIPS", widthPos - -60, new Color(100, 100, 100));
//        Game.ui.drawTextCenterTips("TO USE STEROIDS: PRESS CTRL", widthPos - -90, new Color(100, 100, 100));
//        Game.ui.drawTextCenterTips("TO USE CROWN: PRESS SPACE", widthPos - -120, new Color(100, 100, 100));
//        Game.ui.drawTextCenterTips("TO RUN USING ENERGY: PRESS SHIFT", widthPos - -150, new Color(100, 100, 100));
//        Game.ui.drawTextCenterTips("TO USE RAFFLE DETECTOR: PRESS E", widthPos - -180, new Color(100, 100, 100));
//        Game.ui.drawTextCenterTips("TO ENTER PYRAMID: PRESS ENTER", widthPos - -210, new Color(100, 100, 100));
        int menuStart = calculateMenuYStart();
        Game.ui.drawTextCenter("PAUSE", menuStart, new Color(250, 0, 0));
        menuStart += (UI.LINE_HEIGHT);
        for (int i = 0; i < options.size(); i++)
            Game.ui.drawTextCenter(options.get(i), menuStart + (i * UI.LINE_HEIGHT), i == currentOption ? null : new Color(100, 100, 100));
    }

    public void renderGameOverMenu(Graphics2D graphics2d) {
        graphics2d.fillRect(-756, -19, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        graphics2d.setColor(new Color(250, 0, 250));
        int menuStart = calculateMenuYStart();
        Game.ui.drawTextCenter("GAME OVER", menuStart, new Color(250, 0, 0));
        menuStart += (UI.LINE_HEIGHT);
        for (int i = 0; i < options.size(); i++)
            Game.ui.drawTextCenter(options.get(i), menuStart + (i * UI.LINE_HEIGHT), i == currentOption ? null : new Color(100, 100, 100));
    }

    public void renderEndMenu(Graphics2D graphics2d) {
        graphics2d.fillRect(-756, -19, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        graphics2d.setColor(new Color(250, 0, 250));
        int menuStart = calculateMenuYStart();
        Game.ui.drawTextCenter("END GAME", menuStart, new Color(250, 0, 0));
        menuStart += (UI.LINE_HEIGHT);
        for (int i = 0; i < options.size(); i++)
            Game.ui.drawTextCenter(options.get(i), menuStart + (i * UI.LINE_HEIGHT), i == currentOption ? null : new Color(100, 100, 100));
    }
}