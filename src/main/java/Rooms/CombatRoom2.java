package Rooms;

import CharacterResources.Enemies.EvilRock;
import CharacterResources.Items.Item;
import CharacterResources.Enemies.SoundBat;
import GUIDesign.GamePlayComponent;

import java.awt.*;
import java.util.Random;

/**
 * A specialized class for a combat room a player can encounter in their adventure.
 */
public class CombatRoom2 extends Room {

    /**
     * Constructs a CombatRoom2 object with the following parameters.
     * @param parentComponent The GamePlayComponent the room is to be drawn to.
     * @param stairID The stairID associated with the room.
     * @param inItem An item to be put in a chest within the room. Pass null into this parameter if there is no chest in this room.
     */
    public CombatRoom2(GamePlayComponent parentComponent, int stairID, Item inItem)
    {
        super(parentComponent,stairID);

        //Add enemies for combat room.
        //Bats
        addEnemy(new SoundBat(parentComponent.getPreferredSize().width/16,
                parentComponent.getPreferredSize().height/12, parentComponent));
        addEnemy(new SoundBat(parentComponent.getPreferredSize().width*15/16,
                parentComponent.getPreferredSize().height/12, parentComponent));
        addEnemy(new SoundBat(parentComponent.getPreferredSize().width*4/16,
                parentComponent.getPreferredSize().height*8/12, parentComponent));
        addEnemy(new SoundBat(parentComponent.getPreferredSize().width*15/16,
                parentComponent.getPreferredSize().height*11/12, parentComponent));

        //Rocks
        addEnemy(new EvilRock(parentComponent.getPreferredSize().width*2/16,
                parentComponent.getPreferredSize().height*11/12, parentComponent));
        addEnemy(new EvilRock(parentComponent.getPreferredSize().width*11/16,
                parentComponent.getPreferredSize().height*2/12,parentComponent));

        //Add obstacles to room

        //Upper Left Obstacles
        for(int y = 0; y < 4; y++)
        {
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*5/16,
                    parentComponent.getPreferredSize().height*y/12));
        }
        addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*4/16,
                parentComponent.getPreferredSize().height*4/12));
        addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*6/16,
                parentComponent.getPreferredSize().height*4/12));


        //Upper-Right Obstacles
        for(int y = 0; y < 4; y++)
        {
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*10/16,
                    parentComponent.getPreferredSize().height*y/12));
        }
        addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*9/16,
                parentComponent.getPreferredSize().height*4/12));
        addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*11/16,
                parentComponent.getPreferredSize().height*4/12));

        //Bottom Left Corner obstacles
        for(int x = 2; x < 5; x++)
        {
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*x/16,
                    parentComponent.getPreferredSize().height*9/12));
        }
        for(int y = 10; y < 13; y++)
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*4/16,
                    parentComponent.getPreferredSize().height*y/12));


        //Bottom Right Corner Obstacles
        for(int x = 11; x < 14; x++)
        {
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*x/16,
                    parentComponent.getPreferredSize().height*9/12));
        }
        for(int y = 10; y < 13; y++)
            addObstacle(new Obstacle(parentComponent,parentComponent.getPreferredSize().width*11/16,
                    parentComponent.getPreferredSize().height*y/12));


        //Chest TIME!
        Random rand = new Random();
        int[] chestPos = new int[] {rand.nextInt(2)+7, rand.nextInt(2)+7};
        if(inItem != null)
        {
            addObstacle(new Chest(parentComponent, parentComponent.getPreferredSize().width * chestPos[0] / 16,
                    parentComponent.getPreferredSize().height * chestPos[1] / 12, inItem));
        }
        //Deals with rendering Mine-carts, should skip the chest space.
        for(int x = 7; x < 9; x++) {
            for(int y = 7; y < 9; y++ ) {
                if(inItem != null && x == chestPos[0] && y == chestPos[1])
                    continue;
                else {
                    addObstacle(new Obstacle(parentComponent, "MineCart.png", parentComponent.getPreferredSize().width * x / 16,
                            parentComponent.getPreferredSize().height * y / 12));
                }
            }

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
