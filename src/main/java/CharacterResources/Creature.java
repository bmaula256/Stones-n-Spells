package CharacterResources;

import CharacterResources.Enemies.Enemy;
import CharacterResources.Items.*;
import CharacterResources.Player.Player;
import Collision.CollisionEntity;
import Collision.Projectile;
import GUIDesign.GamePlayComponent;
import Collision.*;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.InputMismatchException;

/**
 * Abstract class representing a Creature that can move and has combat stats.
 * This class serves to facilitate common functions which entities can perform by interacting with the environment.
 * As an example, when assigning an Item, Item will require a Creature in order to use it.
 */
public abstract class Creature implements Collideable
{
    private final HashSet<CharacterResources.Items.Item> ITEMS = new HashSet<CharacterResources.Items.Item>();

    /**
     * The maximum health value the Creature is allowed to have at a given point.
     */
    protected int maxHP;
    /**
     * The current health value the Creature has at a given point.
     */
    protected int currentHp;
    /**
     * The attack damage which a Creature has.
     */
    protected int atk;
    /**
     * The speed which a Creature has. (The amount which it is set to move on every move call
     * @see #move(String direction, HashSet collideables)
     */
    protected int speed;

    /**
     * The current xPos of the Creature.
     */
    protected int xPos;

    /**
     * The current yPos of the Creature.
     */
    protected int yPos;
    /**
     * The current width of the creature. <b>Not to be confused with width of the actual creature image!</b>
     * @see #getImageWidth()
     */
    protected int width;
    /**
     * The current height of the creature. <b>Not to be confused with height of the actual creature image!</b>
     * @see #getImageHeight()
     */
    protected int height;

    /**
     * The GamePlayComponent which the creature is linked to and will be drawn to.
     * @see GamePlayComponent
     */
    protected GamePlayComponent gameWorld;

    /**
     * Constructs a Creature object with default hp, atk, speed, position, width, and height.
     * @param inGameWorld The component that the Creature will be drawn to, helps with bound detection in move method
     */
    public Creature(GamePlayComponent inGameWorld)
    {
        maxHP = 1;
        currentHp = maxHP;

        atk = 1;
        speed = 1;
        xPos = 400;
        yPos = 100;
        width = 50;
        height = 50;

        //Will help with bound detection in move method.
        gameWorld = inGameWorld;
    }

    /**
     * Constructs Creature with specified parameters.
     * @param inX Initial X position.
     * @param inY Initial Y position.
     * @param inWidth Creature width.
     * @param inHeight Creature height.
     * @param inGameWorld The parent GamePlayComponent which the Creature is being drawn to.
     */
    public Creature(int inX, int inY, int inWidth, int inHeight, GamePlayComponent inGameWorld)
    {
        maxHP = 1;
        currentHp = maxHP;

        atk = 1;
        speed = 1;
        xPos = inX;
        yPos = inY;
        width = inWidth;
        height = inHeight;

        //Will help with bound detection in move method.
        gameWorld = inGameWorld;
    }



    /**
     * Constructs a Creature object with specified hp, atk, speed, position, width, and height.
     * @param health The hp for the Creature.
     * @param attack The attack damage for the Creature.
     * @param speed The movement speed for the Creature.
     * @param x the x position of the top left corner of the creature.
     * @param y the y position of the top left corner of the creature.
     * @param width The width of the creature.
     * @param height The height of the creature.
     * @param inGameWorld The component that the Creature will be drawn to, helps with bound detection in move method.
     */
    public Creature(int health, int attack, int speed, int x, int y, int width, int height, GamePlayComponent inGameWorld)
    {
        maxHP = health;
        currentHp = maxHP;
        atk = attack;
        this.speed = speed;
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;

        //Will help with bound detection in move method.
        gameWorld = inGameWorld;
    }

    /**
     * Adds an item to the creature.
     * @param item And Item object to be added.
     */
    public void addItem(Item item)
    {
        ITEMS.add(item);

        //Item specific behavior on add.
        switch(item)
        {
            case WhetstoneItem w -> w.activateEffect(this);
            case WingBootsItem b -> b.activateEffect(this);
            case StoneHeartItem s -> s.activateEffect(this);
            case FireballScroll f -> f.assignOwner(this);
            default -> throw new InputMismatchException("Item not covered by Creature addItem switch-case");
        }
    }

    /**
     * Sets the max health for a creature object.
     * @param health the new health for the creature.
     */
    public void setMaxHP(int health)
    {
        maxHP = health;
    }

    /**
     * Gets the max health stat of a creature object.
     * @return the health stat of a creature object.
     */
    public int getMaxHP()
    {
        return maxHP;
    }

    /**
     * Mutator method for adjusting HP of Creature.
     * @param newHP An integer representing the new HP a creature should have.
     */
    public void setCurrentHP(int newHP)
    {
        currentHp = newHP;
    }

    /**
     * Getter method to return hp of the creature.
     * @return An integer representing the health the Creature object has.
     */
    public int getCurrentHP(){ return currentHp; }

    /**
     * Sets the attack damage for a creature object.
     * @param attack the new attack damage for the creature.
     */
    public void setAtk(int attack)
    {
        atk = attack;
    }

    /**
     * Gets the attack stat of a creature object.
     * @return the attack stat of a creature object.
     */
    public int getAtk()
    {
        return atk;
    }

