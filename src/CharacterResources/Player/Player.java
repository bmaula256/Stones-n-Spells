package CharacterResources.Player;

import CharacterResources.Creature;
import GUIDesign.GamePlayComponent;

import java.awt.*;
import javax.swing.ImageIcon;
import java.util.HashSet;

/**
 * Abstract class which represents the player character. Defines all basic functions.
 */
public abstract class Player extends Creature
{
    /**
     * The amount of I-Frames in ms which the player should be invulnerable for after being hit.
     * This system is set to be changed to work with ticks instead of multithreading.
     */
    public static final int PLAYER_I_FRAMES = 2000; //In ms
    private Pickaxe pickaxe;
    private String direction;
    /**
     * The current ImageIcon to be drawn to the screen which represents the player.
     */
    protected ImageIcon currentPlayerImage;


    /**
     * Constructs player object with default stats.
     * @param inGameWorld GamePlayComponent to be drawn to.
     * @param spriteFileName The name of file for default sprite of the player when the game starts.
     */
    protected Player(GamePlayComponent inGameWorld, String spriteFileName)
    {
        super(inGameWorld);
        maxHP = 7;
        currentHp = maxHP;
        atk = 1; //DO NOT FORGET TO CHANGE BACK TO 1!!!!!!!!!!!!!!!!!!!!!!!!!!
        speed = 3;
        xPos = 200;
        yPos = 300;
        super.width = 24;
        super.height = 45;
        if(spriteFileName != null)
            currentPlayerImage = new ImageIcon(getClass().getClassLoader().getResource(spriteFileName));
        pickaxe = new Pickaxe(inGameWorld, this);
        direction = "S";

    }
    /**
     * Constructs a Player object with specified hp, atk, speed, position, width, and height.
     * @param health The hp for the Player.
     * @param attack The attack damage for the Player.
     * @param speed The movement speed for the Player.
     * @param x the x position of the top left corner of the Player.
     * @param y the y position of the top left corner of the Player.
     * @param width The width of the Player.
     * @param height The height of the Player.
     * @param inGameWorld The GamePlayComponent to be drawn to.
     */
    public Player(int health, int attack, int speed, int x, int y, int width, int height, GamePlayComponent inGameWorld)
    {
        super(health, attack, speed, x, y, width, height, inGameWorld);
    }

    /**
     * Returns the image width of the currently active sprite for the player.
     * @return The image width of the currently active sprite for the player.
     */
    @Override
    public int getImageWidth() {
        return currentPlayerImage.getIconWidth();
    }

    /**
     * Returns the image height of the currently active sprite for the player.
     * @return The image height of the currently active sprite for the player.
     */
    @Override
    public int getImageHeight() {
        return currentPlayerImage.getIconHeight();
    }

    /**
     * Get the direction the player is facing/moving
     * @return the direction the player is moving
     */
    public String getDirection()
    {
        return direction;
    }


    /**
     * Reduces player hp by one if their hp is greater than zero.
     * @param damage The amount of damage the player should take.
     */
    public void damagePlayer(int damage)
    {
        if(currentHp > 0)
            currentHp-=damage;
    }

    /**
     * Checks to see if the player is dead
     * @return A boolean flag which is true if player is at or below zero health. Otherwise, returns false.
     */
    public boolean isDead()
    {
        return currentHp <= 0;
    }


    /**
     * Draws the Player in the specified Graphics window.
     * @param window the Graphics window.
     */
    public void draw(Graphics window)
    {
        //For testing purposes.
        if(currentPlayerImage == null) {
            window.setColor(Color.YELLOW);
            window.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        else {
            currentPlayerImage.paintIcon(gameWorld,window,xPos,yPos);
            pickaxe.drawPickaxe(window);
        }

    }

    /**
     * Returns player's Pickaxe object
     * @return Returns player's Pickaxe object
     */
    public Pickaxe getPickaxe()
    {
        return pickaxe;
    }

    /**
     * An overridden variant of Creature's move method. This calls super and also calls updatePlayerImage().
     * @param direction The direction in which to move.
     * @param collideables A HashSet of type Collision.Collideable, used to check for collision in super call.
     */
    @Override
    public void move(String direction, HashSet<Collision.Collideable> collideables)
    {
        super.move(direction, collideables);
        this.direction = direction;
        updatePlayerImage(direction);
    }

    /**
     * Updates the Player's Image depending on the direction which it is moving.
     * @param direction The direction which the Player is now moving.
     */
    protected abstract void updatePlayerImage(String direction);
}
