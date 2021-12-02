/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import entity.Entity;
import main.Game;

/**
 *
 * @author Leonardo
 */
public class Camera {
    public static int x;
    public static int y;
    
    public static int clamp(int xyActual, int xyMin, int xyMax){
        if(xyActual < xyMin){
            xyActual = xyMin;
        }
        
        if(xyActual > xyMax){
            xyActual = xyMax;
        }
        return xyActual;
    }
    
    public void updateCamera(Entity e) {
    	Camera.x = Camera.clamp(e.getX() - (Game.WIDTH / 2), 0, (World.mapWidth * (World.TILE_SIZE - Game.WIDTH)));
        Camera.y = Camera.clamp(e.getY() - (Game.HEIGHT / 2), 0, (World.mapHeight * (World.TILE_SIZE - Game.HEIGHT)));
    }
}
