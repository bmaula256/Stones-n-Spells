package main.GUIDesign;

import main.Updateable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.*;

/**
 * Class which represents pause menu for the game.
 * When pause menu is active, it will freeze the game.
 * Can be toggled on and off with togglePauseMenu method.
 * @see #togglePauseMenu()
 * @see GamePlayComponent
 */
public class PauseMenu implements KeyListener, ActionListener, Updateable {

    private final HashSet<JButton> ACTIVE_BUTTON_SET = new HashSet<JButton>();

    private JButton resumeButton;
    private JButton inventoryButton;
    private JButton settingsButton;
    private JButton saveButton;
    private JButton exitButton;

    private boolean isActive;
    private GamePlayComponent parent;
    private static final int BORDER_SIZE = 5;

    private int xPos;
    private int yPos;
    private int width, height;

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTONS_Y_OFFSET = 80;
    private static final int BUTTON_SPACING = 20;

    private int buttonWidth;
    private int buttonHeight;


    /**
     * Constructor of PauseMenu object with associated parameters
     * @param parent GamePlayComponent to draw to and interact with.
     */
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

        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);

        inventoryButton = new JButton("Inventory");
        inventoryButton.addActionListener(this);

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        ImageIcon exitIcon = new ImageIcon((new ImageIcon(getClass().getClassLoader().getResource("Exit.png"))).getImage().getScaledInstance(buttonWidth,buttonHeight,0));
        exitButton = new JButton(exitIcon);
        exitButton.addActionListener(this);
    }

    /**
     * Draws the pause menu.
     * @param g The Graphics context to be drawn to.
     */
    public void drawPauseMenu(Graphics g)
    {
        //Main background
        g.setColor(Color.BLACK);
        g.fillRect(xPos, yPos, width, height);

        g.setColor(Color.WHITE);
        drawCenteredStringX(g, "--Paused--", new Font("Pause font", Font.PLAIN,24), yPos + height/16);

        //The Border
        g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, BORDER_SIZE, height);
        g.fillRect(xPos,yPos,width,BORDER_SIZE);
        g.fillRect(xPos+width-BORDER_SIZE,yPos,BORDER_SIZE,height);
        g.fillRect(xPos,yPos+height-BORDER_SIZE,width,BORDER_SIZE);


    }

    /**
     * Toggles the pause menu on and off.
     */
    public void togglePauseMenu()
    {
        if(!isActive)
        {
            isActive = true;
            parent.getCurrentRoomRef().deactivateRoom();
        }
        else{
            isActive = false;
            parent.getCurrentRoomRef().activateRoom();
        }
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
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            togglePauseMenu();
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
        if(e.getSource() == resumeButton)
        {
            togglePauseMenu();
            System.out.println("Resume called");
        }

        if(e.getSource() == inventoryButton)
        {
            //Nothing happens yet.
        }

        if(e.getSource() == settingsButton)
        {
            //Nothing happens yet.
        }

        if(e.getSource() == saveButton)
        {
            //Nothing happens yet.
        }

        if(e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    /**
     * Detects button inputs when the pause menu is active.
     * If the pause menu is active, the update method will add any buttons to the parent GamePlayComponent that have not been added yet.
     * If the pause menu is not active, the update method will remove any buttons from the parent GamePlayComponent that have been added.
     * Parent GamePlayComponent handles painting of buttons via Component hierarchy.
     * @param nullPoint Should be null, does nothing.
     * @see GamePlayComponent
     * @see GamePlayComponent#add(Component)
     */
    @Override
    public void update(Object nullPoint)
    {
        if(isActive) {
            /*
            if (exitButton != null && ACTIVE_BUTTON_SET.add(exitButton)) {
                System.out.println("Button added");
                exitButton.setBorder(BorderFactory.createEmptyBorder());
                exitButton.setContentAreaFilled(false);
                exitButton.setBounds(xPos+ BORDER_SIZE,yPos+height-BORDER_SIZE-buttonHeight,buttonWidth,buttonHeight);
                parent.add(exitButton);

             */
            addButton(resumeButton);
            addButton(inventoryButton);
            addButton(settingsButton);
            addButton(saveButton);
            addButton(exitButton);
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

    //Adds button to pause menu.
    private void addButton(JButton button)
    {
        if(button != null && ACTIVE_BUTTON_SET.add(button)) {
            button.setBounds(centerButtonX(), yPos + BUTTONS_Y_OFFSET + (buttonHeight + BUTTON_SPACING)*(ACTIVE_BUTTON_SET.size()-1)+BORDER_SIZE, buttonWidth, buttonHeight);
            parent.add(button);
        }
    }

    private void drawCenteredStringX(Graphics g, String text, Font font, int yPos)
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = xPos + (width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = yPos  + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    private int centerButtonX()
    {
        return xPos + (width - buttonWidth)/2;
    }
}
