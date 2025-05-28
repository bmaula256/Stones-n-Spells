package Main.CharacterResources.Enemies;

import Main.GUIDesign.GamePlayComponent;

import javax.swing.*;
import java.awt.*;

/**
 * An enemy by the name of "Evil-Rock." One of the most basic enemies in the game. It moves towards the player and deals damage on contact.
 */
public class EvilRock extends Enemy {

    private final ImageIcon SPRITE1 = new ImageIcon(getClass().getClassLoader().getResource("EvilRock.png"));

    /**
     * Constructs an EvilRock object.
     * @param x The initial x position.
     * @param y The initial y position.
     * @param inGameWorld The GamePlayComponent the EvilRock is being drawn to.
     */
    public EvilRock(int x, int y, GamePlayComponent inGameWorld)
    {
        super(x, y,50, 50, inGameWorld);
        maxHP = 2;
        currentHp = maxHP;
    }

    /**
     * Returns width of EvilRock image, including transparent space.
     * @return Width of EvilRock image, including transparent space.
     */
    @Override
    public int getImageWidth() {
        return SPRITE1.getIconWidth();
    }

    /**
     * Returns height of EvilRock image, including transparent space.
     * @return height of EvilRock image, including transparent space.
     */
    @Override
    public int getImageHeight() {
        return SPRITE1.getIconHeight();
    }

    /**
     * Draws the EvilRock.
     * @param window the Graphics context to be drawn to.
     */
    @Override
    public void draw(Graphics window) {
        SPRITE1.paintIcon(gameWorld,window,xPos,yPos);
    }
}
