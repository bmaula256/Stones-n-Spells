package Main.CharacterResources.Items;

import Main.CharacterResources.Creature;
import Main.Collision.Collideable;
import Main.GUIDesign.GamePlayComponent;

import java.util.HashSet;

public class FireballScroll extends ActiveItem{

    private static final String FIREBALL_NAME = "Fireball.png";

    public FireballScroll(GamePlayComponent parentComponent) {
        super(FIREBALL_NAME,parentComponent);

    }

    @Override
    public void activateEffect(Creature creature)
    {
        //getParentComponent().getCurrentRoomRef().addProjectile(new Fireball());
    }


    /**
     * Does nothing in this case, because one does not simply deactivate fireball.
     * @param creature The Creature to deactivate the Item's effect on.
     */
    @Override
    public void deactivateEffect(Creature creature)
    {
        //One does not simply deactivate fireball
    }

}
