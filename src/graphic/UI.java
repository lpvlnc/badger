/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Game;
import world.World;

/**
 *
 * @author Leonardo
 */
public class UI {
    private Graphics g;
    public static BufferedImage heartBack;
    public static BufferedImage heart;
    public static BufferedImage weakHeartBack;
    public static BufferedImage weakHeart;
    public static BufferedImage chocolateBack;
    public static BufferedImage chocolate;
    public static BufferedImage energyBack;
    public static BufferedImage energy;
    public static BufferedImage steroid;
    public static BufferedImage parchmentBack;
    public static BufferedImage parchment;
    public static boolean updateFps;
    public static int frames = 0;
    
    public int animationFrames = 0;
    public int animationMaxFrames = 10;
    public int animationPos = 0;

    
    
    // font
    public static Font pixelFont;
    Color fontOutlineColor = Color.black;
    Color fontFillColor = Color.white;
    
    
    public UI() throws IOException{
        initSprites();
        
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream).deriveFont(16f);
        } catch (FontFormatException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initSprites() {
        heartBack = Game.spritesheet.getSprite(608, 32, World.TILE_SIZE, World.TILE_SIZE);
        heart = Game.spritesheet.getSprite(608, 0, World.TILE_SIZE, World.TILE_SIZE);
        
        weakHeartBack = Game.spritesheet.getSprite(608, 96, World.TILE_SIZE, World.TILE_SIZE);
        weakHeart = Game.spritesheet.getSprite(608, 64, World.TILE_SIZE, World.TILE_SIZE);
        
        chocolateBack = Game.spritesheet.getSprite(576, 32, World.TILE_SIZE, World.TILE_SIZE);
        chocolate = Game.spritesheet.getSprite(576, 0, World.TILE_SIZE, World.TILE_SIZE);
        
        energyBack = Game.spritesheet.getSprite(416, 32, World.TILE_SIZE, World.TILE_SIZE);
        energy = Game.spritesheet.getSprite(416, 0, World.TILE_SIZE, World.TILE_SIZE);
        
        steroid = Game.spritesheet.getSprite(512, 0, World.TILE_SIZE, World.TILE_SIZE);
        
        parchmentBack = Game.spritesheet.getSprite(544, 32, World.TILE_SIZE, World.TILE_SIZE);
        parchment = Game.spritesheet.getSprite(544, 0, World.TILE_SIZE, World.TILE_SIZE);
    }
    
    public void render(Graphics graphics){
        this.g = graphics;
        g.setFont(pixelFont);
        renderLife();
        renderEnergy();
        if(Game.level == 1)
            renderChocolate();
        else
            renderParchment();
        renderSteroid();
        renderScore();
        showFPS();
    }
    
    public void showFPS(){
        if(Game.showFps) {
            drawText("FPS:" + frames, 854, 606, null);
        }
    }
    
    public void renderScore() {
        String score = String.valueOf(Game.player.score);
        while(score.length() < 6) {
            score = "0" + score;
        }
            
        drawText("SCORE:" + score, 756, 20, null);
    }
    
    public void renderSteroid() {
        g.drawImage(steroid, -437, 558, null);
        String zero = Game.player.steroidCounter < 10 ? "0" : "";
        drawText("x" + zero + Game.player.steroidCounter, 26, 604, null);
    }
    
    public void renderChocolate() {
        drawText("CHOCOLATE:", 437, 20, null);
        for(int i = 0; i < 5; i++) {
            g.drawImage(chocolateBack, 154 + (i * 30), -24, null);
        }
        
        for(int i = 0; i < Game.player.chocolateCounter; i++) {
            g.drawImage(chocolate, 154 + (i * 30), -24, null);
        }
    }
    
    public void renderParchment() {
        drawText("PARCHMENT:", 437, 20, null);
        for(int i = 0; i < 10; i ++) {
            if(i < 5)
                g.drawImage(parchmentBack, 160 + (i * 30), -19, 24, 24, null);
            else
                g.drawImage(parchmentBack, 160 + ((i - 5) * 30), 7, 24, 24, null);
        }
        for(int i = 0; i < Game.player.parchmentCounter; i ++) {
            if(i < 5)
                g.drawImage(parchment, 160 + (i * 30), -19, 24, 24, null);
            else
                g.drawImage(parchment, 160 + ((i - 5) * 30), 7, 24, 24, null);
        }
    }
    
    public void renderEnergy() {
        drawText("ENERGY:", 221, 20, null);
        if (Game.player.onSteroid){
            for(int i = 0; i < 5; i++) {
                g.drawImage(energyBack, 101 + (i * 17), -24, null);
            }
            g.drawImage(energy, 101 + (animationPos * 17), -24, null);
            animationFrames++;
            if(animationFrames == animationMaxFrames) {
                animationFrames = 0;
                animationPos++;
                if(animationPos > 4){
                    animationPos = 0; 
                }
            }
        }else {
            for(int i = 0; i < 5; i++) {
                g.drawImage(energyBack, 101 + (i * 17), -24, null);
            }
            if(!Game.player.weak){
                for(int i = 0; i < 5 * Game.player.energy / 100; i++) {
                    g.drawImage(energy, 101 + (i * 17), -24, null);
                }
            }
        }
    }
    
    public void renderLife() {
        drawText("LIFE:", 4, 20, null);
        if(Game.player.onSteroid) {
            for(int i = 0; i < 5; i++){
                g.drawImage(heartBack, 79 +  (i * 24), -15, null);
            }
            g.drawImage(heart, 79 + (animationPos * 24), -15, null);
            animationFrames++;
            if(animationFrames == animationMaxFrames) {
                animationFrames = 0;
                animationPos++;
                if(animationPos > 4){
                    animationPos = 0; 
                }
            }
        } else if(Game.player.weak) {
            for(int i = 0; i < 5; i++){
                g.drawImage(weakHeartBack, 79 +  (i * 24), -15, null);
            }
            if(Game.player.life >= 1)
                g.drawImage(weakHeart, 79, -15, null);
        } else {
            for(int i = 0; i < 5; i++){
                g.drawImage(heartBack, 79 +  (i * 24), -15, null);
            }
            for(int i = 0; i < Game.player.life; i++){
                g.drawImage(heart, 79 + (i * 24), -15, null);
            }
        }
    }
    
    public void drawText(String text, int x, int y, Color color) {

        BasicStroke outlineStroke = new BasicStroke(2.0f);

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            
            AffineTransform tform = AffineTransform.getTranslateInstance(x, y);
            tform.scale(1, 1);
            g2.setTransform(tform);
            
            // remember original settings
            Color originalColor = g2.getColor();
            Stroke originalStroke = g2.getStroke();
            RenderingHints originalHints = g2.getRenderingHints();
            
            // create a glyph vector from your text
            GlyphVector glyphVector = g2.getFont().createGlyphVector(g2.getFontRenderContext(), text);
            
            // get the shape object
            Shape textShape = glyphVector.getOutline();
            
            // activate anti aliasing for text rendering (if you want it to look nice)
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setColor(this.fontOutlineColor);
            g2.setStroke(outlineStroke);
            g2.draw(textShape); // draw outline
            
            if(color == null)
                g2.setColor(this.fontFillColor);
            else
                g2.setColor(color);
            g2.fill(textShape); // fill the shape

            // reset to original settings after painting
            g2.setColor(originalColor);
            g2.setStroke(originalStroke);
            g2.setRenderingHints(originalHints);
            g = (Graphics)g2;
        }
    }
    
    public void drawTextCenter(String text, int y, Color color) {

        BasicStroke outlineStroke = new BasicStroke(2.0f);

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            FontMetrics metrics = g.getFontMetrics(g2.getFont());
            int xx = ((Game.WIDTH * Game.SCALE ) - metrics.stringWidth(text)) / 2;
            AffineTransform tform = AffineTransform.getTranslateInstance(xx, y);
            tform.scale(1, 1);
            g2.setTransform(tform);
            
            // remember original settings
            Color originalColor = g2.getColor();
            Stroke originalStroke = g2.getStroke();
            RenderingHints originalHints = g2.getRenderingHints();
            
            // create a glyph vector from your text
            GlyphVector glyphVector = g2.getFont().createGlyphVector(g2.getFontRenderContext(), text);
            
            // get the shape object
            Shape textShape = glyphVector.getOutline();
            
            // activate anti aliasing for text rendering (if you want it to look nice)
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setColor(this.fontOutlineColor);
            g2.setStroke(outlineStroke);
            g2.draw(textShape); // draw outline
            
            if(color == null)
                g2.setColor(this.fontFillColor);
            else
                g2.setColor(color);
            g2.fill(textShape); // fill the shape

            // reset to original settings after painting
            g2.setColor(originalColor);
            g2.setStroke(originalStroke);
            g2.setRenderingHints(originalHints);
            g = (Graphics)g2;
        }
    }
}
