package MapDesign;
import java.awt.*;
import java.util.HashSet;
import java.util.Stack;

import CharacterResources.*;

//This should stay abstract, for testing purposes, undoing this
/**
 * A class for the basic functions of a Room in this game.
 * This should be extended for the various different types of rooms.
 */
public class Room
{
    //General data
    private final Stack<Enemy> ENEMIES_TO_BE_DELETED = new Stack<Enemy>();
    protected HashSet<Obstacle> obstacles;
    protected HashSet<Enemy> enemies;
    protected GamePlayComponent parentComponent;
    private Stairs[] stairs;
    private HashSet<Collideable> collideables;

    //Stairs Timer (Might need to be fixed)
    private static final int STAIRS_WAIT_TIME = 10; // in repaints
    private int stairsCounter;

    //Flags
    protected boolean stairsActive;
    protected boolean roomActive;

    /**
     * The basic constructor for a Room object.
     * @param parentComponent The GamePlayComponent object which the room is being drawn to.
     * @param stairID The respective ID of the room, this is used to generate the stairs that need to be drawn and point to other rooms.
     */
    public Room(GamePlayComponent parentComponent, int stairID)
    {
        this.parentComponent = parentComponent;
        stairsActive = false;
        roomActive = false;
        enemies = new HashSet<Enemy>();
        stairs = new Stairs[4];
        collideables = new HashSet<Collideable>();
        obstacles = new HashSet<Obstacle>();
        //Adds player to collideables
        collideables.add(parentComponent.getPlayer());

        //Initializes stairs
        stairsCounter = 0;
        initStairs(stairID);
    }

    /**
     * Returns the enemies within this room.
     * This does not include traps.
     * @return A HashSet of type Enemy which includes all enemies within the room
     */
    public HashSet<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Adds an enemy to the Room
     * @param enemy The Enemy object to be added to the Room.
     */
    public void addEnemy(Enemy enemy)
    {
        enemies.add(enemy);
        collideables.add(enemy);
    }

    /**
     * Adds an obstacle to the Room
     * @param obstacle The Obstacle object to be added to the Room.
     */
    public void addObstacle(Obstacle obstacle)
    {
        obstacles.add(obstacle);
        collideables.add(obstacle);
    }

    /**
     * Adds a default Obstacle to the room at specified position
     * @param gridX The x grid location from 0 to 15 to draw properly on the map.
     * @param gridY The y grid location from 0 to 11 to draw properly on the map.
     */
    public void addSimpleObstacle(int gridX, int gridY)
    {
        //Remember to throw error here if not used right.
        Obstacle temp = new Obstacle(parentComponent, parentComponent.getPreferredSize().width*gridX/16,
                parentComponent.getPreferredSize().height*gridY/12);
        obstacles.add(temp);
        collideables.add(temp);
    }

    /**
     * Activates the room, used for thread management stuff.
     * If thread management occurs via the room, should call super, then be overridden.
     */
    public void activateRoom()
    {
        roomActive = true;
    }

    /**
     * Deactivates the room, used for thread management stuff.
     * If thread management occurs via the room, should call super, then be overridden.
     */
    public void deactivateRoom()
    {
        roomActive = false;
    }

    //This will be overridden, but should have a call to super.

    /**
     * Draws the room and all relevant objects within it.
     * Will also handle calling the traverseStairs method and calling the enemy pathfinding method.
     * Subsequent subclasses will likely override this, and should include a call to super.
     * @param g The graphics context that the room is drawn to.
     */
    public void drawRoom(Graphics g)
    {
        if(stairsActive)
        {
            for(int index = 0; index < stairs.length; index++)
            {
                if(stairs[index] != null) {
                    stairs[index].drawStairs(g);
                    parentComponent.traverseStairs(stairs[index]);
                }
            }
        }

        //To be handled if room is active.
        if(roomActive)
        {
            for(Obstacle o : obstacles) {
                o.drawObstacle(g);
            }

            //Move enemies via pathfinding.
            for(Enemy e : enemies)
            {
                e.pathFinding(parentComponent.getPlayer().getX() + parentComponent.getPlayer().getWidth() / 2,
                        parentComponent.getPlayer().getY() + parentComponent.getPlayer().getHeight() / 2, collideables);
                if(e.getCurrentHP() <= 0)
                    ENEMIES_TO_BE_DELETED.push(e);
                e.draw(g);
            }
        }

        //Deletes dead enemies via processing them off of a stack.
        while(!ENEMIES_TO_BE_DELETED.isEmpty()) {
            collideables.remove(ENEMIES_TO_BE_DELETED.peek());
            enemies.remove(ENEMIES_TO_BE_DELETED.pop());
        }
    }

