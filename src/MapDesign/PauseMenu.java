package MapDesign;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PauseMenu implements KeyListener{

    private boolean isActive;
    private GamePlayComponent parent;
    private static final int BORDER_SIZE = 5;
    public PauseMenu(GamePlayComponent parent)
    {
        this.parent = parent;
        isActive = false;
        parent.addKeyListener(this);
    }

    /**
     * Draws the pause menu.
     * @param g The Graphics context to be drawn to.
     */
    public void drawPauseMenu(Graphics g)
    {
        g.setColor(Color.BLUE);
        for(int i =0; i<BORDER_SIZE; i++) {
            g.drawRect(parent.getWidth() / 3 +i, i, parent.getWidth() / 3- (i*2), parent.getHeight() - (i*2));
        }
        g.setColor(Color.BLACK);
        g.fillRect(parent.getWidth()/3+BORDER_SIZE, BORDER_SIZE, parent.getWidth()/3-BORDER_SIZE*2, parent.getHeight()-BORDER_SIZE*2);
    }

    /**
     * Returns whether the pause menu is active.
     * @return A boolean representing whether the pause menu is active.
     */
    public boolean isActive()
    {
        return isActive;
    }

    /**
     * Processes input related to the pause menu. Works on key press.
     * When the escape key is pressed, the game toggles the pause menu and toggles whether the room is active.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //Toggle pause menu
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE && !isActive) {
            isActive = true;
            parent.getCurrentRoomRef().deactivateRoom();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            isActive = false;
            parent.getCurrentRoomRef().activateRoom();
        }

    }

    /**
     * Processes input related to the pause menu. Works on key released.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Does nothing, needed to satisfy key listener.
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {}
}
