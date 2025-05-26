package GUIDesign;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.locks.*;
import java.util.*;

import CharacterResources.Enemies.Enemy;
import CharacterResources.Enemies.GolemBoss;
import CharacterResources.Items.Item;
import CharacterResources.Items.StoneHeartItem;
import CharacterResources.Items.WhetstoneItem;
import CharacterResources.Items.WingBootsItem;
import CharacterResources.Player.Classes.PlayerIceMage;
import CharacterResources.Player.Player;
import Collision.Collideable;
import MapDesign.Rooms.*;

/**
 * This class is the component that actually "runs" the main game. This is where the player moves around and interacts primarily.
 */
public class GamePlayComponent extends JComponent implements ActionListener, KeyListener {

    //Menu stuff
    private final PauseMenu PAUSE_MENU;

    //In house objects
    private static final int CHEST_CONSTANT = 3;
    private MainFrame parent;
    private Player player;
    private final Queue<Item> ITEM_QUEUE = new LinkedList<Item>();
    //private ArrayList<Enemy> enemies;

    //Room gaming
    private int currentRoom;
    private HashMap<Integer, Room> roomRef;
    private static final int DEFAULT_ROOM = 24;


    //Out of house variables
    private Timer timer;    //For Thread Management
    public static int UPDATE_INTERVAL = 10; //in ms

    private static final int NUM_INPUT_KEYS = 6;
    private boolean[] keys;

    ImageIcon floor;
    private ReentrantLock hpLock;

    //Helper methods for constructor.

        //Handles any behavior for initializing the player.
        private void initPlayer()
        {
            player = new PlayerIceMage(this);
        }

        //Initializes rooms by populating Rooms map.
        private void initRooms()
        {
            Random rand = new Random();
            //Construct Room stuff:
            currentRoom = DEFAULT_ROOM;
            roomRef = new HashMap<Integer, Room>();
            roomRef.put(DEFAULT_ROOM,new Room(this,DEFAULT_ROOM));

            HashSet<Integer> chestRooms = new HashSet<Integer>();

            //Modify this to add new items!
            for(int i = 0; i < CHEST_CONSTANT; i++)
            {
                switch(rand.nextInt(3))
                {
                    case(0) -> ITEM_QUEUE.add(new WhetstoneItem());
                    case(1) -> ITEM_QUEUE.add(new StoneHeartItem());
                    case(2) -> ITEM_QUEUE.add(new WingBootsItem());
                }
            }

            int chestCount = 0;
            while(chestCount < CHEST_CONSTANT)
            {
                int newChestRoom = rand.nextInt(24,35);
                if(chestRooms.add(newChestRoom))
                    chestCount++;
            }

            //This is for testing, in final roomNum should start at 25
            for(int roomNum = 25; roomNum < 36; roomNum++)
            {
                Item item = null;
                if(!chestRooms.isEmpty() && chestRooms.contains(roomNum))
                    item = ITEM_QUEUE.remove();
                int roomType = rand.nextInt(3);
                switch(roomType) {
                    case(0) -> roomRef.put(roomNum, new CombatRoom1(this, roomNum,item));
                    case(1) -> roomRef.put(roomNum, new CombatRoom2(this, roomNum,item));
                    case(2) -> roomRef.put(roomNum, new CombatRoom3(this, roomNum,item));
                }
            }

            //Adds boss room
            roomRef.put(36,new BossRoom(this,36));


            roomRef.get(DEFAULT_ROOM).activateRoom();
            roomRef.get(DEFAULT_ROOM).activateStairs();

        }