    /**
     * Returns array of hashsets. This array will only be filled with hashsets that have generic types which extend Collideable.
     * @return Returns array of hashsets. This array will only be filled with hashsets that have generic types which extend Collideable.
     */
    public HashSet<Collideable> getCollideables()
    {
        return collideables;
    }

    //Method with giant switch case for deciding what stairs to spawn
    private void initStairs(int roomNum)
    {
        switch(roomNum)
        {
            //Center Rooms
            case(24):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,27), new Stairs(parentComponent, Stairs.Direction.SOUTH,31),
                        new Stairs(parentComponent, Stairs.Direction.WEST,29), new Stairs(parentComponent, Stairs.Direction.EAST,25)};
                break;
            case(25):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,26), new Stairs(parentComponent, Stairs.Direction.SOUTH,32),
                        new Stairs(parentComponent, Stairs.Direction.WEST,24), new Stairs(parentComponent, Stairs.Direction.EAST,33)};
                break;
            case(27):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,34), new Stairs(parentComponent, Stairs.Direction.SOUTH,24),
                        new Stairs(parentComponent, Stairs.Direction.WEST,28), new Stairs(parentComponent, Stairs.Direction.EAST,26)};
                break;
            case(29):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,28), new Stairs(parentComponent, Stairs.Direction.SOUTH,30),
                        new Stairs(parentComponent, Stairs.Direction.WEST,35), new Stairs(parentComponent, Stairs.Direction.EAST,24)};
                break;

            //Corner rooms
            case(26):
                stairs = new Stairs[]{null, new Stairs(parentComponent, Stairs.Direction.SOUTH,25),
                        new Stairs(parentComponent, Stairs.Direction.WEST,27),null};
                break;
            case(28):
                stairs = new Stairs[]{null, new Stairs(parentComponent, Stairs.Direction.SOUTH,29),
                        null,new Stairs(parentComponent, Stairs.Direction.EAST,27)};
                break;
            case(30):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,29),null,
                        null, new Stairs(parentComponent, Stairs.Direction.EAST,31)};
                break;
            case(32):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,25), null,
                        new Stairs(parentComponent, Stairs.Direction.WEST,31), null};
                break;

            //Other
            case(31):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,24), null,
                        new Stairs(parentComponent, Stairs.Direction.WEST,30), new Stairs(parentComponent, Stairs.Direction.EAST,32)};
                break;

            //Exit pointing rooms
            case(33):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,36), new Stairs(parentComponent, Stairs.Direction.SOUTH,36),
                        new Stairs(parentComponent, Stairs.Direction.WEST,25), null };
                break;
            case(35):
                stairs = new Stairs[]{new Stairs(parentComponent, Stairs.Direction.NORTH,36), new Stairs(parentComponent, Stairs.Direction.SOUTH,36),
                        null, new Stairs(parentComponent, Stairs.Direction.EAST,29)};
                break;
            case(34):
                stairs = new Stairs[]{null, new Stairs(parentComponent, Stairs.Direction.SOUTH,27),
                        new Stairs(parentComponent, Stairs.Direction.WEST,36), new Stairs(parentComponent, Stairs.Direction.EAST,36)};
                break;

            //Final Room (Boss)
            case(36):
                stairs = new Stairs[] {null,null,null,null};
                break;

        }
    }

    /**
     * Sets stairs to active, which will draw the stairs and begin checking for player collision on each subsequent repaint while stairs are active.
     * Also sets a wait-time after stairs are activated to determine how long before collision starts getting checked.
     */
    public void activateStairs()
    {
        stairsActive = true;
        stairsCounter = STAIRS_WAIT_TIME;
    }

    /**
     * Checks if the stairs are active in the room.
     * @return A boolean value which states whether the stairs are active.
     */
    public boolean isStairsActive()
    {
        return stairsActive;
    }

    /**
     * Returns a variable which represents the amount of time left in repaints until stairs check for collision with player.
     * @return int number of repaints left.
     */
    public int getStairsCounter()
    {
        return stairsCounter;
    }

}
