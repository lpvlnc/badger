package graphics;

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

import main.Game;
import world.World;

public class UI {
    private Graphics graphics;
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
    public static BufferedImage crownBack;
    public static BufferedImage crown;
    public static BufferedImage detectorBack;
    public static BufferedImage detector;
    public static boolean updateFps;
    public static int frames = 0;
    public int animationFrames = 0;
    public int animationMaxFrames = 10;
    public int animationPos = 0;

    // Font
    public static Font pixelFont;
    Color fontOutlineColor = Color.black;
    Color fontFillColor = Color.white;

    public UI() throws IOException, FontFormatException {
        initializeSprites();
        pixelFont = Font.createFont(Font.TRUETYPE_FONT, Game.inputStream).deriveFont(16f);
    }

    public void initializeSprites() {
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

        crownBack = Game.spritesheet.getSprite(512, 64, World.TILE_SIZE, World.TILE_SIZE);
        crown = Game.spritesheet.getSprite(512, 32, World.TILE_SIZE, World.TILE_SIZE);

        detectorBack = Game.spritesheet.getSprite(512, 128, World.TILE_SIZE, World.TILE_SIZE);
        detector = Game.spritesheet.getSprite(512, 96, World.TILE_SIZE, World.TILE_SIZE);
    }

    public void render(Graphics g) {
        this.graphics = g;
        graphics.setFont(pixelFont);
        renderLife();
        renderEnergy();
        if(Game.level == 1)
            renderChocolate();
        else
            renderParchment();
        renderSteroid();
        renderScore();
        renderCrown();
        renderDetector();
        showFPS();
    }

    public void showFPS() {
        if(Game.showFps)
            drawText("FPS:" + frames, 854, 606, null);
    }

    public void renderCrown() {
        graphics.drawImage(crownBack, -753, 0, null);
        if(Game.player.hasCrown)
            graphics.drawImage(crown, -753, 0, null);
    }

    public void renderDetector() {
        graphics.drawImage(detectorBack, -753, 20, null);
        if(Game.player.hasDetector) {
            graphics.drawImage(detector, -753, 20, null);
            graphics.setColor(new Color(128, 128, 128));
            graphics.fillRect(-752, 50, (Game.player.detectingMaxCounter / 64) * 5, 5);
            graphics.setColor(new Color(0, 206, 209));
            graphics.fillRect(-752, 50, (Game.player.detectingCounter / 64) * 5, 5);
        }
    }

    public void renderScore() {
        StringBuilder score = new StringBuilder(String.valueOf(Game.player.score));
        while(score.length() < 6)
            score.insert(0, "0");
        drawText("SCORE:" + score, 756, 20, null);
    }

    public void renderSteroid() {
        graphics.drawImage(steroid, -437, 558, null);
        String zero = Game.player.steroidCounter < 10 ? "0" : "";
        drawText("x" + zero + Game.player.steroidCounter, 26, 604, null);
    }

    public void renderChocolate() {
        drawText("CHOCOLATE:", 437, 20, null);
        for(int i = 0; i < 5; i++)
            graphics.drawImage(chocolateBack, 154 + (i * 30), -24, null);
        for(int i = 0; i < Game.player.chocolateCounter; i++)
            graphics.drawImage(chocolate, 154 + (i * 30), -24, null);
    }

    public void renderParchment() {
        drawText("PARCHMENT:", 437, 20, null);
        for(int i = 0; i < 10; i ++) {
            if(i < 5)
                graphics.drawImage(parchmentBack, 160 + (i * 30), -19, 24, 24, null);
            else
                graphics.drawImage(parchmentBack, 160 + ((i - 5) * 30), 7, 24, 24, null);
        }
        for(int i = 0; i < Game.player.parchmentCounter; i ++) {
            if(i < 5)
                graphics.drawImage(parchment, 160 + (i * 30), -19, 24, 24, null);
            else
                graphics.drawImage(parchment, 160 + ((i - 5) * 30), 7, 24, 24, null);
        }
    }

