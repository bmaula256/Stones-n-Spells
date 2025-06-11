package CharacterResources.Items;

import CharacterResources.Enemies.Enemy;
import Collision.*;
import GUIDesign.GamePlayComponent;
import GUIDesign.Updateable;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * A class which represents a Projectile subclass for a fireball.
 * Fireball flies in a consistent direction until it collides and explodes!
 */
public class Fireball extends Projectile implements Updateable {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final int DEFAULT_SPEED = 2;
    private static final int DEFAULT_DAMAGE = 3;

    //Radius of explosion, as of writing, every tile is 50px
    private static final int EXPLOSION_RADIUS = 150;
    private static final int DEFAULT_KNOCKBACK = 100;

    private static final int FRAME_CONSTANT = 10;
    private int frameCD;
    private boolean exploded;
    private FireballScroll ownerScroll;

    private ImageIcon FRAME_1 = new ImageIcon(getClass().getClassLoader().getResource("Fireball1.png"));
    private ImageIcon FRAME_2 = new ImageIcon(getClass().getClassLoader().getResource("Fireball2.png"));;
    private ImageIcon FRAME_3 = new ImageIcon(getClass().getClassLoader().getResource("Fireball3.png"));;

    //Image not added yet.
    //private ImageIcon EXPLOSION_DRAWING = new ImageIcon(getClass().getClassLoader().getResource("Explosion.png"));

    /**
     * Constructs fireball with associated parameters.
     * @param x The x coordinate of the fireball.
     * @param y The y coordinate of the fireball.
     * @param targetCenterX The X center of the target.
     * @param targetCenterY The Y center of the target.
     * @param inGamePlayComponent The GamePlayComponent to be drawn to.
     */
    public Fireball (int x, int y, int targetCenterX, int targetCenterY, GamePlayComponent inGamePlayComponent, FireballScroll ownerScroll) {
        super(x,y,DEFAULT_WIDTH,DEFAULT_HEIGHT,DEFAULT_SPEED, DEFAULT_DAMAGE, targetCenterX,targetCenterY, inGamePlayComponent, inGamePlayComponent.getCurrentRoomRef());
        obstacle = FRAME_1;
        frameCD = FRAME_CONSTANT;
        exploded = false;
        this.ownerScroll = ownerScroll;
    }

    /**
     * Constructs fireball with associated parameters.
     * @param x The x coordinate of the fireball.
     * @param y The y coordinate of the fireball.
     * @param moveDirection An array of String directions. Options are N,S,W,E. Array is max size of 2.
     * @param inGamePlayComponent The GamePlayComponent to be drawn to.
     */
    public Fireball(int x, int y, String[] moveDirection, GamePlayComponent inGamePlayComponent, FireballScroll ownerScroll) {
        super(x,y,DEFAULT_WIDTH,DEFAULT_HEIGHT,DEFAULT_SPEED,DEFAULT_DAMAGE,moveDirection,inGamePlayComponent,inGamePlayComponent.getCurrentRoomRef());
        obstacle = FRAME_1;
        frameCD = FRAME_CONSTANT;
        exploded = false;
        this.ownerScroll = ownerScroll;
    }

    @Override
    public void drawObstacle(Graphics g) {
        super.drawObstacle(g);

    }

    @Override
    public void collisionEffect(Collideable collideable) {

        //Unfinished, relook.
        if(collideable != ownerScroll.getOwner()) {
            frameCD = FRAME_CONSTANT;
            exploded = true;
            //obstacle = EXPLOSION_DRAWING;

            HashSet<Collideable> collideables = getParentRoom().getCollideables();
            CollisionEntity collisionEntity = new CollisionEntity(getImageCenterX() - EXPLOSION_RADIUS,
                    getImageCenterY() - EXPLOSION_RADIUS, EXPLOSION_RADIUS * 2, EXPLOSION_RADIUS * 2, parent, this);

            for (Collideable c : collideables) {
                if (Collideable.circleSquareCollides(collisionEntity, c)) {
                    if (c instanceof Enemy)
                        ((Enemy) c).startDamage(DEFAULT_KNOCKBACK);
                }
            }
        }
    }



    @Override
    public void update(Object nullPoint)
    {
        //Changes Fireball frame every time amount of ticks equal to the frame constant passes.
        frameCD--;
        if(frameCD <= 0 && exploded)
            terminateProjectile();
        else if(frameCD <= 0) {
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
