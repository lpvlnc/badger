/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import world.Tile.Tile;
import world.Tile.TileFloor;
import world.Tile.TileWall;
import entity.Chocolate;
import entity.Entity;
import entity.Life;
import entity.Parchment;
import entity.Player;
import entity.Steroid;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Game;

/**
 *
 * @author Leonardo
 */
public class World {
    public static Tile[] tiles;
    public static int mapWidth;
    public static int mapHeight;
    public static int TILE_SIZE = 32;
    
    public static int enemyCount = 0;
    
    public static int day = 0;
    public static int night = 1;
    public static int shift = day;
    
    public World(String path) throws IOException {

        // ALGORITMO DE RENDERIZACAO DE MAPA A PARTIR DE UMA IMAGEM BIT MAP //
        BufferedImage map = ImageIO.read(getClass().getResource(path));

        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        int[] pixels = new int[mapWidth * mapHeight];
        tiles = new Tile[mapWidth * mapHeight];

        map.getRGB(0, 0, mapWidth, mapHeight, pixels, 0, mapWidth);

        for(int xx = 0; xx < mapWidth; xx++){
            for(int yy = 0; yy < mapHeight; yy++){

                int pixelAtual = pixels[xx + (yy * mapWidth)];

                int pos = xx + (yy * mapWidth);

                tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);

                switch(pixelAtual){
                    case 0xFF00ff00: // player
                        if(Game.player == null){
                            Game.player = new Player(0, 0, 16, 16, null);
                            Game.entities.add(Game.player);
                            Game.player.setX(xx * TILE_SIZE);
                            Game.player.setY(yy * TILE_SIZE);
                        }
                        break;
                    case 0xFF000000: // floor
                        tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);
                        break;
                    case 0xFFFFFFFF: // wall
                        tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.WALL);
                        break;
                    case 0xFFe6e6e6: // WALL bottom left corner
                        tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.WALL_BOTTOM_LEFT_CORNER);
                        break;
                    case 0xFFd2d2d2: // WALL bottom center wall
                        tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.WALL_BOTTOM_CENTER);
                        break;
                    case 0xFFbebebe: // WALL bottom right corner
                        tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.WALL_BOTTOM_RIGHT_CORNER);
                        break;
                    case 0xFFafafaf: // WALL top left corner
                        tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.WALL_TOP_LEFT_CORNER);
                        break;
                    case 0xFF9b9b9b: // WALL top right corner
                        tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.WALL_TOP_RIGHT_CORNER);
                        break;
                    case 0xFF7e7e7e: // WALL top
                    tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.WALL_TOP);
                    break;
                    case 0xFFff0000: // life
                        Game.entities.add(new Life(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                        break;
                    case 0xFF72413b: // chocolate
                        Game.entities.add(new Chocolate(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                        break;
                    case 0xFFffd26d:
                        Game.entities.add(new Parchment(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                        break;
                    case 0xFF7507e2:
                        Game.entities.add(new Steroid(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
                        break;
                    default: // floor
                        tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.FLOOR);
                        break;
                }

            }
        }
    }
    
    public static boolean isFree(int xNext, int yNext){
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
    
    public static boolean isFreeDynamic(int xNext, int yNext, int width, int height){
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;
        
        int x2 = (xNext + width - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;
        
        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + height - 1) / TILE_SIZE;
        
        int x4 = (xNext + width - 1) /  TILE_SIZE;
        int y4 = (yNext + height - 1) / TILE_SIZE;
        
        return !(
                (tiles[x1 + (y1 * World.mapWidth)] instanceof TileWall) ||
                (tiles[x2 + (y2 * World.mapWidth)] instanceof TileWall) ||
                (tiles[x3 + (y3 * World.mapWidth)] instanceof TileWall) ||
                (tiles[x4 + (y4 * World.mapWidth)] instanceof TileWall)
                );
    }
    
    public void render(Graphics g){
    
        int xStart = Camera.x / TILE_SIZE;
        int yStart = Camera.y / TILE_SIZE;
        
        int xFinal = xStart + (Game.WIDTH / TILE_SIZE);
        int yFinal = yStart + (Game.HEIGHT / TILE_SIZE);
        
        for(int xx = xStart ; xx <= xFinal; xx++){
            for(int yy = yStart; yy <= yFinal; yy++){
                
                if(xx < 0 || yy < 0 || xx >= mapWidth || yy >= mapHeight){
                    continue;
                }
                
                Tile tile = tiles[xx + (yy * mapWidth)];
                tile.render(g);
            }
        }
    }
}
