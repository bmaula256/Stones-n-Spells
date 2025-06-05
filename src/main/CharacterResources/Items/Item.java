package main.CharacterResources.Items;
import main.CharacterResources.Creature;

import javax.swing.*;
import java.awt.*;

/**
 * An abstract superclass for extending items to use for their basis.
 * Items are objects which modify the owning creature's stats.
 * Items can be found inside Chest objects or at the shop.
 */
public abstract class Item
{

    private ImageIcon itemImage;

    /**
     * Constructs an Item and populates the Item's image.
     * @param inFileName The name of the image file that represents the Item.
     */
    public Item(String inFileName)
    {
        itemImage = new ImageIcon(getClass().getClassLoader().getResource(inFileName));
    }

    /**
     * Draws the item to the specified Component.
     * @param parent The Component to be drawn to.
     * @param g The Graphics context to be drawn to.
     * @param x The x-position.
     * @param y The y-position.
     */
    public void drawItem(Component parent, Graphics g, int x, int y)
    {
        System.out.println("Draw Item called");
        itemImage.paintIcon(parent, g,x,y);
    }

    /**
     * Handles the logic for activating the Item's effects on a specific Creature.
     * @param creature The Creature to activate the Item's effect on.
     */
    public abstract void activateEffect(Creature creature);

    /**
     * Handles the logic for deactivating the Item's effects on a specific Creature.
     * @param creature The Creature to deactivate the Item's effect on.
     */
    public abstract void deactivateEffect(Creature creature);
}
