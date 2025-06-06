package CharacterResources.Player;

import CharacterResources.Items.ActiveItem;
import Collision.CollisionEntity;
import CharacterResources.Creature;
import CharacterResources.Enemies.*;
import CharacterResources.Items.Item;
import Collision.Collideable;
import GUIDesign.GamePlayComponent;
import Rooms.Chest;
import GUIDesign.Updateable;

import java.awt.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.concurrent.locks.*;

/**
 * Class that handles pickaxe idle sprite and player attacks
 */
public class Pickaxe extends Creature implements Updateable
{
    //Animation constants.
    private static final int ANIMATIONDELAY = 15;
    private static final int ATTACKDURATION = ANIMATIONDELAY * 3;
    private static final int ATTACKDELAY = 10;
    /**
     * The distance in pixels which the default this pickaxe will knock enemies back by.
     * @see Enemy
     */
    public static final int KNOCKBACK_CONSTANT = 50;

    //Idle Pickaxe Sprites
    private final ImageIcon IDLE_NORTH = new ImageIcon(getClass().getClassLoader().getResource("PickaxeNorth.png"));
    private final ImageIcon IDLE_SOUTH = new ImageIcon(getClass().getClassLoader().getResource("PickaxeSouth.png"));
    private final ImageIcon IDLE_WEST = new ImageIcon(getClass().getClassLoader().getResource("PickaxeWest.png"));
    private final ImageIcon IDLE_EAST = new ImageIcon(getClass().getClassLoader().getResource("PickaxeEast.png"));

    //Attack North
    private final ImageIcon ATTACK_NORTH1 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashNorth1.png"));
    private final ImageIcon ATTACK_NORTH2 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashNorth2.png"));
    private final ImageIcon ATTACK_NORTH3 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashNorth3.png"));
    //Attack South
    private final ImageIcon ATTACK_SOUTH1 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashSouth1.png"));
    private final ImageIcon ATTACK_SOUTH2 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashSouth2.png"));
    private final ImageIcon ATTACK_SOUTH3 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashSouth3.png"));
    //Attack West
    private final ImageIcon ATTACK_WEST1 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashWest1.png"));
    private final ImageIcon ATTACK_WEST2 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashWest2.png"));
    private final ImageIcon ATTACK_WEST3 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashWest3.png"));
    //Attack East
    private final ImageIcon ATTACK_EAST1 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashEast1.png"));
    private final ImageIcon ATTACK_EAST2 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashEast2.png"));
    private final ImageIcon ATTACK_EAST3 = new ImageIcon(getClass().getClassLoader().getResource("PlayerSlashEast3.png"));

    /*
    //Animation cycles
    private static final ImageIcon[] ANIMATION_CYCLE_NORTH = new ImageIcon[] {ATTACK_NORTH1,ATTACK_NORTH2,ATTACK_NORTH3};
    private static final ImageIcon[] ANIMATION_CYCLE_SOUTH = new ImageIcon[] {ATTACK_SOUTH1,ATTACK_SOUTH2,ATTACK_SOUTH3};
    private static final ImageIcon[] ANIMATION_CYCLE_WEST = new ImageIcon[] {ATTACK_WEST1,ATTACK_WEST2,ATTACK_WEST3};
    private static final ImageIcon[] ANIMATION_CYCLE_EAST = new ImageIcon[] {ATTACK_EAST1,ATTACK_EAST2,ATTACK_EAST3};
     */


    private GamePlayComponent parentComponent;
    private Player owner;
    private ReentrantLock attackLock = new ReentrantLock();
    private int attackCount;

    /**
     * Constructs a Pickaxe with the specified parameters.
     * @param parentComponent The GamePlayComponent which the Pickaxe is to be drawn to.
     * @param owner The Player object which owns this Pickaxe.
     */
    public Pickaxe(GamePlayComponent parentComponent, Player owner)
    {
        super(parentComponent);
        this.owner = owner;
        this.parentComponent = parentComponent;
        attackCount = 0;
    }


