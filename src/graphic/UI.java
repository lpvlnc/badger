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
import static java.util.Arrays.stream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Spring.height;
import main.Game;

/**
 *
 * @author Leonardo
 */
public class UI {
    private Graphics g;
    public static BufferedImage heart;
    public static boolean updateFps;
    public static int frames = 0;
    
    // font
    public static InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("./font/prstart.ttf");
    public static Font pixel_font;
    Color fontOutlineColor = Color.black;
    Color fontFillColor = Color.white;
    
    
    public UI() throws IOException{
        heart = Game.spritesheet.getSprite(608, 0, 15, 13);
        
        try {
            pixel_font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
        } catch (FontFormatException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void render(Graphics graphics){
        this.g = graphics;
        g.setFont(pixel_font);
        renderLife();
        showFPS();
        
        
        
        
        //drawText("CHOCOLATE: " + Game.player.chocolate, 20, 20);
        
    }
    
    public void showFPS(){
        if(Game.showFps) {
            drawText("FPS:" + frames, 854, 20);
        }
    }
    
    public void renderLife() {
        drawText("LIFE:", 4, 20);
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
