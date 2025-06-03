package Main.CharacterResources.Items;

import Main.CharacterResources.Creature;
import Main.Collision.*;
import Main.GUIDesign.GamePlayComponent;

public class Fireball extends Projectile {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final int DEFAULT_SPEED = 2;
    private static final int DEFAULT_DAMAGE = 3;

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
    }

    @Override
    public void collisionEffect(Collideable collideable) {
        int fireballCenterX = getImageWidth();
        //CollisionEntity collisionEntity = new CollisionEntity();
    }

}
