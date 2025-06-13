package main;

import entities.Entity;
import world.World;

public class Camera {
    public static int x;
    public static int y;

    public static int clamp(int xyActual, int xyMin, int xyMax) {
        if(xyActual < xyMin)
            xyActual = xyMin;

        if(xyActual > xyMax)
            xyActual = xyMax;
        return xyActual;
    }

    public void updateCamera(Entity e) {
        Camera.x = Camera.clamp(e.getX() - (Game.WIDTH / 2), 0, (World.mapWidth * (World.TILE_SIZE - Game.WIDTH)));
        Camera.y = Camera.clamp(e.getY() - (Game.HEIGHT / 2), 0, (World.mapHeight * (World.TILE_SIZE - Game.HEIGHT)));
    }
}