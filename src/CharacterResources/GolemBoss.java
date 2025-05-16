package CharacterResources;

import MapDesign.Collideable;
import MapDesign.GamePlayComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImagingOpException;
import java.util.HashSet;
import java.util.Stack;

/**
 * A class which represents the golem boss that appears at the end of the adventure.
 */
public class GolemBoss extends Enemy
{
    //sprites
    private final ImageIcon GOLEM_IDLE = new ImageIcon("GolemBossIdle.png");
    private final ImageIcon GOLEM_WALK_1 = new ImageIcon(getClass().getClassLoader().getResource("GolemBossWalk1.png"));
    private final ImageIcon GOLEM_WALK_2 = new ImageIcon(getClass().getClassLoader().getResource("GolemBossWalk2.png"));
    private final ImageIcon GOLEM_DRILL_IDLE =  new ImageIcon(getClass().getClassLoader().getResource("GolemDrillIdle.png"));
    //Attack Sprites
    private final ImageIcon GOLEM_SLASH1 =  new ImageIcon(getClass().getClassLoader().getResource("GolemSlash1.png"));
    private final ImageIcon GOLEM_SLASH2 =  new ImageIcon(getClass().getClassLoader().getResource("GolemSlash2.png"));
    private final ImageIcon GOLEM_SLASH3 =  new ImageIcon(getClass().getClassLoader().getResource("GolemSlash3.png"));
    private final ImageIcon GOLEM_SLASH4 =  new ImageIcon(getClass().getClassLoader().getResource("GolemSlash4.png"));
    private final ImageIcon GOLEM_SLASH5 =  new ImageIcon(getClass().getClassLoader().getResource("GolemSlash5.png"));
    private final ImageIcon GOLEM_BLAST1 =  new ImageIcon(getClass().getClassLoader().getResource("GolemBlast1.png"));
    private final ImageIcon GOLEM_BLAST2 =  new ImageIcon(getClass().getClassLoader().getResource("GolemBlast2.png"));

    //instance varis
    private static final int BLAST_CD = 300;
    private static final int DRILL_ATTACK_RANGE = 100;
    private static final int DRILL_ATTACK_CD = 400;
    private static final int DRILL_ANIMATION_CD = 100;
    private static final int WALK_CD = 15;

    private int blastCD;
    private int drillCD;
    private int drillAnimationCD;
    private boolean walkFlag;
    private int walkCD;

    private final HashSet<GolemBlast> BLASTS = new HashSet<GolemBlast>();
    private final Stack<GolemBlast> removeBlastStack = new Stack<GolemBlast>();


    /**
     * Constructs a GolemBoss with the specified parameters.
     * @param x The initial X-position of the GolemBoss.
     * @param y The initial Y-position of the GolemBoss.
     * @param inGameWorld The GamePlayComponent the GolemBoss is being drawn to.
     */
    public GolemBoss(int x, int y, GamePlayComponent inGameWorld)
    {
        super(15,2,1,x,y,100,100,inGameWorld);
        blastCD = BLAST_CD;
        drillCD = DRILL_ATTACK_CD;
        drillAnimationCD = 0;
        walkFlag = true;
        walkCD = WALK_CD;
    }

    /**
     * Returns width of GolemBoss image.
     * @return Width of GolemBoss image.
     */
    @Override
    public int getImageWidth() {
        return GOLEM_IDLE.getIconWidth();
    }

    /**
     * Returns height of GolemBoss image.
     * @return Height of GolemBoss image.
     */
    @Override
    public int getImageHeight() {
        return GOLEM_IDLE.getIconHeight();
    }

