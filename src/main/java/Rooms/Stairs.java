package Rooms;

import Collision.Collideable;
import GUIDesign.GamePlayComponent;

import javax.swing.*;
import java.awt.*;

/**
 * A class representing the stairs which a player can traverse to swap between rooms.
 */
public class Stairs implements Collideable {

    /**
     * An enum in collideable for handling directions.
     * Unfortunately, very underutilized in the grand scheme of things.
     */
    public enum Direction  {NORTH,SOUTH,WEST,EAST}
    private final ImageIcon NORTHSTAIRS = new ImageIcon(getClass().getClassLoader().getResource("StairsNorth.png"));
    private final ImageIcon SOUTHSTAIRS = new ImageIcon(getClass().getClassLoader().getResource("StairsSouth.png"));
    private final ImageIcon EASTSTAIRS = new ImageIcon(getClass().getClassLoader().getResource("StairsEast.png"));
    private final ImageIcon WESTSTAIRS = new ImageIcon(getClass().getClassLoader().getResource("StairsWest.png"));
    private int xPos, yPos;
    private GamePlayComponent parent;
    private ImageIcon usedImageIcon;

    private Direction direction;
    private Integer nextRoom;

    /**
     * Constructs a Stairs object with the specified parameters.
     * @param parent The GamePlayComponent the stairs are to be drawn to.
     * @param inDirection The direction which the stairs are supposed to represent.
     * @param nextRoom The room reference number which this set of Stairs points to.
     */
    public Stairs(GamePlayComponent parent, Direction inDirection, int nextRoom)
    {
        super();
        direction = inDirection;
        this.nextRoom = nextRoom;
        switch(direction)
        {
            case NORTH:
                xPos = parent.getPreferredSize().width*7/16;
                yPos = 0;
                usedImageIcon = NORTHSTAIRS;
                break;
            case SOUTH:
                xPos = parent.getPreferredSize().width*7/16;
                yPos = parent.getPreferredSize().height*11/12;
                usedImageIcon = SOUTHSTAIRS;
                break;
            case WEST:
                xPos = 0;
                yPos = parent.getPreferredSize().height*5/12;
                usedImageIcon = WESTSTAIRS;
                break;
            case EAST:
                xPos = parent.getPreferredSize().width*15/16;
                yPos = parent.getPreferredSize().height*5/12;
                usedImageIcon = EASTSTAIRS;
                break;
        }
    }

    /**
     * Draws Stairs object to GamePlayComponent.
     * @param g The Graphics component to be drawn to.
     */
    public void drawStairs(Graphics g)
    {
        usedImageIcon.paintIcon(parent,g,xPos,yPos);
    }

    /**
     * Returns the direction which the stairs are representing.
     * @return The direction which the stairs are representing. Returned as an enum which represents different directions.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Gets the integer number of the next room.
     * @return the integer of the next room.
     */
    public Integer getNextRoom()
    {
        return nextRoom;
    }

    /**
     * Returns the x-position of the Stairs object.
     * @return An integer representing the Stairs object's x-position.
     */
    @Override
    public int getX() {
        return xPos;
    }

    /**
     * Returns the y-position of the Stairs object.
     * @return An integer representing the Stairs object's y-position.
     */
    @Override
    public int getY()
    {
        return yPos;
    }

    /**
     * Returns the width of the Stairs object.
     * @return An integer representing the Stairs object's width.
     */
    @Override
    public int getWidth()
    {
        return usedImageIcon.getIconWidth();
    }

    /**
     * Returns the height of the Stairs object.
     * @return An integer representing the Stairs object's height.
     */
    @Override
    public int getHeight()
    {
        return usedImageIcon.getIconHeight();
    }

    /**
     * Effectively the same as getWidth() for this object.
     * @return The width of the image.
     */
    @Override
    public int getImageWidth()
    {
        return usedImageIcon.getIconWidth();
    }

    /**
     * Effectively the same as getHeight() for this object.
     * @return The height of the image.
     */
    @Override
    public int getImageHeight()
    {
        return usedImageIcon.getIconHeight();
    }

    /**
     * Returns boolean value which helps decide how collision works on the implementing class's level.
     * @return Always false.
     */
    @Override
    public boolean isObstacle()
    {
        return false;
    }
}
