package MapDesign;

import Collision.Collideable;
import GUIDesign.GamePlayComponent;

import javax.swing.*;
import java.awt.*;

/**
 * A basic class for representing obstacles which the player cannot move through.
 */
public class Obstacle implements Collideable
{
    /**
     * The ImageIcon which represents the actual image of the object to be drawn to the GamePlayComponent.
     * @see GamePlayComponent
     */
    protected ImageIcon obstacle;
    /**
     * The x and y positions of the object.
     */
    protected int xPos, yPos;
    /**
     * The GamePlayComponent which this object is linked and drawn to.
     */
    protected GamePlayComponent parent;
    private final ImageIcon OBSTACLE_DEFAULT = new ImageIcon(getClass().getClassLoader().getResource("ObstacleDefaultBlock.png"));


    /**
     * Constructs an Obstacle object.
     * @param parent The GameplayComponent this will be drawn to.
     * @param fileName A String representation of the filename which the image belongs to for the obstacle.
     * @param xPos Initial xPosition of the object.
     * @param yPos Initial yPosition of the object.
     */
    public Obstacle(GamePlayComponent parent, String fileName, int xPos, int yPos)
    {
        obstacle = new ImageIcon(getClass().getClassLoader().getResource(fileName));
        this.xPos = xPos;
        this.yPos = yPos;
        this.parent = parent;
    }

    /**
     * Constructs an Obstacle object.
     * @param parent The GameplayComponent this will be drawn to.
     * @param xPos Initial xPosition of the object.
     * @param yPos Initial yPosition of the object.
     */
    public Obstacle(GamePlayComponent parent, int xPos, int yPos)
    {
        obstacle = OBSTACLE_DEFAULT;
        this.xPos = xPos;
        this.yPos = yPos;
        this.parent = parent;
    }

    /**
     * Draws the obstacle to the GamePlayComponent.
     * @param g The Graphics component to be drawn to.
     */
    public void drawObstacle(Graphics g)
    {
        obstacle.paintIcon(parent, g, xPos, yPos);
    }

    /**
     * Changes the ImageIcon associated with the Obstacle.
     * @param inIcon The new ImageIcon.
     */
    public void setImageIcon(ImageIcon inIcon)
    {
        obstacle = inIcon;
    }

    /**
     * Returns the x-position of the Obstacle.
     * @return An integer representing the x-position of the Obstacle.
     */
    public int getX() {
        return xPos;
    }

    /**
     * Returns the y-position of the Obstacle.
     * @return An integer representing the y-position of the Obstacle.
     */
    public int getY()
    {
        return yPos;
    }

    /**
     * Returns the width of the Obstacle.
     * @return An integer representing the width of the Obstacle.
     */
    public int getWidth()
    {
        return obstacle.getIconWidth();
    }

    /**
     * Returns the height of the Obstacle.
     * @return An integer representing the height of the Obstacle.
     */
    public int getHeight()
    {
        return obstacle.getIconHeight();
    }

    @Override
    public int getImageWidth() {
        return obstacle.getIconWidth();
    }

    @Override
    public int getImageHeight() {
        return obstacle.getIconHeight();
    }

    /**
     * Returns true to signify this as an Obstacle which should deny movement to Creatures attempting to move through.
     * @return Always returns true.
     */
    public boolean isObstacle()
    {
        return true;
    }

    /**
     * Returns a String representation of the data contained within the Obstacle object.
     * @return A String representation of the data contained within the Obstacle object.
     */
    @Override
    public String toString()
    {
        return String.format("Object: %s at \nxPos: %d yPos: %d Width: %d Height: %d",super.toString(),xPos, yPos, getWidth(), getHeight());
    }


}
