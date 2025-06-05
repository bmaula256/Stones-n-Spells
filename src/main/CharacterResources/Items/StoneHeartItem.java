package main.CharacterResources.Items;

import main.CharacterResources.Creature;

/**
 * An item which functions to boost the max health of its owner while it is active.
 * Must be owned by a Creature.
 */
public class StoneHeartItem extends Item
{
    private static final String IMAGE_FILE_NAME = "StoneHeart.png";
    private boolean isActive;

    /**
     * Constructs StoneHeartItem.
     */
    public StoneHeartItem()
    {
        super(IMAGE_FILE_NAME);
        isActive = false;
    }


    /**
     * Performs the core function of this item, which is to set creature max hp +2 and fully heals the creature
     * @param creature A Creature object to be modified by the Stone Heart.
     */
    @Override
    public void activateEffect(Creature creature)
    {
        if(!isActive)
        {
            creature.setMaxHP(creature.getMaxHP() + 2);
            creature.setCurrentHP(creature.getMaxHP());
            isActive = true;
        }
    }

    /**
     * Reverses the core function of this item, which is to set creature max hp +2 and fully heals the creature
     * @param creature A Creature object to be modified by the Stone Heart.
     */
    @Override
    public void deactivateEffect(Creature creature)
    {
        if(isActive) {
            creature.setMaxHP(creature.getMaxHP() - 2);
            //Ensures no HP overflow.
            if(creature.getCurrentHP() > creature.getMaxHP())
                creature.setCurrentHP(creature.getMaxHP());
            isActive = false;
        }
    }
}
