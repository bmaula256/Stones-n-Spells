package CharacterResources.Items;

import Collision.*;
import GUIDesign.GamePlayComponent;
import GUIDesign.Updateable;

import javax.swing.*;
import java.awt.*;

/**
 * A class which represents a Projectile subclass for a fireball.
 * Fireball flies in a consistent direction until it collides and explodes!
 */
public class Fireball extends Projectile implements Updateable {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final int DEFAULT_SPEED = 2;
    private static final int DEFAULT_DAMAGE = 3;

    private static final int FRAME_CONSTANT = 10;
    private int frameCD;

    private ImageIcon FRAME_1 = new ImageIcon(getClass().getClassLoader().getResource("Fireball1.png"));
    private ImageIcon FRAME_2 = new ImageIcon(getClass().getClassLoader().getResource("Fireball2.png"));;
    private ImageIcon FRAME_3 = new ImageIcon(getClass().getClassLoader().getResource("Fireball3.png"));;

    /**
     * Constructs fireball with associated parameters.
     * @param x The x coordinate of the fireball.
     * @param y The y coordinate of the fireball.
     * @param targetCenterX The X center of the target.
     * @param targetCenterY The Y center of the target.
     * @param inGamePlayComponent The GamePlayComponent to be drawn to.
     */
    public Fireball (int x, int y, int targetCenterX, int targetCenterY, GamePlayComponent inGamePlayComponent) {
        super(x,y,DEFAULT_WIDTH,DEFAULT_HEIGHT,DEFAULT_SPEED, DEFAULT_DAMAGE, targetCenterX,targetCenterY, inGamePlayComponent, inGamePlayComponent.getCurrentRoomRef());
        obstacle = FRAME_1;
        frameCD = FRAME_CONSTANT;
    }

    @Override
    public void drawObstacle(Graphics g) {
        super.drawObstacle(g);

    }

    @Override
    public void collisionEffect(Collideable collideable) {

        //CollisionEntity collisionEntity = new CollisionEntity();
    }

    @Override
    public void update(Object nullPoint)
    {
        frameCD--;
        if(frameCD <= 0) {
            frameCD = FRAME_CONSTANT;
            if(obstacle == FRAME_1)
                obstacle = FRAME_2;
            else if (obstacle == FRAME_2)
                obstacle = FRAME_3;
            else
                obstacle = FRAME_1;
        }
    }

}
