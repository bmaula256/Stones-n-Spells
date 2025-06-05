package main.CharacterResources.Items;

import main.CharacterResources.Creature;

/**
 * An item which boosts the attack of its owner whenever it is active.
 * Must be owned by a Creature.
 */
public class WhetstoneItem extends Item
{

    private static final String IMAGE_FILE_NAME = "Whetstone.png";
    private boolean isActive;

    /**
     * Constructs WhetstoneItem.
     */
    public WhetstoneItem()
    {
        super(IMAGE_FILE_NAME);
        isActive = false;
    }


    /**
     * Performs the core function of this item, which is to set creature attack +1
     * @param creature A Creature object to be modified by the Whetstone.
     */
    @Override
    public void activateEffect(Creature creature) {
        if(!isActive) {
            creature.setAtk(creature.getAtk() + 1);
            isActive = true;
        }
    }

    /**
     * Reverses the core function of this item, which is to set creature attack +1
     * @param creature A Creature object to be modified by the Whetstone.
     */
    @Override
    public void deactivateEffect(Creature creature)
    {
        if(isActive) {
            creature.setAtk(creature.getAtk() - 1);
            isActive = false;
        }
    }
}
