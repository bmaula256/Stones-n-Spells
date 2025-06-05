package main.CharacterResources.Items;

import main.CharacterResources.Creature;

/**
 * An item which boost the speed of its owner whenever it is active.
 * Must be owned by a Creature.
 */
public class WingBootsItem extends Item
{
    private static final String IMAGE_FILE_NAME = "WingBoots.png";
    private boolean isActive;

    /**
     * Constructs WingBootsItem.
     */
    public WingBootsItem()
    {
        super(IMAGE_FILE_NAME);
        isActive = false;
    }


    /**
     * Performs the core function of this item, which is to set creature speed +1
     * @param creature A Creature object to be modified by the Wing Boots.
     */
    @Override
    public void activateEffect(Creature creature) {
        if(!isActive) {
            creature.setSpeed(creature.getSpeed() + 1);
            isActive = true;
        }
    }

    /**
     * Reverses the core function of this item, which is to set creature speed +1
     * @param creature A Creature object to be modified by the Wing Boots.
     */
    @Override
    public void deactivateEffect(Creature creature)
    {
        if(isActive) {
            creature.setSpeed(creature.getSpeed() - 1);
            isActive = false;
        }
    }
}
