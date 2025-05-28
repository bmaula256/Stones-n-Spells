package Main.Rooms;
import Main.CharacterResources.Enemies.EvilRock;
import Main.CharacterResources.Enemies.SoundBat;
import Main.CharacterResources.Items.Item;
import Main.GUIDesign.GamePlayComponent;

import java.awt.*;
import java.util.Random;

/**
 * A specialized class for a combat room a player can encounter in their adventure.
 */
public class CombatRoom3 extends Room {

    /**
     * Constructs a CombatRoom2 object with the following parameters.
     * @param parentComponent The GamePlayComponent the room is to be drawn to.
     * @param stairID The stairID associated with the room.
     * @param inItem An item to be put in a chest within the room. Pass null into this parameter if there is no chest in this room.
     */
    public CombatRoom3(GamePlayComponent parentComponent, int stairID, Item inItem)
    {
        super(parentComponent, stairID);

        //Enemies (Once elites are in the game, these should be elite)

            //Bats
            addEnemy(new SoundBat(parentComponent.getPreferredSize().width*6/16,
                    parentComponent.getPreferredSize().height*2/12, parentComponent));
            addEnemy(new SoundBat(parentComponent.getPreferredSize().width*6/16,
                    parentComponent.getPreferredSize().height*9/16, parentComponent));

            //Rocks
            addEnemy(new EvilRock(parentComponent.getPreferredSize().width*5/16,
                    parentComponent.getPreferredSize().height*5/12, parentComponent));
            addEnemy(new EvilRock(parentComponent.getPreferredSize().width*9/16,
                    parentComponent.getPreferredSize().height*9/12,parentComponent));

        //Obstacles

            //Top-left
            for(int y = 0; y < 5; y++)
            {
                addSimpleObstacle(4,y);
                addSimpleObstacle(4,12-y);
            }
            addSimpleObstacle(5,4);
            addSimpleObstacle(5,8);

            //Top-Right
            for(int y = 0; y < 5; y++)
            {
                addSimpleObstacle(11,y);
                addSimpleObstacle(11,12-y);
            }
            addSimpleObstacle(10,4);
            addSimpleObstacle(10,8);

        //Chest TIME!
        Random rand = new Random();
        int[] chestPos;
        int[] minecartPos;
        if(rand.nextInt(2) == 0) {
            chestPos = new int[]{2, 10};
            minecartPos = new int[] {13,2};
        }
        else{
            chestPos = new int[] {13,2};
            minecartPos = new int[]{2,10};
        }
        if(inItem != null)
        {
            addObstacle(new Chest(parentComponent, parentComponent.getPreferredSize().width * chestPos[0] / 16,
                    parentComponent.getPreferredSize().height * chestPos[1] / 12, inItem));
            addObstacle(new Obstacle(parentComponent, "MineCart.png", parentComponent.getPreferredSize().width * minecartPos[0] / 16,
                    parentComponent.getPreferredSize().height * minecartPos[1] / 12));
        }
        //Deals with rendering Mine-carts, should skip the chest space.

        else {
            addObstacle(new Obstacle(parentComponent, "MineCart.png", parentComponent.getPreferredSize().width * chestPos[0] / 16,
                    parentComponent.getPreferredSize().height * chestPos[1] / 12));
            addObstacle(new Obstacle(parentComponent, "MineCart.png", parentComponent.getPreferredSize().width * minecartPos[0] / 16,
                    parentComponent.getPreferredSize().height * minecartPos[1] / 12));

        }
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