    /**
     * Handles drawing the pickaxe. Also handles collision checking with enemies and chests.
     * @param g The Graphics which the Pickaxe is being drawn to.
     */
    public void drawPickaxe(Graphics g)
    {
        //UNFINISHED
        final int SPACE_CONSTANT = -2;
        final int ATTACK_SPACE_CONSTANT = 10;
        //Pickaxe is to the right of player when facing south

        //Checks to see if attack is in progress, if not, draw idle sprite.
        if(attackCount <= 0) {
            switch (owner.getDirection()) {
                case ("S"):
                    IDLE_SOUTH.paintIcon(parentComponent, g, xPos = owner.getX() + owner.getWidth() + SPACE_CONSTANT,
                            yPos = owner.getY() + owner.getHeight() / 3);
                    break;
                case ("N"):
                    IDLE_NORTH.paintIcon(parentComponent, g, xPos = owner.getX() + owner.getWidth() + SPACE_CONSTANT,
                            yPos = owner.getY() - owner.getHeight() / 24);
                    break;
                case ("E"):
                    IDLE_EAST.paintIcon(parentComponent, g, xPos = owner.getX() + owner.getWidth() / 6,
                            yPos = owner.getY() + owner.getHeight() / 3 + SPACE_CONSTANT);
                    break;
                case ("W"):
                    IDLE_WEST.paintIcon(parentComponent, g, xPos = owner.getX() - owner.getWidth() / 6,
                            yPos = owner.getY() + owner.getHeight() / 3 + SPACE_CONSTANT);
                    break;
            }
        }
        else
        {
            switch(owner.getDirection())
            {
                //37px from pickaxe handle to head.
                //43px from left to right (This one shouldn't matter)
                case("N"):
                    width = 50;
                    height = 37;
                    //System.out.println("North Called");
                    if(attackCount >= ATTACKDURATION*2/3)
                        ATTACK_NORTH1.paintIcon(parentComponent,g,xPos = owner.getX(),
                                yPos = owner.getY()-SPACE_CONSTANT-height);
                    else if(attackCount >= ATTACKDURATION/3)
                        ATTACK_NORTH2.paintIcon(parentComponent,g,xPos = owner.getX(),
                                yPos = owner.getY()-SPACE_CONSTANT-height);
                    else
                    {
                        ATTACK_NORTH3.paintIcon(parentComponent,g,xPos = owner.getX(),
                                yPos = owner.getY()-SPACE_CONSTANT-height);
                    }
                    //Handle collision here.
                    break;
                case("E"):
                    width = 37;
                    height = 50;
                    if(attackCount >= ATTACKDURATION*2/3)
                        ATTACK_EAST1.paintIcon(parentComponent,g,xPos = owner.getX()+ Collideable.PIXEL_CONSTANT ,
                                yPos = owner.getY());
                    else if(attackCount >= ATTACKDURATION/3)
                        ATTACK_EAST2.paintIcon(parentComponent,g,xPos = owner.getX()+ Collideable.PIXEL_CONSTANT ,
                                yPos = owner.getY());
                    else
                    {
                        ATTACK_EAST3.paintIcon(parentComponent,g,xPos = owner.getX()+ Collideable.PIXEL_CONSTANT ,
                               yPos = owner.getY());
                    }
                    break;
                case("S"):
                    width = 50;
                    height = 37;
                    if(attackCount >= ATTACKDURATION*2/3)
                        ATTACK_SOUTH1.paintIcon(parentComponent,g,xPos = owner.getX(),
                                yPos = owner.getY()+SPACE_CONSTANT+height);
                    else if(attackCount >= ATTACKDURATION/3)
                        ATTACK_SOUTH2.paintIcon(parentComponent,g,xPos = owner.getX(),
                                yPos = owner.getY()+SPACE_CONSTANT+height);
                    else
                    {
                        ATTACK_SOUTH3.paintIcon(parentComponent,g,xPos = owner.getX(),
                                yPos = owner.getY()+SPACE_CONSTANT+height);
                    }

                    break;
                case("W"):
                    width = 37;
                    height = 50;
                    if(attackCount >= ATTACKDURATION*2/3)
                        ATTACK_WEST1.paintIcon(parentComponent,g,xPos = owner.getX()-width - ATTACK_SPACE_CONSTANT,
                                yPos = owner.getY());
                    else if(attackCount >= ATTACKDURATION/3)
                        ATTACK_WEST2.paintIcon(parentComponent,g,xPos = owner.getX()-width - ATTACK_SPACE_CONSTANT,
                                yPos = owner.getY());
                    else
                    {
                        ATTACK_WEST3.paintIcon(parentComponent,g,xPos = owner.getX()-width - ATTACK_SPACE_CONSTANT,
                                yPos = owner.getY());
                    }
                    break;
            }

        }
    }

    /**
     * Returns an integer representing where in an attack phase the Pickaxe object is.
     * @return An integer which represent what part of an attack phase the Pickaxe is in. The returned value will be equal to or less than zero if no attack is currently in progress.
     */
    public int getAttackCount()
    {
        return attackCount;
    }

    /**
     * Sets attackCount to the attack duration constant.
     */
    public void initAttack()
    {
        attackCount = ATTACKDURATION;
    }

    /**
     * Returns width of idle pickaxe image.
     * @return Width of idle pickaxe image.
     */
    @Override
    public int getImageWidth()
    {
        return IDLE_NORTH.getIconWidth();
    }

    /**
     * Returns height of idle pickaxe image.
     * @return Height of idle pickaxe image.
     */
    @Override
    public int getImageHeight()
    {
        return IDLE_NORTH.getIconHeight();
    }

    /**
     * Checks through collision with Enemies for damage purposes, and also checks for collision with other relevant objects for interaction.
     * Does so on every tick of swing Timer in GamePlayComponent.
     * @param nullPoint Should be null, not required.
     * @see GamePlayComponent
     * @see javax.swing.Timer
     */
    @Override
    public void update(Object nullPoint)
    {
        if(attackCount > 0) {
            CollisionEntity tester = new CollisionEntity(xPos, yPos, width, height, parentComponent, owner);
            HashSet<Collideable> collideables = parentComponent.getCurrentRoomRef().getCollideables();
            for (Collideable c : collideables) {
                if (tester.collides(c) != null) {
                    //Start damage
                    if (c instanceof GolemBoss) {
                        ((Enemy) c).startDamage(0);
                    } else if (c instanceof Enemy)
                        ((Enemy) c).startDamage(KNOCKBACK_CONSTANT);
                    else if(c instanceof Chest && ((Chest)c).peekChest() instanceof ActiveItem)
                    {
                        Item temp = ((Chest)c).openChest();
                        addItemToWorld(temp);
                        ((ActiveItem)temp).assignOwner(owner);
                    }
                    else if (c instanceof Chest && ((Chest) c).peekChest() != null) {
                        //Do chest stuff.
                        Item temp = ((Chest) c).openChest();
                        addItemToWorld(temp);

                    }
                }
            }

            attackCount--;
        }
    }

    private void addItemToWorld(Item item)
    {
        parentComponent.getPlayer().addItem(item);
        //Add logic here later to handle UI spot for active item.
        parentComponent.getMainFrame().getTopUI().addItem(item);
        parentComponent.getMainFrame().getTopUI().repaint();//Call to repaint in order to update Indicator Component
    }
}
