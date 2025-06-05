package main.Rooms;
import main.CharacterResources.Items.Item;
import main.GUIDesign.GamePlayComponent;

import javax.swing.*;
/**
 * Chest is an extension of obstacle which the player can get near and interact with in order to open.
 * Chest has a closed and open state.
 * A chest is considered closed when it contains an Item.
 * A chest is considered open when the Item it contains has a null value, and it therefore does not contain an Item.
 */
public class Chest extends Obstacle {

    private static final String CLOSED_CHEST_IMAGE = "Chest.png";
    private static final String OPEN_CHEST_IMAGE = "ChestOpen.png";
    private Item containedItem ;

    /**
     * Constructs a Chest with the specified parameters.
     * @param inParent The GamePlayComponent to be drawn to.
     * @param xPos The initial x-position.
     * @param yPos The initial y-position.
     * @param inItem The Item to be stored within the Chest.
     */
    public Chest(GamePlayComponent inParent, int xPos, int yPos, Item inItem)
    {
        super(inParent, CLOSED_CHEST_IMAGE, xPos, yPos);
        containedItem = inItem;
    }

    /**
     * Constructs a Chest with the specified parameters.
     * This is used to pass a different image to the chest. (Use for special Chests/Items).
     * @param inParent The GamePlayComponent to be drawn to.
     * @param inFileName The file-name of the picture to be associated with this chest.
     * @param xPos The initial x-position.
     * @param yPos The initial y-position.
     * @param inItem The Item to be stored within the Chest.
     */
    public Chest(GamePlayComponent inParent, String inFileName, int xPos, int yPos, Item inItem)
    {
        super(inParent, inFileName, xPos, yPos);
        containedItem = inItem;
    }

    /**
     * Returns Item contained within chest and sets the contained Item within the chest to null.
     * @return The Item contained within the chest.
     */
    public Item openChest()
    {
        setImageIcon(new ImageIcon(getClass().getClassLoader().getResource(OPEN_CHEST_IMAGE)));
        Item temp = containedItem;
        containedItem = null;
        return temp;
    }

    /**
     * This returns the Item contained within the chest without setting the contained Item to null.
     * @return The Item contained within the chest.
     */
    public Item peekChest()
    {
        return containedItem;
    }
}
