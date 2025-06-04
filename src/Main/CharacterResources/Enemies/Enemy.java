package Main.CharacterResources.Enemies;

import Main.CharacterResources.Creature;
import Main.Collision.Collideable;
import Main.GUIDesign.GamePlayComponent;
import java.awt.*;
import java.util.HashSet;
import java.util.concurrent.locks.*;

/**
 * Abstract class which defines and implements the basic functions of an enemy which the player will face in their adventure.
 */
public abstract class Enemy extends Creature //Make sure to make abstract later
{
    /**
     * The amount of repaints which must pass before the Enemy object can be damaged again.
     * Likely to be based off ticks in the future, like many other repaint based features.
     */
    public static final int ENEMY_I_FRAMES = 900;
    private final ReentrantLock healthLock = new ReentrantLock();

    /**
     * Initializes enemy for testing purposes with default xPos, yPos, width and height.
     * No longer used.
     * @param inGameWorld The GamePlayComponent which the enemy is associated with.
     */
    @Deprecated
    public Enemy(GamePlayComponent inGameWorld)
    {
        super(inGameWorld);
    }

    /**
     * Constructs an enemy of specified size and position with default stats.
     * @param x Initial x position
     * @param y Initial y position
     * @param width Enemy width.
     * @param height Enemy height.
     * @param inGameWorld The GamePlayComponent the Enemy is being drawn to.
     */
    public Enemy(int x, int y, int width, int height, GamePlayComponent inGameWorld)
    {
        super(inGameWorld);
        xPos = x;
        yPos = y;
        super.width = width;
        super.height = height;
    }

    /**
     * Constructs an Enemy with specified stats.
     * @param health The max health which the Enemy will have.
     * @param attack The attack the Enemy will have.
     * @param speed The speed the Enemy will have.
     * @param x The initial x position of the Enemy.
     * @param y The initial y position of the Enemy.
     * @param width The width of the Enemy.
     * @param height The height of the Enemy.
     * @param inGameWorld The GamePlayComponent the Enemy is being drawn to.
     */
    public Enemy(int health, int attack, int speed, int x, int y, int width, int height , GamePlayComponent inGameWorld)
    {
        super(health, attack, speed, x, y, width, height, inGameWorld);
    }

    /**
     * Handles the logic for which way the Enemy object should move.
     * In the default implementation, the enemy continuously moves towards the player.
     * @param playerCenterX the x position of the center of the player.
     * @param playerCenterY the y position of the center of the player.
     * @param collideables A HashSet of type Collideable which contains all possible things which Enemy could collide with.
     * @see Collideable
     */
    public void pathFinding(int playerCenterX, int playerCenterY, HashSet<Collideable> collideables)
    {
        int enemyCenterX = getImageCenterX();
        int enemyCenterY = getImageCenterY();
        int xDistance = Math.abs(enemyCenterX - playerCenterX);
        int yDistance = Math.abs(enemyCenterY - playerCenterY);
        if (xDistance > 0)
        {
            if (enemyCenterX > playerCenterX)
            {
                super.move("W", collideables);
            }
            else
            {
                super.move("E", collideables);
            }
        }
        if (yDistance > 0)
        {
            if (enemyCenterY > playerCenterY)
            {
                super.move("N", collideables);
            }
            else
            {
                super.move("S", collideables);
            }
        }
    }


    /**
     * Draws the Enemy. Likely to be overridden
     * @param window the Graphics context to be drawn to.
     */
    public void draw(Graphics window)
    {
        window.setColor(Color.MAGENTA);
        window.fillRect(xPos,yPos,width,height);
    }

    /**
     * Starts the damage on the enemy. Implements I-frames to ensure double counts are not done.
     * Also knocks back the Enemy. The Enemy may be knocked into another Enemy or Obstacle. This will freeze the enemy, this is intended behavior.
     * @param knockback The amount which the Enemy is to be knocked back.
     */
    public void startDamage(int knockback) {
        class IFramesRunnable implements Runnable {
            public IFramesRunnable()
            {
                //Nothing here
            }
            public void run() {
                if(healthLock.tryLock()) {
                    try {
                        switch (gameWorld.getPlayer().getDirection()) {
                            case ("N") -> setPos(xPos, yPos - knockback);
                            case ("S") -> setPos(xPos, yPos + knockback);
                            case ("W") -> setPos(xPos - knockback, yPos);
                            case ("E") -> setPos(xPos + knockback, yPos);
                        }
                        currentHp -= gameWorld.getPlayer().getAtk();
                        System.out.println("Enemy damage called");
                        Thread.sleep(ENEMY_I_FRAMES);
                    } catch (InterruptedException e) {
                        System.out.println("ZZZZ");
                    } finally {
                        healthLock.unlock();
                    }
                }
            }
        }
        IFramesRunnable r1 = new IFramesRunnable();
        Thread t1 = new Thread(r1);
        t1.start();
    }
    /**
     * Overridden so Enemy counts as obstacle, this is ignored by the player during a move operation, but not other entities.
     * @return Returns true.
     */
    @Override
    public boolean isObstacle() {
        return true;
    }
}
