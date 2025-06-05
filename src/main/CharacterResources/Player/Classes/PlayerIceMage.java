package main.CharacterResources.Player.Classes;

import main.CharacterResources.Player.Player;
import main.GUIDesign.GamePlayComponent;

import javax.swing.*;

/**
 * A class which represents a player subclass based around ice in a game design sense.
 */
public class PlayerIceMage extends Player {

    private final ImageIcon ICE_MAGE_SOUTH = new ImageIcon(getClass().getClassLoader().getResource("IceMageSouth.png"));
    private final ImageIcon ICE_MAGE_NORTH = new ImageIcon(getClass().getClassLoader().getResource("IceMageNorth.png"));
    private final ImageIcon ICE_MAGE_WEST = new ImageIcon(getClass().getClassLoader().getResource("IceMageWest.png"));
    private final ImageIcon ICE_MAGE_EAST = new ImageIcon(getClass().getClassLoader().getResource("IceMageEast.png"));

    /**
     * Constructs an object of type PlayerIceMage.
     * @param inComponent The GamePlayComponent to pass into the super constructor.
     */
    public PlayerIceMage(GamePlayComponent inComponent)
    {
        super(inComponent, "IceMageSouth.png");
    }

    /**
     * Updates the Player's Image depending on the direction which it is moving.
     * @param direction The direction which the Player is now moving.
     */
    @Override
    protected void updatePlayerImage(String direction)
    {
        switch (direction)
        {
            case("N") -> currentPlayerImage = ICE_MAGE_NORTH;
            case("S") -> currentPlayerImage = ICE_MAGE_SOUTH;
            case("W") -> currentPlayerImage = ICE_MAGE_WEST;
            case("E") -> currentPlayerImage = ICE_MAGE_EAST;
        }
    }
}
