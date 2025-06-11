package CharacterResources.Items;

import CharacterResources.Creature;
import CharacterResources.Player.Player;
import GUIDesign.GamePlayComponent;

/**
 * ActiveItem which manages creating Fireballs!
 */
public class FireballScroll extends ActiveItem{

    private static final String FIREBALL_NAME = "FireballScroll.png";

    /**
     * Constructs FireBallScroll with relevant parameters.
     * @param parentComponent The GamePlayComponent to be linked to.
     */
    public FireballScroll(GamePlayComponent parentComponent) {
        super(FIREBALL_NAME,parentComponent);

    }

    /**
     * Creates a fireball on the Creature's position and sets it to move in the direction the Creature is facing.
     * @param creature The Creature to activate the Item's effect on.
     */
    @Override
    public void activateEffect(Creature creature)
    {
        if(creature instanceof Player) {
            switch (((Player) creature).getDirection()) {
                case "N" -> getParentComponent().getCurrentRoomRef().addProjectile(
                        new Fireball(getOwner().getImageCenterX(), getOwner().getImageCenterY(),
                                new String[]{"N"}, getParentComponent(), this));
                case "S" -> getParentComponent().getCurrentRoomRef().addProjectile(
                        new Fireball(getOwner().getImageCenterX(), getOwner().getImageCenterY(),
                                new String[]{"S"}, getParentComponent(), this));
                case "W" -> getParentComponent().getCurrentRoomRef().addProjectile(
                        new Fireball(getOwner().getImageCenterX(), getOwner().getImageCenterY(),
                                new String[]{"W"}, getParentComponent(), this));
                case "E" -> getParentComponent().getCurrentRoomRef().addProjectile(
                        new Fireball(getOwner().getImageCenterX(), getOwner().getImageCenterY(),
                                new String[]{"E"}, getParentComponent(), this));
            }
        }
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
