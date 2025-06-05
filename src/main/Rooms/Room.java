package main.Rooms;
import java.awt.*;
import java.util.HashSet;
import java.util.Stack;

import main.CharacterResources.Enemies.Enemy;
import main.Collision.*;
import main.GUIDesign.GamePlayComponent;
import main.Updateable;

//This should stay abstract, for testing purposes, undoing this
/**
 * A class for the basic functions of a Room in this game.
 * This should be extended for the various different types of rooms.
 */
public class Room implements Updateable
{
    //General data
    private final Stack<Enemy> ENEMIES_TO_BE_DELETED = new Stack<Enemy>();
    private final Stack<Projectile> PROJECTILE_TO_BE_DELETED = new Stack<Projectile>();
    /**
     * HashSet containing all obstacles to drawn in the Room.
     */
    protected final HashSet<Obstacle> obstacles = new HashSet<Obstacle>();
    /**
     * HashSet responsible for storing all enemies in a room.
     */
    protected final HashSet<Enemy> enemies  = new HashSet<Enemy>();
    /**
     * GamePlayComponent to draw the room to.
     */
    protected GamePlayComponent parentComponent;
    private Stairs[] stairs;
    private final HashSet<Collideable> COLLIDEABLES = new HashSet<Collideable>();
    private final HashSet<Projectile> PROJECTILES = new HashSet<Projectile>();

    //Stairs Timer (Might need to be fixed)
    private static final int STAIRS_WAIT_TIME = 10; // in repaints
    private int stairsCounter;

    //Flags
    /**
     * Flag for whether the stairs are active in the Room.
     */
    protected boolean stairsActive;
    /**
     * Flag for whether the whole room is active.
     * @see #activateRoom()
     * @see #deactivateRoom()
     */
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
        stairs = new Stairs[4];
        //Adds player to collideables
        COLLIDEABLES.add(parentComponent.getPlayer());

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
        COLLIDEABLES.add(enemy);
    }

    /**
     * Adds a Projectile object to the room.
     * @param projectile The Projectile to be added.
     */
    public void addProjectile(Projectile projectile)
    {
        COLLIDEABLES.add(projectile);
        PROJECTILES.add(projectile);
    }

    /**
     * Adds an obstacle to the Room
     * @param obstacle The Obstacle object to be added to the Room.
     */
    public void addObstacle(Obstacle obstacle)
    {
        obstacles.add(obstacle);
        COLLIDEABLES.add(obstacle);
    }

    /**
     * Adds Projectile object to Stack to be removed on update() call.
     * @param projectile The Projectile to be removed.
     */
    public void removeProjectile(Projectile projectile)
    {
        PROJECTILE_TO_BE_DELETED.push(projectile);
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
        COLLIDEABLES.add(temp);
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
     * Subsequent subclasses will likely override this, and should include a call to super.
     * @param g The graphics context that the room is drawn to.
     */
    public void drawRoom(Graphics g)
    {
        if(stairsActive)
        {
            for (Stairs stair : stairs) {
                if (stair != null) {
                    stair.drawStairs(g);
                }
            }
        }
        for(Enemy e : enemies) {
            e.draw(g);
        }

        for(Obstacle o : obstacles) {
            o.drawObstacle(g);
        }

        for(Projectile p : PROJECTILES) {
            p.drawObstacle(g);
        }
    }

    /**
     * Returns array of hashsets. This array will only be filled with hashsets that have generic types which extend Main.Collision.Collideable.
     * @return Returns array of hashsets. This array will only be filled with hashsets that have generic types which extend Main.Collision.Collideable.
     */
    public HashSet<Collideable> getCollideables()
    {
        return COLLIDEABLES;
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
     * Returns whether the room is in an active state.
     * @return A boolean representing whether the room is in an active state.
     */
    public boolean isRoomActive()
    {
        return roomActive;
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


    /**
     * Updates the state of the room. This method is tied to a swing Timer object, and handles behavioral updates of objects and entities.
     * @param nullPoint Should be null, doesn't do anything.
     * @see javax.swing.Timer
     */
    @Override
    public void update(Object nullPoint)
    {
        if(stairsActive)
        {
            for (Stairs stair : stairs) {
                if (stair != null) {
                    parentComponent.traverseStairs(stair);
                }
            }
        }

        if(roomActive)
        {
            //Move enemies via pathfinding.
            for(Enemy e : enemies)
            {
                e.pathFinding(parentComponent.getPlayer().getImageCenterX(),
                        parentComponent.getPlayer().getImageCenterY(), COLLIDEABLES);
                if(e.getCurrentHP() <= 0)
                    ENEMIES_TO_BE_DELETED.push(e);
            }

            //Moves projectiles.
            for(Projectile p: PROJECTILES) {
                p.move(COLLIDEABLES);

                //Default projectile update behavior.
                if(p instanceof Updateable)
                    ((Updateable) p).update(null);
            }
        }

        //Deletes dead enemies via processing them off of a stack.
        while(!ENEMIES_TO_BE_DELETED.isEmpty()) {
            COLLIDEABLES.remove(ENEMIES_TO_BE_DELETED.peek());
            enemies.remove(ENEMIES_TO_BE_DELETED.pop());
        }

        //Deletes Projectiles pushed onto the stack.
        while (!PROJECTILE_TO_BE_DELETED.isEmpty())
        {
            COLLIDEABLES.remove(PROJECTILE_TO_BE_DELETED.peek());
            PROJECTILES.remove(PROJECTILE_TO_BE_DELETED.pop());
        }
    }

}