    /**
     * Handles the logic for which way the enemy object should move.
     * @param playerCenterX the x position of the center of the player.
     * @param playerCenterY the y position of the center of the player.
     */
    @Override
    public void pathFinding(int playerCenterX, int playerCenterY, HashSet<Collideable> collideables)
    {
        int enemyCenterX = getX() + getWidth()/2;
        int enemyCenterY = getY() + getHeight()/2;
        int xDistance = Math.abs(enemyCenterX - playerCenterX);
        int yDistance = Math.abs(enemyCenterY - playerCenterY);
        if ((xDistance < DRILL_ATTACK_RANGE && yDistance < DRILL_ATTACK_RANGE) && (drillCD < 0 && drillAnimationCD <= 0))
        {
            //Starts attack.
            drillAnimationCD = DRILL_ANIMATION_CD;
            drillCD = DRILL_ATTACK_CD;
        }
        else if (blastCD < 0 && xDistance >= DRILL_ATTACK_RANGE && yDistance >= DRILL_ATTACK_RANGE)
        {
            magicBlast(playerCenterX, playerCenterY);
            blastCD = BLAST_CD;
        }
        else
        {
            drillCD--;
            blastCD--;
        }
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

        //For blast movement
        for(GolemBlast b : BLASTS)
        {
            b.move(collideables);
        }

        while(!removeBlastStack.isEmpty())
            BLASTS.remove(removeBlastStack.pop());
    }

    /**
     * Returns an integer representing the current animation count which the Golem drill is on.
     * Counts down from a constant which defines the duration of the animation.
     * @return An integer representing the current animation count which the Golem drill is on.
     */
    public int getDrillAnimationCD()
    {
        return drillAnimationCD;
    }

    /**
     * Shoots a blast at the player by adding it to the Golem.
     * @param playerCenterX X position at the center of the player.
     * @param playerCenterY Y position at the center of the player.
     */
    public void magicBlast(int playerCenterX, int playerCenterY)
    {
        BLASTS.add(new GolemBlast(gameWorld, this, playerCenterX, playerCenterY));
    }

    /**
     * Removes a blast from the Golem.
     * @param blast The blast to remove.
     */
    public void removeBlast(GolemBlast blast)
    {
        removeBlastStack.push(blast);
    }




    /**
     * Draws the GolemBoss and associated projectiles.
     * @param window the Graphics context to be drawn to.
     */
    public void draw(Graphics window)
    {
        if(walkCD <= 0 && walkFlag) {
            walkFlag = false;
            walkCD = WALK_CD;
        }
        else if(walkCD <= 0 && !walkFlag) {
            walkFlag = true;
            walkCD = WALK_CD;
        }
        if(walkFlag)
            GOLEM_WALK_1.paintIcon(gameWorld,window,xPos,yPos);
        else{
            GOLEM_WALK_2.paintIcon(gameWorld,window,xPos,yPos);
        }
        walkCD--;

        if (drillAnimationCD <= 0)
        {
            GOLEM_DRILL_IDLE.paintIcon(gameWorld,window,xPos,yPos);
        }
        //Will be greater than zero when set to such in Pathfinding().
        else
        {
            drillSwing(window);
        }
        for(GolemBlast b : BLASTS)
        {
            b.drawObstacle(window);
        }
    }

    private void drillSwing(Graphics window)
    {
        drillAnimationCD--;
        //Do draw stuff.
        if (drillAnimationCD >= DRILL_ANIMATION_CD*5/6)
        {
            GOLEM_SLASH1.paintIcon(gameWorld,window,xPos,yPos);
        }
        else if (drillAnimationCD >= DRILL_ANIMATION_CD*4/6)
        {
            GOLEM_SLASH2.paintIcon(gameWorld,window,xPos,yPos);
        }
        else if (drillAnimationCD >= DRILL_ANIMATION_CD*3/6)
        {
            GOLEM_SLASH3.paintIcon(gameWorld,window,xPos,yPos);
        }
        else if (drillAnimationCD >= DRILL_ANIMATION_CD*2/6)
        {
            GOLEM_SLASH4.paintIcon(gameWorld,window,xPos,yPos);
        }
        else
        {
            GOLEM_SLASH5.paintIcon(gameWorld,window,xPos,yPos);
        }
    }

}
