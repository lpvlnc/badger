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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
    public static boolean updateFps;
    public static int frames = 0;
    
    public int heartFrames = 0;
    public int heartMaxFrames = 5;
    public int heartPos = 0;
    
    
    // font
    public static InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("./font/prstart.ttf");
    public static Font pixelFont;
    Color fontOutlineColor = Color.black;
    Color fontFillColor = Color.white;
    
    
    public UI() throws IOException{
        initSprites();
        
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
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
    }
    
    public void render(Graphics graphics){
        this.g = graphics;
        g.setFont(pixelFont);
        renderLife();
        renderChocolate();
        showFPS();
    }
    
    public void showFPS(){
        if(Game.showFps) {
            drawText("FPS:" + frames, 854, 20);
        }
    }
    
    public void renderChocolate() {
        drawText("CHOCOLATE:", 303, 20);
        for(int i = 0; i < 5; i++) {
            g.drawImage(chocolateBack, 154 + (i * 30), -24, null);
        }
        
        for(int i = 0; i < Game.player.chocolate; i++) {
            g.drawImage(chocolate, 154, -24, null);
        }
    }
    
    public void renderLife() {
        drawText("LIFE:", 4, 20);
        if(Game.player.steroid) {
            for(int i = 0; i < 5; i++){
                g.drawImage(heartBack, 79 +  (i * 24), -15, null);
            }
            g.drawImage(heart, 79 + (heartPos * 24), -15, null);
            heartFrames++;
            if(heartFrames == heartMaxFrames) {
                heartFrames = 0;
                heartPos++;
                if(heartPos > 4){
                    heartPos = 0; 
                }
            }
        } else if(Game.player.weak) {
            for(int i = 0; i < 5; i++){
                g.drawImage(weakHeartBack, 79 +  (i * 24), -15, null);
            }
            g.drawImage(weakHeart, 79, -15, null);
        } else {
            for(int i = 0; i < 5; i++){
                g.drawImage(heartBack, 79 +  (i * 24), -15, null);
            }
            for(int i = 0; i <= Game.player.life; i++){
                g.drawImage(heart, 79 + (i * 24), -15, null);
            }
        }
        
    }
    
    public void drawText(String text, int x, int y) {

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

            g2.setColor(this.fontFillColor);
            g2.fill(textShape); // fill the shape

            // reset to original settings after painting
            g2.setColor(originalColor);
            g2.setStroke(originalStroke);
            g2.setRenderingHints(originalHints);
        }
    }
}
