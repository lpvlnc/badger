/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import entity.Chocolate;
import entity.Parchment;
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

                tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.TILE_FLOOR);

                switch(pixelAtual){
                    case 0xFF000000: // chão
                        tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.TILE_FLOOR);
                        break;
                    case 0xFFFFFFFF:
                        tiles[pos] = new TileWall(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.TILE_WALL);
                        break;
                    case 0xFF4CFF00: // player
                        Game.player.setX(xx * TILE_SIZE);
                        Game.player.setY(yy * TILE_SIZE);
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
                    default: // chão
                        tiles[pos] = new TileFloor(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Tile.TILE_FLOOR);
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
                (tiles[x1 + (y1 * World.mapWidth)] instanceof TileFloor) ||
                (tiles[x2 + (y2 * World.mapWidth)] instanceof TileFloor) ||
                (tiles[x3 + (y3 * World.mapWidth)] instanceof TileFloor) ||
                (tiles[x4 + (y4 * World.mapWidth)] instanceof TileFloor)
                );
    }
    
    public static boolean isFreeDynamic(int xNext, int yNext, int width, int height){
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;
        
        int x2 = (xNext+width - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;
        
        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext+height - 1) / TILE_SIZE;
        
        int x4 = (xNext + width - 1) / TILE_SIZE;
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
