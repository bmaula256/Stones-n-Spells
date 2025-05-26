package CharacterResources.Enemies;

import CharacterResources.Creature;
import CharacterResources.Player.Player;
import Collision.Projectile;
import GUIDesign.GamePlayComponent;

import javax.swing.*;
import java.awt.*;

/**
 * The projectile object which the Golem-Boss sends at the player.
 */
public class GolemBlast extends Projectile
{
    private static final String BLAST1 = "GolemBlast1.png";
    private final ImageIcon BLAST2 = new ImageIcon(getClass().getClassLoader().getResource("GolemBlast2.png"));
    private static final int PROJECTILE_WIDTH = 50;
    private static final int PROJECTILE_HEIGHT = 50;

    private static final int DEFAULT_SPEED = 3;
    private static final int MAX_ANIMATION_CD = 10;
    private final GolemBoss PARENT_GOLEM_BOSS;

    private int animationCD;
    private boolean animationFlag;

    /**
     *
     * @param parent The GamePlayComponent the GolemBlast is being drawn to.
     * @param boss The boss that owns the object.
     * @param playerCenterX The X-center of the player when the projectile is launched.
     * @param playerCenterY The Y-center of the player when the projectile is launched.
     */
    public GolemBlast(GamePlayComponent parent, GolemBoss boss, int playerCenterX, int playerCenterY)
    {
        //super(parent, boss.getX() + boss.getWidth() / 2, boss.getY() + boss.getHeight() / 3);
        super(boss.getX(), boss.getY(), PROJECTILE_WIDTH, PROJECTILE_HEIGHT, DEFAULT_SPEED, boss.getAtk(),playerCenterX,playerCenterY,parent);

        obstacle = new ImageIcon(getClass().getClassLoader().getResource(BLAST1));
        PARENT_GOLEM_BOSS = boss;
        animationFlag = true;
        animationCD = 0;
    }

    /**
     * Overrides drawObstacle to alternate between drawing two different sprites of the same projectile for animation.
     * @param g The Graphics component to be drawn to.
     */
    @Override
    public void drawObstacle(Graphics g) {
        if(animationCD <= 0 && animationFlag) {
            animationCD = MAX_ANIMATION_CD;
            animationFlag = false;
        }

        else if(animationCD<= 0 && !animationFlag) {
            animationCD = MAX_ANIMATION_CD;
            animationFlag = true;
        }

        if(animationFlag){
            super.drawObstacle(g);
        }
        else{
            BLAST2.paintIcon(parent,g,xPos,yPos);
        }

        animationCD--;
    }

    /**
     * Removes this GolemBlast object from the Golem-Boss.
     */
    @Override
    public void terminateProjectile()
    {
        PARENT_GOLEM_BOSS.removeBlast(this);
    }

    /**
     * Damages player then terminates projectile.
     * @param creature The creature to potentially apply effect to.
     */
    @Override
    public void collisionEffect(Creature creature) {
        if(creature instanceof Player)
        {
            parent.startPlayerDamage(getAttack());
            terminateProjectile();
        }
    }
}