    public void renderEnergy() {
        drawText("ENERGY:", 221, 20, null);
        if (Game.player.onSteroid) {
            for(int i = 0; i < 5; i++)
                graphics.drawImage(energyBack, 101 + (i * 17), -24, null);
            graphics.drawImage(energy, 101 + (animationPos * 17), -24, null);
            animationFrames++;
            if(animationFrames == animationMaxFrames) {
                animationFrames = 0;
                animationPos++;
                if(animationPos > 4)
                    animationPos = 0;
            }
        } else {
            for(int i = 0; i < 5; i++)
                graphics.drawImage(energyBack, 101 + (i * 17), -24, null);
            if(!Game.player.weak) {
                for(int i = 0; i < 5 * Game.player.energy / 100; i++)
                    graphics.drawImage(energy, 101 + (i * 17), -24, null);
            }
        }
    }

    public void renderLife() {
        drawText("LIFE:", 4, 20, null);
        if(Game.player.onSteroid) {
            for(int i = 0; i < 5; i++)
                graphics.drawImage(heartBack, 79 +  (i * 24), -15, null);
            graphics.drawImage(heart, 79 + (animationPos * 24), -15, null);
            animationFrames++;
            if(animationFrames == animationMaxFrames) {
                animationFrames = 0;
                animationPos++;
                if(animationPos > 4)
                    animationPos = 0;
            }
        } else if(Game.player.weak) {
            for(int i = 0; i < 5; i++)
                graphics.drawImage(weakHeartBack, 79 +  (i * 24), -15, null);
            if(Game.player.life >= 1)
                graphics.drawImage(weakHeart, 79, -15, null);
        } else {
            for(int i = 0; i < 5; i++)
                graphics.drawImage(heartBack, 79 +  (i * 24), -15, null);
            for(int i = 0; i < Game.player.life; i++)
                graphics.drawImage(heart, 79 + (i * 24), -15, null);
        }
    }

    private Graphics applyTextEffect(Graphics2D graphics2d, AffineTransform affineTransform, String text, Color color) {
        graphics2d.setTransform(affineTransform);

        // Remember original settings
        Color originalColor = graphics2d.getColor();
        Stroke originalStroke = graphics2d.getStroke();
        RenderingHints originalHints = graphics2d.getRenderingHints();

        // Create a glyph vector from your text
        GlyphVector glyphVector = graphics2d.getFont().createGlyphVector(graphics2d.getFontRenderContext(), text);

        // Get the shape object
        Shape textShape = glyphVector.getOutline();

        // Activate antialiasing for text rendering
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2d.setColor(this.fontOutlineColor);
        graphics2d.setStroke(new BasicStroke(2.0f));
        graphics2d.draw(textShape); // draw outline

        if(color == null)
            graphics2d.setColor(this.fontFillColor);
        else
            graphics2d.setColor(color);
        graphics2d.fill(textShape); // fill the shape

        // Reset to original settings after painting
        graphics2d.setColor(originalColor);
        graphics2d.setStroke(originalStroke);
        graphics2d.setRenderingHints(originalHints);
        return (Graphics)graphics2d;
    }

    public void drawText(String text, int x, int y, Color color) {
        if (graphics instanceof Graphics2D graphics2d) {
            AffineTransform affineTransform = AffineTransform.getTranslateInstance(x, y);
            affineTransform.scale(1, 1);
            graphics = applyTextEffect(graphics2d, affineTransform, text, color);
        }
    }

    public void drawTextCenter(String text, int y, Color color) {
        if (graphics instanceof Graphics2D graphics2d) {
            FontMetrics metrics = graphics.getFontMetrics(graphics2d.getFont());
            int xx = ((Game.WIDTH * Game.SCALE ) - metrics.stringWidth(text)) / 2;
            AffineTransform affineTransform = AffineTransform.getTranslateInstance(xx, y);
            affineTransform.scale(1, 1);
            graphics = applyTextEffect(graphics2d, affineTransform, text, color);
        }
    }

    public void drawTextCenterTips(String text, int y, Color color) {
        if (graphics instanceof Graphics2D graphics2d) {
            FontMetrics metrics = graphics.getFontMetrics(graphics2d.getFont());
            double xx = ((Game.WIDTH * Game.SCALE ) - metrics.stringWidth(text)) / 1.75;
            AffineTransform affineTransform = AffineTransform.getTranslateInstance(xx, y);
            affineTransform.scale(0.8, 0.8);
            graphics = applyTextEffect(graphics2d, affineTransform, text, color);
        }
    }
}
