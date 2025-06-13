package tiles;

import main.Camera;
import main.Game;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import static world.World.mapWidth;
import static world.World.tiles;

public class Tile {
    BufferedImage sprite;
    int x;
    int y;
    private int width;
    private int height;

    public boolean solid = true;
    public boolean spawnArea = false;
    public int depth = 0;

    // Outside floor
    public static final BufferedImage OUTSIDE_FLOOR = Game.spritesheet.getSprite(0, 608, World.TILE_SIZE, World.TILE_SIZE);

    // Outside wall
    public static final BufferedImage OUTSIDE_WALL_TOP = Game.spritesheet.getSprite(128, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_TOP_SOLID = Game.spritesheet.getSprite(128, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_TOP_DOOR = Game.spritesheet.getSprite(96, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_LEFT = Game.spritesheet.getSprite(64, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL = Game.spritesheet.getSprite(96, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_RIGHT = Game.spritesheet.getSprite(128, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_BOTTOM_LEFT_CORNER = Game.spritesheet.getSprite(64, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_BOTTOM_CENTER = Game.spritesheet.getSprite(96, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_WALL_BOTTOM_RIGHT_CORNER = Game.spritesheet.getSprite(128, 608, World.TILE_SIZE, World.TILE_SIZE);

    // Outside obstacles
    public static final BufferedImage OUTSIDE_OBSTACLE_1 = Game.spritesheet.getSprite(512, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_2 = Game.spritesheet.getSprite(544, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_3 = Game.spritesheet.getSprite(576, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_4 = Game.spritesheet.getSprite(608, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_5 = Game.spritesheet.getSprite(288, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_6 = Game.spritesheet.getSprite(320, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_7 = Game.spritesheet.getSprite(352, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_8 = Game.spritesheet.getSprite(320, 512, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_9 = Game.spritesheet.getSprite(352, 512, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_10 = Game.spritesheet.getSprite(320, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_11 = Game.spritesheet.getSprite(352, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage OUTSIDE_OBSTACLE_12 = Game.spritesheet.getSprite(320, 576, World.TILE_SIZE, World.TILE_SIZE);

    // Pyramid floor
    public static final BufferedImage PYRAMID_FLOOR = Game.spritesheet.getSprite(0, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_DARK_FLOOR = Game.spritesheet.getSprite(0, 544, World.TILE_SIZE, World.TILE_SIZE);

    // Pyramid wall
    public static final BufferedImage PYRAMID_WALL_TOP = Game.spritesheet.getSprite(416, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_TOP_SOLID = Game.spritesheet.getSprite(416, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_TOP_DOOR = Game.spritesheet.getSprite(384, 544, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_TOP_LEFT = Game.spritesheet.getSprite(384, 512, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_TOP_RIGHT = Game.spritesheet.getSprite(416, 512, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_LEFT = Game.spritesheet.getSprite(352, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL = Game.spritesheet.getSprite(384, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_RIGHT = Game.spritesheet.getSprite(416, 576, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_BOTTOM_LEFT_CORNER = Game.spritesheet.getSprite(352, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_BOTTOM_CENTER = Game.spritesheet.getSprite(384, 608, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_WALL_BOTTOM_RIGHT_CORNER = Game.spritesheet.getSprite(416, 608, World.TILE_SIZE, World.TILE_SIZE);

    // Pyramid obstacles
    public static final BufferedImage PYRAMID_OBSTACLE_1 = Game.spritesheet.getSprite(384, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_OBSTACLE_2 = Game.spritesheet.getSprite(416, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_OBSTACLE_3 = Game.spritesheet.getSprite(448, 480, World.TILE_SIZE, World.TILE_SIZE);
    public static final BufferedImage PYRAMID_OBSTACLE_4 = Game.spritesheet.getSprite(480, 480, World.TILE_SIZE, World.TILE_SIZE);

    // Pyramid obelisk with background
    public static final BufferedImage PYRAMID_OBELISK_BG_1 = Game.spritesheet.getSprite(448, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_BG_2 = Game.spritesheet.getSprite(480, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_BG_3 = Game.spritesheet.getSprite(512, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_BG_4 = Game.spritesheet.getSprite(544, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_BG_5 = Game.spritesheet.getSprite(576, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_BG_6 = Game.spritesheet.getSprite(608, 384, 32, 96);

    // Pyramid obelisk without background
    public static final BufferedImage PYRAMID_OBELISK_1 = Game.spritesheet.getSprite(256, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_2 = Game.spritesheet.getSprite(288, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_3 = Game.spritesheet.getSprite(320, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_4 = Game.spritesheet.getSprite(352, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_5 = Game.spritesheet.getSprite(384, 384, 32, 96);
    public static final BufferedImage PYRAMID_OBELISK_6 = Game.spritesheet.getSprite(416, 384, 32, 96);

    // Floor
    public static BufferedImage FLOOR = OUTSIDE_FLOOR;

    // Wall
    public static BufferedImage WALL_TOP = OUTSIDE_WALL_TOP;
    public static BufferedImage WALL_TOP_SOLID = OUTSIDE_WALL_TOP_SOLID;
    public static BufferedImage WALL_TOP_DOOR = OUTSIDE_WALL_TOP_DOOR;
    public static BufferedImage WALL_LEFT = OUTSIDE_WALL_LEFT;
    public static BufferedImage WALL = OUTSIDE_WALL;
    public static BufferedImage WALL_RIGHT = OUTSIDE_WALL_RIGHT;
    public static BufferedImage WALL_BOTTOM_LEFT_CORNER = OUTSIDE_WALL_BOTTOM_LEFT_CORNER;
    public static BufferedImage WALL_BOTTOM_CENTER = OUTSIDE_WALL_BOTTOM_CENTER;
    public static BufferedImage WALL_BOTTOM_RIGHT_CORNER = OUTSIDE_WALL_BOTTOM_RIGHT_CORNER;

    public Tile(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

    }

    public static void loadLevel1Tiles() {
        FLOOR = OUTSIDE_FLOOR;
        WALL_TOP = OUTSIDE_WALL_TOP;
        WALL_TOP_SOLID = OUTSIDE_WALL_TOP_SOLID;
        WALL_TOP_DOOR = OUTSIDE_WALL_TOP_DOOR;
        WALL_LEFT = OUTSIDE_WALL_LEFT;
        WALL = OUTSIDE_WALL;
        WALL_RIGHT = OUTSIDE_WALL_RIGHT;
        WALL_BOTTOM_LEFT_CORNER = OUTSIDE_WALL_BOTTOM_LEFT_CORNER;
        WALL_BOTTOM_CENTER = OUTSIDE_WALL_BOTTOM_CENTER;
        WALL_BOTTOM_RIGHT_CORNER = OUTSIDE_WALL_BOTTOM_RIGHT_CORNER;
    }

    public static void loadLevel2Tiles() {
        FLOOR = PYRAMID_FLOOR;
        WALL_TOP = PYRAMID_WALL_TOP;
        WALL_TOP_SOLID = PYRAMID_WALL_TOP_SOLID;
        WALL_TOP_DOOR = PYRAMID_WALL_TOP_DOOR;
        WALL_LEFT = PYRAMID_WALL_LEFT;
        WALL = PYRAMID_WALL;
        WALL_RIGHT = PYRAMID_WALL_RIGHT;
        WALL_BOTTOM_LEFT_CORNER = PYRAMID_WALL_BOTTOM_LEFT_CORNER;
        WALL_BOTTOM_CENTER = PYRAMID_WALL_BOTTOM_CENTER;
        WALL_BOTTOM_RIGHT_CORNER = PYRAMID_WALL_BOTTOM_RIGHT_CORNER;
    }

    public void update() {
        solid = sprite == Tile.WALL_BOTTOM_LEFT_CORNER ||
                sprite == Tile.WALL_BOTTOM_CENTER ||
                sprite == Tile.WALL_BOTTOM_RIGHT_CORNER ||
                sprite == Tile.WALL_TOP_SOLID ||
                sprite == Tile.PYRAMID_WALL_BOTTOM_CENTER ||
                sprite == Tile.OUTSIDE_OBSTACLE_1 ||
                sprite == Tile.OUTSIDE_OBSTACLE_2 ||
                sprite == Tile.OUTSIDE_OBSTACLE_3 ||
                sprite == Tile.OUTSIDE_OBSTACLE_4 ||
                sprite == Tile.OUTSIDE_OBSTACLE_5 ||
                sprite == Tile.OUTSIDE_OBSTACLE_6 ||
                sprite == Tile.OUTSIDE_OBSTACLE_7 ||
                sprite == Tile.OUTSIDE_OBSTACLE_8 ||
                sprite == Tile.OUTSIDE_OBSTACLE_9 ||
                sprite == Tile.OUTSIDE_OBSTACLE_10 ||
                sprite == Tile.OUTSIDE_OBSTACLE_11 ||
                sprite == Tile.OUTSIDE_OBSTACLE_12 ||
                sprite == Tile.PYRAMID_OBSTACLE_1 ||
                sprite == Tile.PYRAMID_OBSTACLE_2 ||
                sprite == Tile.PYRAMID_OBSTACLE_3 ||
                sprite == Tile.PYRAMID_OBSTACLE_4 ||
                sprite == null ||
                tiles[(x / World.TILE_SIZE) + (((y - 1) / World.TILE_SIZE) * mapWidth)].sprite == Tile.WALL_TOP_SOLID;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, (int)x - Camera.x, (int)y - Camera.y, null);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
