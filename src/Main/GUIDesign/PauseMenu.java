package Main.GUIDesign;

import Main.Updateable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.*;

public class PauseMenu implements KeyListener, ActionListener, Updateable {

    private ImageIcon exitIcon;
    private JButton exitButton;
    private final HashSet<JButton> ACTIVE_BUTTON_SET = new HashSet<JButton>();
    private boolean isActive;
    private GamePlayComponent parent;
    private static final int BORDER_SIZE = 5;
    private int xPos;
    private int yPos;

    private int width, height;

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private int buttonWidth;
    private int buttonHeight;


    public PauseMenu(GamePlayComponent parent)
    {
        this.parent = parent;
        isActive = false;
        parent.addKeyListener(this);

        xPos = parent.getPreferredSize().width/3;
        yPos = parent.getPreferredSize().height/12;
        width = parent.getPreferredSize().width/3;
        height = parent.getPreferredSize().height*10/12;
        buttonWidth = BUTTON_WIDTH;
        buttonHeight = BUTTON_HEIGHT;

        exitIcon = new ImageIcon((new ImageIcon(getClass().getClassLoader().getResource("Exit.png"))).getImage().getScaledInstance(buttonWidth,buttonHeight,0));
        exitButton = new JButton(exitIcon);
        exitButton.addActionListener(this);
    }

    /**
     * Draws the pause menu.
     * @param g The Graphics context to be drawn to.
     */
    public void drawPauseMenu(Graphics g)
    {
        g.setColor(Color.BLUE);

        g.fillRect(xPos, yPos, BORDER_SIZE, height);
        g.fillRect(xPos,yPos,width,BORDER_SIZE);
        g.fillRect(xPos+width-BORDER_SIZE,yPos,BORDER_SIZE,height);
        g.fillRect(xPos,yPos+height-BORDER_SIZE,width,BORDER_SIZE);

        g.setColor(Color.BLACK);
        g.fillRect(parent.getWidth()/3+BORDER_SIZE, parent.getHeight()/12 + BORDER_SIZE, parent.getWidth()/3-BORDER_SIZE*2, parent.getHeight()*10/12 -BORDER_SIZE*2);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    /**
     * Detects button inputs when the pause menu is active.
     * If the pause menu is active, the update method will add any buttons to the parent GamePlayComponent that have not been added yet.
     * If the pause menu is not active, the update method will remove any buttons from the parent GamePlayComponent that have been added.
     * Parent GamePlayComponent handles painting of buttons via Component hierarchy.
     * @see GamePlayComponent
     * @see GamePlayComponent#add(Component)
     */
    @Override
    public void update()
    {
        if(isActive) {
            if (exitButton != null && ACTIVE_BUTTON_SET.add(exitButton)) {
                System.out.println("Button added");
                exitButton.setBorder(BorderFactory.createEmptyBorder());
                exitButton.setContentAreaFilled(false);
                exitButton.setBounds(xPos+ BORDER_SIZE,yPos+height-BORDER_SIZE-buttonHeight,buttonWidth,buttonHeight);
                parent.add(exitButton);
            }
        }
        else {
            Stack<JButton> removeButtons = new Stack<JButton>();
            for(JButton button : ACTIVE_BUTTON_SET) {
                removeButtons.push(button);
            }
            while(!removeButtons.isEmpty())
            {
                JButton temp = removeButtons.pop();
                parent.remove(temp);
                ACTIVE_BUTTON_SET.remove(temp);
            }
        }

    }
}
