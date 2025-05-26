package MapDesign.Rooms;

import CharacterResources.Enemies.EvilRock;
import CharacterResources.Enemies.SoundBat;
import CharacterResources.Items.Item;
import GUIDesign.GamePlayComponent;

import java.awt.*;

/**
 * A specialized class for a combat room a player can encounter in their adventure.
 */
public class CombatRoom1 extends Room {

    /**
     * Constructs a CombatRoom1 object with the following parameters.
     * @param parentComponent The GamePlayComponent the room is to be drawn to.
     * @param stairID The stairID associated with the room.
     * @param inItem An item to be put in a chest within the room. Pass null into this parameter if there is no chest in this room.
     */
    public CombatRoom1(GamePlayComponent parentComponent, int stairID, Item inItem)
    {
        super(parentComponent,stairID);

        //Add enemies for combat room.
        addEnemy(new SoundBat(parentComponent.getPreferredSize().width/8,
                parentComponent.getPreferredSize().height/6, parentComponent));
        addEnemy(new SoundBat(parentComponent.getPreferredSize().width*12/16,
                parentComponent.getPreferredSize().height*9/12, parentComponent));
        addEnemy(new EvilRock(parentComponent.getPreferredSize().width*5/8,
                parentComponent.getPreferredSize().height/3, parentComponent));
        addEnemy(new EvilRock(parentComponent.getPreferredSize().width*5/16,
                parentComponent.getPreferredSize().height*2/3,parentComponent));

        //Add obstacles to room
            //X row

        for(int x = 3; x < 14; x++)
        {
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*x/16,
                    parentComponent.getPreferredSize().height/4));
        }

        for(int y = 3; y < 11; y++)
        {
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*3/16,
                    parentComponent.getPreferredSize().height*y/12));
        }

        if(inItem != null)
            //REMEMBER TO DO ITEM WORK
            addObstacle(new Chest(parentComponent,parentComponent.getPreferredSize().width*11/16,
                    parentComponent.getPreferredSize().height*6/12, inItem));
        else
            addObstacle(new Obstacle(parentComponent,"MineCart.png",parentComponent.getPreferredSize().width*11/16,
                parentComponent.getPreferredSize().height*6/12));

    }

    /**
     * Draws the room to the associated Graphics context. First calls super version, then checks if the room has any enemies.
     * If all enemies are dead, then by extension the room has no enemies, and the stairs are activated.
     * @param g The graphics context that the room is drawn to.
     */
    @Override
    public void drawRoom(Graphics g) {
        super.drawRoom(g);
        if(getEnemies().isEmpty())
            activateStairs();
    }
}
