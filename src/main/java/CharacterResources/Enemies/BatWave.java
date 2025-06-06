package CharacterResources.Enemies;
import CharacterResources.Player.Player;
import Collision.Collideable;
import Collision.Projectile;
import GUIDesign.GamePlayComponent;
import Rooms.Room;

import javax.swing.*;

/**
 * Class responsible for the batwave projectile
 * A BatWave projectile is spawned at an interval from a SoundBat object.
 * Once spawned, the projectile continues moving in the direction which it was initially facing.
 */
public class BatWave extends Projectile {

    private static final String SOUTHWAVE = "SouthWave.png";
    private static final String SOUTHEASTWAVE = "SouthEastWave.png";
    private static final String SOUTHWESTWAVE = "SouthWestWave.png";
    private static final String NORTHWAVE = "NorthWave.png";
    private static final String NORTHWESTWAVE = "NorthWestWave.png";
    private static final String NORTHEASTWAVE = "NorthEastWave.png";
    private static final String EASTWAVE = "EastWave.png";
    private static final String WESTWAVE = "WestWave.png";

    //Constants related to actual pixel width and height of wave images.
    private static final int WAVE_WIDTH = 15;
    private static final int WAVE_HEIGHT = 15;

    private static final int DIAGONALCONST = 100;

    private ImageIcon waveImage;
    private String[] moveDir;
    private SoundBat parentBat;
    private int speed;

    /**
     * Constructs batwave object based on player's current location relative to the parent SoundBat.
     * @param parent The GameplayComponent the wave is drawn to.
     * @param bat The owner/creator of the wave.
     * @param playerCenterX The current x location of the player's center
     * @param playerCenterY The current y location of the player's center
     */
    public BatWave(GamePlayComponent parent, Room parentRoom, SoundBat bat, int playerCenterX, int playerCenterY)
    {
        //super(parent,bat.getX()+bat.getWidth()/2,bat.getY()+bat.getHeight()/3);
        super(bat.getX(), bat.getY(), WAVE_WIDTH, WAVE_HEIGHT, bat.getSpeed(), bat.getAtk(), playerCenterX, playerCenterY, parent, parentRoom);

        int batCenterX = bat.getX()+bat.getWidth()/2;
        int batCenterY = bat.getY()+bat.getHeight()/3;

        int distX = Math.abs(batCenterX-playerCenterX);
        int distY = Math.abs(batCenterY-playerCenterY);

        String waveImageDir;

        //Multi-Direction
        if(Math.abs(distX - distY) < DIAGONALCONST)
        {
            if(playerCenterX > batCenterX && playerCenterY < batCenterY)
            {
                waveImageDir = NORTHEASTWAVE;
            }
            else if (playerCenterX > batCenterX && playerCenterY > batCenterY) {
                waveImageDir = SOUTHEASTWAVE;
            }

            else if(playerCenterX < batCenterX && playerCenterY < batCenterY)
            {
                waveImageDir = NORTHWESTWAVE;
            }
            else {
                waveImageDir = SOUTHWESTWAVE;
            }
        }

        //Single-Direction
        else {
            if(distX > distY)
            {
                if(playerCenterX > batCenterX) {
                    waveImageDir = EASTWAVE;
                }
                else {
                    waveImageDir = WESTWAVE;
                }
            }
            else{
                if(playerCenterY < batCenterY) {
                    waveImageDir = NORTHWAVE;
                }
                else {
                    waveImageDir = SOUTHWAVE;
                }
            }
        }

        obstacle = new ImageIcon(getClass().getClassLoader().getResource(waveImageDir));
        parentBat = bat;
        speed = parentBat.getSpeed();

    }

    /**
     * Overrides parent to return a constant related to the width of the actual wave in the ImageIcon with the extra transparent space mitigated.
     * @return The width of the wave as an integer.
     */
    @Override
    public int getWidth() {
        return WAVE_WIDTH;
    }

    /**
     * Overrides parent to return a constant related to the height of the actual wave in the ImageIcon with the extra transparent space mitigated.
     * @return The height of the wave as an integer.
     */
    @Override
    public int getHeight() {
        return WAVE_HEIGHT;
    }

    /**
     * Damages Player on collision with attack proportional to spawning bat.
     * @param collideable The Creature object to apply the effect to.
     */
    @Override
    public void collisionEffect(Collideable collideable)
    {
        if(collideable instanceof Player) {
            parent.startPlayerDamage(parentBat.getAtk());
            terminateProjectile();
        }
    }
}