    /**
     * Sets the movement speed for the creature object.
     * @param speed the new speed for the creature.
     */
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    /**
     * Gets the speed stat of a creature object.
     * @return the speed stat of a creature object.
     */
    public int getSpeed()
    {
        return speed;
    }

    /**
     * Sets the position of a creature object.
     * @param x the new x position.
     * @param y the new y position.
     */
    public void setPos(int x, int y)
    {
        if(x < gameWorld.getPreferredSize().width && x > 0
        && y < gameWorld.getPreferredSize().height && y > 0) {
            xPos = x;
            yPos = y;
        }
    }

    /**
     * Gets the x position of the top left corner creature object.
     * @return the x position the top left corner creature object.
     */
    public int getX()
    {
        return xPos;
    }

    /**
     * Gets the y position of the top left corner creature object.
     * @return the y position the top left corner creature object.
     */
    public int getY()
    {
        return yPos;
    }

    /**
     * Sets the dimensions of a creature object.
     * @param width the new width of the creature.
     * @param height the new height of the creature.
     */
    public void setDimensions(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of a creature object.
     * @return the width.
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * Gets the height of a creature object.
     * @return the height.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Returns a HashSet of all the Items the Creature object has.
     * @return A HashSet full of all the Items the Creature object has.
     */
    public HashSet<Item> getITEMS()
    {
        return ITEMS;
    }

    /**
     * Should be implemented so that classes which draw Creatures define the width of the image, including transparent space.
     * @return Width of image associated with Creature, including transparent space.
     */
    public abstract int getImageWidth();

    /**
     * Should be implemented so that classes which draw Creatures define the height of the image, including transparent space.
     * @return Height of image associated with Creature, including transparent space.
     */
    public abstract int getImageHeight();

    /**
     * Draws the Creature in the specified Graphics window.
     * @param window the Graphics window.
     */
    public void draw(Graphics window)
    {
        window.fillRect(xPos,yPos,width, height);
    }

    /**
     * Moves the Creature according to the specified direction.
     * @param direction the direction in which to move.
     * @param collideables An array of Hashsets, precondition for this method to work is for all hashsets to have a generic type of an object which implements collideable.
     */
    public void move(String direction, HashSet<Collideable> collideables) {

        if(!canMove(direction, collideables))
            return;

        switch (direction.toUpperCase()) {
            case "N":
                if(yPos - speed >= 0)
                    yPos-=speed;
                break;
            case "E":
                if(xPos + speed + width <= gameWorld.getWidth())
                    xPos += speed;
                break;
            case "S":
                if(yPos + speed + height <= gameWorld.getHeight())
                    yPos += speed;
                break;
            case "W":
                if(xPos - speed >= 0)
                    xPos -= speed;
                break;
        }
    }

    //Checks if attempted movement will collide with any other collideables.

    /**
     * Checks if Creature can move in specified direction. With the context of all collideables within the HashSet.
     * @param direction Direction of attempted movement as type String.
     * @param collideables Collision.Collideable HashSet which contains collideables to be checked against.
     * @return A boolean value which determines whether or not player can move in specified direction.
     */
    public boolean canMove(String direction, HashSet<Collideable> collideables)
    {
            for(Collideable other : collideables)
            {
                if(!canMove(direction,other))
                {
                    return false;
                }
            }
        return true;
    }

    /**
     * Checks if Creature can move in specified direction. With the context of one specific Collision.Collideable.
     * See Collideables Hashset version to check multiple entities when you are trying to be unspecific.
     * This will check if the two Collideables are obstacles, if either of them are not, returns true.
     * This always returns true if the Player attempts to move into a Creature which is instance of type Enemy.
     * @param direction Direction of attempted movement as type String.
     * @param other Collision.Collideable to be checked against.
     * @return A boolean value which determines whether or not player can move in specified direction.
     */
    public boolean canMove(String direction, Collideable other) {
        if (this instanceof Player && other instanceof Enemy)
            return true;
        else if (this instanceof Enemy && other instanceof Player)
            return true;
        else if (this instanceof Enemy && other instanceof Projectile)
            return true;
        else if (!(isObstacle() || other.isObstacle()))
            return true;

        CollisionEntity tester;
        switch (direction.toUpperCase()) {
            case "N" -> tester = new CollisionEntity(xPos, yPos - speed, width, height, gameWorld, this);
            case "E" -> tester = new CollisionEntity(xPos+speed,yPos,width,height,gameWorld, this);
            case "W" -> tester = new CollisionEntity(xPos-speed,yPos,width,height,gameWorld, this);
            case "S" -> tester = new CollisionEntity(xPos, yPos + speed, width,height,gameWorld, this);
            default -> throw new InputMismatchException("Direction doesn't map properly");

        }

        if(tester.collides(other)!=null) {

            if(this instanceof Player)
                System.out.println( super.toString() + " " + toString() + " Collides with " + other.toString());
            return false;
        }

        return true;

    }

    /**
     * Necessary to satisfy Collision.Collideable interface. Declares a Creature as an obstacle.
     * This will make move methods of other Collideables that also return true for this method ensure creatures cannot overlap.
     * @return A boolean which determines whether a Collision.Collideable is an obstacle.
     */
    @Override
    public boolean isObstacle()
    {
        return true;
    }

    /**
     * Returns string version of Creature data.
     * @return Returns string version of Creature data.
     */
    public String toString()
    {
        return String.format("X pos: %d Y pos: %d Width: %d Height: %d", xPos, yPos, width, height);
    }
}