    /**
     * The constructor for the component, initializes all rooms and the key listener stuff.
     * @param inWidth The preferred width the component will be constructed with.
     * @param inHeight The preferred height the component will be constructed with.
     * @param parent This is the parent in the hierarchy for swing. Is type MainFrame due to unique properties of MainFrame class.
     */
    public GamePlayComponent(int inWidth, int inHeight, MainFrame parent)
    {
        super();
        this.parent = parent;
        hpLock = new ReentrantLock();
        setPreferredSize(new Dimension(inWidth,inHeight));
        setMinimumSize(new Dimension(inWidth,inHeight));

        //Key listener stuff
        keys = new boolean[NUM_INPUT_KEYS];
        this.addKeyListener(this);
        timer = new Timer(UPDATE_INTERVAL, this);
        timer.start();

        //Begin component menu stuff
        try{
            floor = new ImageIcon(getClass().getClassLoader().getResource("floor.png"));
        }
        catch(Exception e)
        {
            System.out.println("Likely file not found while initializing floor.png");
        }

        PAUSE_MENU = new PauseMenu(this);

        //End component menu stuff.
        //This is a placeholder will eventually take input for class if we get there
        initPlayer();

        initRooms();
        setFocusable(true);
        requestFocus();
    }

    /**
     * Resets this component to a start-of-game state.
     * Should be called from a centralized space that also resets the IndicatorComponent object.
     */
    public void resetGame()
    {
        //Player must be initialized before Rooms.
        initPlayer();
        initRooms();
    }

    /**
     * This method gets the current Room that is active from the map.
     * @return Returns a Room from the roomRef map based on currently active room location.
     */
    public Room getCurrentRoomRef() {
        return roomRef.get(currentRoom);
    }

    /**
     * Returns currently active Room.
     * @return Currently active Room location.
     */
    public int getCurrentRoom() {
        return currentRoom;
    }


    /**
     * Changes the currently active room when a player collides with an active Stairs object.
     * Also swaps the side of the screen the player is on to give illusion of traversing stairs.
     * This doubles to ensure if the swapped room always has active stairs, the player does not instantly traverse to another room.
     * @param stairs The stairs object being checked.
     */
    public void traverseStairs(Stairs stairs)
    {
        if(player.collides(stairs) != null)
        {
            //Don't forget to mirror player placement.
            switch(stairs.getDirection())
            {
                case NORTH:
                    player.setPos(player.getX(),getPreferredSize().height - stairs.getHeight()- Collision.Collideable.PIXEL_CONSTANT-5);
                    break;
                case SOUTH:
                    player.setPos(player.getX(),stairs.getHeight()+5);
                    break;
                case WEST:
                    player.setPos(getPreferredSize().width - stairs.getWidth()- Collideable.PIXEL_CONSTANT-5,player.getY());
                    break;
                case EAST:
                    player.setPos(stairs.getWidth() +5,player.getY());
                    break;
            }
            //Handles room activation stuff, important for thread management with enemies
            getCurrentRoomRef().deactivateRoom();
            currentRoom = stairs.getNextRoom();
            getCurrentRoomRef().activateRoom();

            System.out.println("Room changed, new Room: " + currentRoom);
        }
    }

