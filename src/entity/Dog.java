/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 *
 * @author thaia
 */
public class Dog extends Entity {

    public BufferedImage dog;

    public Dog(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void update() {

    }

    public void render (Graphics g) {

    }
}
