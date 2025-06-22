package handlers;

import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import static main.Game.player;
import static main.Game.showFps;
import static main.Game.showHitBox;

public class KeyHandler implements KeyListener {
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (Objects.requireNonNull(Game.state) == Game.State.NORMAL) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_UP)
                player.up = true;

            if (keyEvent.getKeyCode() == KeyEvent.VK_A || keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
                player.left = true;

            if (keyEvent.getKeyCode() == KeyEvent.VK_S || keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
                player.down = true;

            if (keyEvent.getKeyCode() == KeyEvent.VK_D || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
                player.right = true;

            if (keyEvent.getKeyCode() == KeyEvent.VK_SHIFT)
                if (player.energy > 0 && player.isMoving)
                    player.startRunning();

            if (keyEvent.getKeyCode() == KeyEvent.VK_H)
                showHitBox = !showHitBox;

            if (keyEvent.getKeyCode() == KeyEvent.VK_F)
                showFps = !showFps;

            if (keyEvent.getKeyCode() == KeyEvent.VK_CONTROL)
                player.useSteroid();

            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE && player.hasCrown)
                player.xRay = !player.xRay;

            if (keyEvent.getKeyCode() == KeyEvent.VK_E && player.hasInvisibilityGadget)
                player.invisible = !player.invisible;

            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                player.action = true;

            if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                try {
                    Game.changeGameState(Game.State.PAUSE);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (Game.state == Game.State.MENU || Game.state == Game.State.PAUSE || Game.state == Game.State.GAMEOVER || Game.state == Game.State.END) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_UP)
                Game.menu.up = true;

            if (keyEvent.getKeyCode() == KeyEvent.VK_S || keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
                Game.menu.down = true;

            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER || keyEvent.getKeyCode() == KeyEvent.VK_SPACE)
                Game.menu.select = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (Objects.requireNonNull(Game.state) == Game.State.NORMAL) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_UP)
                player.up = false;

            if (keyEvent.getKeyCode() == KeyEvent.VK_A || keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
                player.left = false;

            if (keyEvent.getKeyCode() == KeyEvent.VK_S || keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
                player.down = false;

            if (keyEvent.getKeyCode() == KeyEvent.VK_D || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
                player.right = false;

            if (keyEvent.getKeyCode() == KeyEvent.VK_SHIFT)
                player.stopRunning();

            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                player.action = false;
        }
    }
}
