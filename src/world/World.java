package world;

import entities.Chocolate;
import entities.Crown;
import entities.InvisibilityGadget;
import entities.Scorpion;
import entities.Door;
import entities.Life;
import entities.Snake;
import entities.Parchment;
import entities.Player;
import entities.Scarab;
import entities.Steroid;
import main.Camera;
import main.Game;
import tiles.Tile;
import tiles.TileFloor;
import tiles.TileWall;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;

public class World {
    public static Tile[] tiles;
    public static Tile[] tilesFloor;
    public static int mapWidth;
    public static int mapHeight;
    public static int TILE_SIZE = 32;
    public static int enemyCount = 0;

    public World(int level) throws IOException {
        if (Game.level == 1)
            Tile.loadLevel1Tiles();
        else if (Game.level == 2)
            Tile.loadLevel2Tiles();

        BufferedImage map = ImageIO.read(Objects.requireNonNull(getClass().getResource("/maps/level" + level + ".png")));

        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        int[] pixels = new int[mapWidth * mapHeight];
        tiles = new Tile[mapWidth * mapHeight];
        tilesFloor = new Tile[mapWidth * mapHeight];

        map.getRGB(0, 0, mapWidth, mapHeight, pixels, 0, mapWidth);

        for(int xx = 0; xx < mapWidth; xx++) {
            for(int yy = 0; yy < mapHeight; yy++) {
                int pixelAtual = pixels[xx + (yy * mapWidth)];

                int pos = xx + (yy * mapWidth);

                tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);
                tilesFloor[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);

                switch (pixelAtual) {
                    case 0xFF00ff00 -> {
                        if (Game.player == null)
                            Game.player = new Player(0, 0, TILE_SIZE, TILE_SIZE, null);
                        Game.entities.add(Game.player);
                        Game.player.setX(xx * TILE_SIZE);
                        Game.player.setY(yy * TILE_SIZE);
                    }
                    case 0xFF9fff9f -> {
                        tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);
                        tiles[pos].spawnArea = true;
                    }
                    case 0xFF003800 -> {
                        tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.PYRAMID_DARK_FLOOR);
                        tiles[pos].spawnArea = true;
                        tilesFloor[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.PYRAMID_DARK_FLOOR);
                        tilesFloor[pos].spawnArea = true;
                    }
                    case 0xFF000000 -> {
                        int enemy = generateEnemy();
                        switch (enemy) {
                            case 0 ->
                                    tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);
                            case 1 ->
                                    Game.entities.add(new Snake(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                            case 2 ->
                                    Game.entities.add(new Scorpion(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                            case 3 ->
                                    Game.entities.add(new Scarab(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                        }
                    }
                    case 0xFF4e4e4e -> // wall top solid
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectWallTopTile(xx, yy, true));
                    case 0xFF7e7e7e -> // wall top
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectWallTopTile(xx, yy, false));
                    case 0xFFc6c6c6 ->
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectWallTopEdge(xx, yy));
                    case 0xFFFFFFFF -> // wall
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectWallTile(xx, yy));
                    case 0xFFa2a2a2 ->
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectWallDoorTop(xx, yy));
                    case 0xFFd3d300 ->
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.PYRAMID_WALL);
                    case 0xFFe4e400 ->
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.PYRAMID_WALL_BOTTOM_CENTER);
                    case 0xFF424242 -> {
                        tiles[xx + ((yy - 2) * mapWidth)] = new TileWall(xx * TILE_SIZE, (yy - 2) * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectObeliskBg());
                        tiles[xx + (yy * mapWidth)] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                    case 0xFF2d2d2d -> {
                        tiles[xx + ((yy - 2) * mapWidth)] = new TileWall(xx * TILE_SIZE, (yy - 2) * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectObelisk());
                        tiles[xx + (yy * mapWidth)] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                    case 0xFFd68e8e ->
                            tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, selectObstacle());
                    case 0xFFff0000 -> // life
                            Game.entities.add(new Life(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFF72413b -> // chocolate
                            Game.entities.add(new Chocolate(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFFffff00 ->
                            Game.entities.add(new Door(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFFffd26d ->
                            Game.entities.add(new Parchment(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFF7507e2 ->
                            Game.entities.add(new Steroid(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFF825353 ->
                            Game.entities.add(new Crown(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFF001ed4 ->
                            Game.entities.add(new InvisibilityGadget(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFF6e956f ->
                            Game.entities.add(new Snake(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFFb09898 ->
                            Game.entities.add(new Scorpion(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    case 0xFFffa579 ->
                            Game.entities.add(new Scarab(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    default -> // floor
                            tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);
                }
            }
        }
        spawnLife();
        spawnSteroid();
        if(Game.level == 1) {
            spawnChocolate();
            spawnCrown();
            spawninvisibilityGadget();
        }

        if(Game.level == 2)
            spawnParchment();
    }

    public static void spawnParchment() {
        int parchment = 0;
        int maxParchment = 10;
        Random rand = new Random();
        while (parchment < maxParchment) {
            int x = rand.nextInt(mapWidth);
            int y = rand.nextInt(mapHeight - 1);
            int pos = x + (y * mapWidth);
            if(tiles[pos] instanceof TileFloor && !tiles[pos].spawnArea) {
                if(rand.nextInt(100) < 10) {
                    Game.entities.add(new Parchment(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    parchment++;
                }
            }
        }
    }

    public static void spawnChocolate() {
        int chocolate = 0;
        int maxChocolate = 5;
        Random rand = new Random();
        while (chocolate < maxChocolate) {
            int x = rand.nextInt(mapWidth);
            int y = rand.nextInt(mapHeight);
            int pos = x + (y * mapWidth);
            if(tiles[pos] instanceof TileFloor && !tiles[pos].spawnArea) {
                if(rand.nextInt(100) < 10) {
                    Game.entities.add(new Chocolate(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    chocolate++;
                }
            }
        }
    }

    public static void spawnLife() {
        int life = 0;
        int maxLife = 3;
        Random rand = new Random();
        while (life < maxLife) {
            int x = rand.nextInt(mapWidth);
            int y = rand.nextInt(mapHeight - 1);
            int pos = x + (y * mapWidth);
            if(tiles[pos] instanceof TileFloor && !tiles[pos].spawnArea) {
                if(rand.nextInt(100) < 10) {
                    Game.entities.add(new Life(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    life++;
                }
            }
        }
    }

    public static void spawnSteroid() {
        int steroid = 0;
        int maxSteroid = 3;
        Random rand = new Random();
        while (steroid < maxSteroid) {
            int x = rand.nextInt(mapWidth);
            int y = rand.nextInt(mapHeight - 1);
            int pos = x + (y * mapWidth);
            if(tiles[pos] instanceof TileFloor && !tiles[pos].spawnArea) {
                if(rand.nextInt(100) < 10) {
                    Game.entities.add(new Steroid(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    steroid++;
                }
            }
        }
    }

    public static void spawnCrown() {
        Random rand = new Random();
        int crown = 0;
        while (crown < 1) {
            int x = rand.nextInt((mapWidth));
            int y = rand.nextInt((mapHeight));
            int pos = x + (y * mapWidth);
            if(tiles[pos] instanceof TileFloor && !tiles[pos].spawnArea) {
                if(rand.nextInt(100) < 10) {
                    Game.entities.add(new Crown(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    crown++;
                }
            }
        }
    }

    public static void spawninvisibilityGadget() {
        Random rand = new Random();
        int invisibilityGadget = 0;
        while (invisibilityGadget < 1) {
            int x = rand.nextInt((mapWidth));
            int y = rand.nextInt((mapHeight));
            int pos = x + (y * mapWidth);
            if(tiles[pos] instanceof TileFloor && !tiles[pos].spawnArea) {
                if(rand.nextInt(100) < 10) {
                    Game.entities.add(new InvisibilityGadget(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                    invisibilityGadget++;
                }
            }
        }
    }

    public BufferedImage selectWallTopEdge(int x, int y) {
        BufferedImage tile = Tile.PYRAMID_WALL_TOP_RIGHT;
        try {
            if(tiles[x - 1 + (y  * mapWidth)] instanceof TileFloor)
                tile = Tile.PYRAMID_WALL_TOP_LEFT;
        } catch (Exception e) {
            return tile;
        }
        return tile;
    }

    public BufferedImage selectObstacle() {
        return switch (Game.level) {
            case 1 -> {
                int random = new Random().nextInt(12);
                yield switch (random) {
                    case 0 -> Tile.OUTSIDE_OBSTACLE_1;
                    case 1 -> Tile.OUTSIDE_OBSTACLE_2;
                    case 2 -> Tile.OUTSIDE_OBSTACLE_3;
                    case 3 -> Tile.OUTSIDE_OBSTACLE_4;
                    case 4 -> Tile.OUTSIDE_OBSTACLE_5;
                    case 5 -> Tile.OUTSIDE_OBSTACLE_6;
                    case 6 -> Tile.OUTSIDE_OBSTACLE_7;
                    case 7 -> Tile.OUTSIDE_OBSTACLE_8;
                    case 8 -> Tile.OUTSIDE_OBSTACLE_9;
                    case 9 -> Tile.OUTSIDE_OBSTACLE_10;
                    case 10 -> Tile.OUTSIDE_OBSTACLE_11;
                    case 11 -> Tile.OUTSIDE_OBSTACLE_12;
                    default -> Tile.OUTSIDE_OBSTACLE_4;
                };
            }
            case 2 -> {
                int random = new Random().nextInt(4);
                yield switch (random) {
                    case 0 -> Tile.PYRAMID_OBSTACLE_1;
                    case 1 -> Tile.PYRAMID_OBSTACLE_2;
                    case 2 -> Tile.PYRAMID_OBSTACLE_3;
                    case 3 -> Tile.PYRAMID_OBSTACLE_4;
                    default -> Tile.OUTSIDE_OBSTACLE_1;
                };
            }
            default -> Tile.OUTSIDE_OBSTACLE_1;
        };
    }

    public int generateEnemy() {
        int random = new Random().nextInt(100);
        if(Game.level == 1) {
            if(random <= 96)
                return 0;
            else if (random == 97)
                return 1;
            else if (random == 98)
                return 2;
            else
                return 3;
        } else {
            if(random <= 94)
                return 0;
            else if (random >= 95 && random <= 98)
                return 2;
            else
                return 3;
        }
    }

    public static BufferedImage selectObelisk() {
        int random = new Random().nextInt(6);
        return switch (random) {
            case 0 -> Tile.PYRAMID_OBELISK_1;
            case 1 -> Tile.PYRAMID_OBELISK_2;
            case 2 -> Tile.PYRAMID_OBELISK_3;
            case 3 -> Tile.PYRAMID_OBELISK_4;
            case 4 -> Tile.PYRAMID_OBELISK_5;
            case 5 -> Tile.PYRAMID_OBELISK_6;
            default -> Tile.PYRAMID_OBELISK_1;
        };
    }

    public static BufferedImage selectObeliskBg() {
        int random = new Random().nextInt(6);
        return switch (random) {
            case 0 -> Tile.PYRAMID_OBELISK_BG_1;
            case 1 -> Tile.PYRAMID_OBELISK_BG_2;
            case 2 -> Tile.PYRAMID_OBELISK_BG_3;
            case 3 -> Tile.PYRAMID_OBELISK_BG_4;
            case 4 -> Tile.PYRAMID_OBELISK_BG_5;
            case 5 -> Tile.PYRAMID_OBELISK_BG_6;
            default -> Tile.PYRAMID_OBELISK_BG_1;
        };
    }

    public static BufferedImage selectWallDoorTop(int x, int y) {
        BufferedImage tile = Tile.WALL_TOP_DOOR;
        try {
            if(tiles[x - 1 + (y  * mapWidth)].getSprite() == Tile.WALL_RIGHT)
                tiles[x - 1 + (y  * mapWidth)].setSprite(Tile.WALL);
        } catch (Exception e) {
            return tile;
        }
        return tile;
    }

    public static BufferedImage selectWallTopTile(int x, int y, boolean solid) {
        BufferedImage tile = solid ? Tile.WALL_TOP_SOLID : Tile.WALL_TOP;
        try {
            // If the tile at left is any kind of right tile/right corner tile it will be replaced by a regular wall tile/wall bottom tile (can't have any right/bottom right kind tile followed by another of its kind)
            if(tiles[x - 1 + (y  * mapWidth)].getSprite() == Tile.WALL_RIGHT)
                tiles[x - 1 + (y  * mapWidth)].setSprite(Tile.WALL);
            if(tiles[x - 1 + (y  * mapWidth)].getSprite() == Tile.WALL_BOTTOM_RIGHT_CORNER)
                tiles[x - 1 + (y  * mapWidth)].setSprite(Tile.WALL_BOTTOM_CENTER);

        } catch (Exception e) {
            return tile;
        }
        return tile;
    }

    public static BufferedImage selectWallTile(int x, int y) {
        BufferedImage tile = Tile.WALL_BOTTOM_LEFT_CORNER;
        try {
            if(tiles[x + ((y - 1)  * mapWidth)].solid)
                tiles[x + (y  * mapWidth)].solid = true;
            // If the tile above is any kind of bottom wall it will be replaced by a regular wall tile (can't have any bottom kind tile above a regular wall tile)
            if(tiles[x + ((y - 1)  * mapWidth)].getSprite() == Tile.WALL_BOTTOM_CENTER)
                tiles[x + ((y - 1)  * mapWidth)].setSprite(Tile.WALL);
            if(tiles[x + ((y - 1)  * mapWidth)].getSprite() == Tile.WALL_BOTTOM_LEFT_CORNER)
                tiles[x + ((y - 1)  * mapWidth)].setSprite(Tile.WALL_LEFT);
            if(tiles[x + ((y - 1)  * mapWidth)].getSprite() == Tile.WALL_BOTTOM_RIGHT_CORNER)
                tiles[x + ((y - 1)  * mapWidth)].setSprite(Tile.WALL_RIGHT);

            // If the tile at left is any kind of right tile/right corner tile it will be replaced by a regular wall tile/wall bottom tile (can't have any right/bottom right kind tile followed by another of its kind)
            if(tiles[x - 1 + (y  * mapWidth)].getSprite() == Tile.WALL_RIGHT)
                tiles[x - 1 + (y  * mapWidth)].setSprite(Tile.WALL);
            if(tiles[x - 1 + (y  * mapWidth)].getSprite() == Tile.WALL_BOTTOM_RIGHT_CORNER) {
                if (tiles[x - 2 + (y  * mapWidth)] instanceof TileFloor)
                    tiles[x - 1 + (y  * mapWidth)].setSprite(Tile.WALL_BOTTOM_LEFT_CORNER);
                else
                    tiles[x - 1 + (y  * mapWidth)].setSprite(Tile.WALL_BOTTOM_CENTER);
            }

            if(tiles[x + ((y - 1)  * mapWidth)].getSprite() == Tile.WALL_TOP || tiles[x + ((y - 1)  * mapWidth)].getSprite() == Tile.WALL_TOP_SOLID)
                tile = Tile.WALL_BOTTOM_RIGHT_CORNER;

            if (tile == Tile.WALL_RIGHT && tiles[x - 1 + (y  * mapWidth)] instanceof TileFloor)
                tile = Tile.WALL_LEFT;

            if(tile == Tile.WALL && tiles[x - 1 + (y  * mapWidth)] instanceof TileFloor)
                tile = Tile.WALL_LEFT;

            if (tile == Tile.WALL_BOTTOM_RIGHT_CORNER && tiles[x - 1 + (y  * mapWidth)] instanceof TileFloor)
                tile = Tile.WALL_BOTTOM_LEFT_CORNER;

            if(tiles[x + ((y - 1)  * mapWidth)].getSprite() == Tile.WALL_RIGHT)
                tile = Tile.WALL_BOTTOM_RIGHT_CORNER;
            if(tiles[x + ((y - 1)  * mapWidth)].getSprite() == Tile.WALL_LEFT)
                tile = Tile.WALL_BOTTOM_LEFT_CORNER;
        } catch (Exception e) {
            return tile;
        }
        return tile;
    }

    public static boolean isFree(int xNext, int yNext) {
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext+TILE_SIZE - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext+TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        return !(
                (tiles[x1 + (y1 * World.mapWidth)] instanceof TileWall) ||
                        (tiles[x2 + (y2 * World.mapWidth)] instanceof TileWall) ||
                        (tiles[x3 + (y3 * World.mapWidth)] instanceof TileWall) ||
                        (tiles[x4 + (y4 * World.mapWidth)] instanceof TileWall)
        );
    }

    public static boolean isFreeDynamic(int xNext, int yNext, int width, int height) {

        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext + width - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + height - 1) / TILE_SIZE;

        int x4 = (xNext + width - 1) /  TILE_SIZE;
        int y4 = (yNext + height - 1) / TILE_SIZE;

        try {
            return !(
                    (tiles[x1 + (y1 * World.mapWidth)] instanceof TileWall && tiles[x1 + (y1 * World.mapWidth)].solid) ||
                            (tiles[x2 + (y2 * World.mapWidth)] instanceof TileWall && tiles[x2 + (y2 * World.mapWidth)].solid) ||
                            (tiles[x3 + (y3 * World.mapWidth)] instanceof TileWall && tiles[x3 + (y3 * World.mapWidth)].solid) ||
                            (tiles[x4 + (y4 * World.mapWidth)] instanceof TileWall && tiles[x4 + (y4 * World.mapWidth)].solid)
            );
        } catch (Exception e) {
            return true;
        }
    }

    public void update() {
        for (int i = 0, tilesLength = tiles.length; i < tilesLength; i++) {
            Tile t = tiles[i];
            t.update();
        }
    }

    public void renderFloor(Graphics g) {
        int xStart = Camera.x / TILE_SIZE;
        int yStart = (Camera.y / TILE_SIZE);

        int xFinal = xStart + (Game.WIDTH / TILE_SIZE);
        int yFinal = yStart + (Game.HEIGHT / TILE_SIZE);

        for(int xx = xStart ; xx <= xFinal; xx++) {
            for(int yy = yStart; yy <= yFinal; yy++) {

                if(xx < 0 || yy < 0 || xx >= mapWidth || yy >= mapHeight)
                    continue;
                Tile tile = tilesFloor[xx + (yy * mapWidth)];
                tile.render(g);
            }
        }
    }

    public void renderWall(Graphics g) {
        int xStart = Camera.x / TILE_SIZE;
        int yStart = (Camera.y / TILE_SIZE) - 3;

        int xFinal = xStart + (Game.WIDTH / TILE_SIZE);
        int yFinal = yStart + (Game.HEIGHT / TILE_SIZE) + 3;

        for(int xx = xStart ; xx <= xFinal; xx++) {
            for(int yy = yStart; yy <= yFinal; yy++) {

                if(xx < 0 || yy < 0 || xx >= mapWidth || yy >= mapHeight)
                    continue;

                Tile tile = tiles[xx + (yy * mapWidth)];
                if(tile instanceof TileWall)
                    tile.render(g);
            }
        }
    }
}