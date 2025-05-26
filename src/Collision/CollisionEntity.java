package Collision;

import CharacterResources.Creature;
import GUIDesign.GamePlayComponent;

/**
 * A class to represent an entity to test collision with.
 */
public class CollisionEntity extends Creature {

    Collision.Collideable parent;

    /**
     * Constructs collision entity with specified parameters.
     * @param inX X location
     * @param inY Y location
     * @param inWidth Width of entity.
     * @param inHeight Height of entity
     * @param inGameWorld GameplayComponent to be checked on.
     * @param parent The parent object initiating the collision.
     */
    public CollisionEntity(int inX, int inY, int inWidth, int inHeight, GamePlayComponent inGameWorld, Collideable parent)
    {
        super(inX, inY, inWidth, inHeight, inGameWorld);
        this.parent = parent;
    }

    /**
     * Checks to see if parent is instance that is being compared to.
     * @param other Collision.Collideable which it is being compared to
     * @return boolean value, returns true if parent is other object, false if not.
     */
    @Override
    public boolean equals(Object other) {
        Collideable temp = (Collideable) other;
        return parent == temp;
    }

    /**
     * Returns zero because collision entities do not have any associated images.
     * @return Returns zero because collision entities do not have any associated images.
     */
    @Override
    public int getImageWidth() {
        return 0;
    }

    /**
     * Returns zero because collision entities do not have any associated images.
     * @return Returns zero because collision entities do not have any associated images.
     */
    @Override
    public int getImageHeight() {
        return 0;
    }
}
