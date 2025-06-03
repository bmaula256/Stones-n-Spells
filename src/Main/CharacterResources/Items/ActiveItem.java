package Main.CharacterResources.Items;

import Main.CharacterResources.Creature;
import Main.CharacterResources.Player.Player;
import Main.GUIDesign.GamePlayComponent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * An abstract class which serves as the basis for an item which must be activated by Creature input.
 * (As opposed to passively affecting Creature stats.)
 * This class implements KeyListener and either listens to an assigned keyboard input or the default keyboard input included in this class (SHIFT).
 * Keyboard input is only listened to in the instance that the assigned Creature is of instance Player.
 */
public abstract class ActiveItem extends Item implements KeyListener {

    private Creature owner;
    private GamePlayComponent parentComponent;
    private int itemKey;

    private static final int  DEFAULT_ITEM_KEY = KeyEvent.VK_SHIFT;


    /**
     * Constructs an ActiveItem with specified parameters.
     * This constructor will assign the default key to listen for (SHIFT).
     * @param inFileName A String representing the file name to which an image representing the item is depicted.
     * @param parentComponent The GamePlayComponent that this item is linked to. This is necessary due to key-listener.
     */
    public ActiveItem(String inFileName, GamePlayComponent parentComponent) {
        super(inFileName);
        owner = null;
        itemKey = DEFAULT_ITEM_KEY;
        this.parentComponent = parentComponent;
    }

    /**
     * Constructs an ActiveItem with specified parameters.
     * @param inFileName A String representing the file name to which an image representing the item is depicted.
     * @param itemKey An integer representing a key input associated with activating the item. Should pass through KeyEvent integer constant.
     * @param parentComponent The GamePlayComponent that this item is linked to. This is necessary due to key-listener.
     */
    public ActiveItem(String inFileName, int itemKey, GamePlayComponent parentComponent)
    {
        super(inFileName);
        owner = null;
        this.itemKey = itemKey;
        this.parentComponent = parentComponent;
    }

    /**
     * This method assigns an owner to the ActiveItem.
     * This also sets up a key-listener if the Creature is an instance of Player.
     * @param owner The Creature which will possess this ActiveItem object.
     */
    public void assignOwner(Creature owner)
    {
        this.owner = owner;
        if(owner instanceof Player && !parentComponent.getKeyListenersHash().contains(this)) {
            parentComponent.addKeyListener(this);
        }
    }

    /**
     * This method unassigns an owner to the active item.
     * This will disable the key listener if the owner of this ActiveItem is of instance Player.
     */
    public void unAssignOwner()
    {
        if(this.owner instanceof Player && parentComponent.getKeyListenersHash().contains(this))
        {
            parentComponent.removeKeyListener(this);
        }
        owner = null;
    }

    /**
     * Enables or disables the KeyListener of this ActiveItem object.
     */
    public void toggleKeyListener()
    {
        if(parentComponent.getKeyListenersHash().contains(this))
            parentComponent.removeKeyListener(this);
        else
            parentComponent.addKeyListener(this);
    }

    /**
     * Gets the GamePlayComponent associated with this object.
     * @return The GamePlayComponent associated with this object.
     */
    public GamePlayComponent getParentComponent() {
        return parentComponent;
    }


    /**
     * Activates ActiveItem effect when key is pressed.
     * @param e the event to be processed.
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == itemKey)
            activateEffect(owner);
    }

    /**
     * Deactivates ActiveItem effect when key is released.
     * @param e the event to be processed.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == itemKey)
            deactivateEffect(owner);
    }

    /**
     * Does nothing, needed to satisfy keyListener.
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        //Does nothing.
    }
}