    /**
     * Calls super variant of method and also paints floor, the active room, then the player in that order.
     * @param g The graphics object to which it will be drawn.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        //Each tile is 50px/50px
        super.paintComponent(g);
        floor.paintIcon(this,g,0,0);
        getCurrentRoomRef().drawRoom(g);
        player.draw(g);

        if(PAUSE_MENU.isActive())
        {
            PAUSE_MENU.drawPauseMenu(g);
        }

        checkPlayerDamageCollision();
    }

    //Now to satisfy interfaces:
    /**
     * Sets an array of booleans at a specific index to true when a key is pressed.
     * @param e The event to be processed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //Directional inputs.
        if(getCurrentRoomRef().isRoomActive()) {
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_LEFT):
                case (KeyEvent.VK_A):
                    keys[0] = true;
                    break;
                case (KeyEvent.VK_RIGHT):
                case (KeyEvent.VK_D):
                    keys[1] = true;
                    break;
                case (KeyEvent.VK_UP):
                case (KeyEvent.VK_W):
                    keys[2] = true;
                    break;
                case (KeyEvent.VK_DOWN):
                case (KeyEvent.VK_S):
                    keys[3] = true;
                    break;
                case (KeyEvent.VK_SPACE):
                    keys[4] = true;
                    break;
                case (KeyEvent.VK_BACK_QUOTE):
                    keys[5] = true;
                    break;
            }
        }
    }

    /**
     * Sets an array of booleans at a specific index to false when a key is pressed.
     * @param e The event to be processed.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case(KeyEvent.VK_LEFT):
            case(KeyEvent.VK_A):
                keys[0] = false;
                break;
            case (KeyEvent.VK_RIGHT):
            case(KeyEvent.VK_D):
                keys[1] = false;
                break;
            case (KeyEvent.VK_UP):
            case(KeyEvent.VK_W):
                keys[2] = false;
                break;
            case (KeyEvent.VK_DOWN):
            case(KeyEvent.VK_S):
                keys[3] = false;
                break;
            case(KeyEvent.VK_SPACE):  keys[4] = false; break;
            case(KeyEvent.VK_BACK_QUOTE): keys[5] = false; break;
        }
    }

    /**
     * Checks if an action is performed, if an action is performed and one of the keys array values is true, executes respective command
     * @param e The event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(player!= null && !player.isDead() && getCurrentRoomRef() != null && getCurrentRoomRef().isRoomActive()) {
            if (keys[0])
                player.move("W", getCurrentRoomRef().getCollideables());
            if (keys[1])
                player.move("E", getCurrentRoomRef().getCollideables());
            if (keys[2])
                player.move("N", getCurrentRoomRef().getCollideables());
            if (keys[3])
                player.move("S", getCurrentRoomRef().getCollideables());

            //Add something here with regards to player attack procedure.
            if (keys[4]) {
                if (player.getPickaxe().getAttackCount() <= 0)
                    player.getPickaxe().initAttack();
            }
        }
        if(keys[5]){
            keys[5] = false;
            parent.resetFrame();
        }

        repaint();
    }

    /**
     * Gets the MainFrame object responsible for handling this GamePlayComponent.
     * @return MainFrame object responsible for handling this GamePlayComponent.
     */
    public MainFrame getMainFrame()
    {
        return parent;
    }

    /**
     *  Gets the player from this GamePlayComponent.
     * @return The Player object from this Component.
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Needed to satisfy the KeyListener.
     * Put this at the bottom of code.
     * @param e not used.
     */
    public void keyTyped(KeyEvent e) {
        // no code needed here
    }



    /**
     * Starts the damage thread on a player, typically called whenever an enemy collides with the player.
     * Can be called by other classes to start the damage in other ways.
     * @param damage The amount of damage to be done to the player.
     */
    public void startPlayerDamage(int damage) {

        class IFramesRunnable implements Runnable {
            public IFramesRunnable()
            {
                //Nothing here
            }
            public void run() {
                if(hpLock.tryLock()) {
                    try {
                        player.damagePlayer(damage);
                        //System.out.println("Player damage called");
                        parent.getTopUI().repaint();
                        Thread.sleep(Player.PLAYER_I_FRAMES);
                    } catch (InterruptedException e) {
                        System.out.println("ZZZZ");
                    } finally {
                        hpLock.unlock();
                    }
                }
            }
        }
            IFramesRunnable r1 = new IFramesRunnable();
            Thread t1 = new Thread(r1);
            t1.start();

    }
    //Cool helper method to check for all body collisions. Not to be used for player attacks and enemies
    private void checkPlayerDamageCollision()
    {
        for(Enemy enemy : getCurrentRoomRef().getEnemies())
        {
            //Stops contact damage from boss, turns on contact damage during boss swing attack.
            if((!(enemy instanceof GolemBoss) && player.collides(enemy) != null) || (enemy instanceof GolemBoss && ((GolemBoss) enemy).getDrillAnimationCD() > 0 && player.collides(enemy) != null))
            {
                startPlayerDamage(enemy.getAtk());
            }
        }
    }



}
