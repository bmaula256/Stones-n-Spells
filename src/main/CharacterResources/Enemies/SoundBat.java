package main.CharacterResources.Enemies;

import main.GUIDesign.GamePlayComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Stack;
import java.util.Random;

/**
 * A class representing an enemy which the player encounters that flies randomly and shoots projectiles.
 */
public class SoundBat extends Enemy {

    private final ImageIcon WINGS_DOWN_ICON = new ImageIcon(getClass().getClassLoader().getResource("SoundBat1.png"));
    private final ImageIcon WINGS_UP_ICON = new ImageIcon(getClass().getClassLoader().getResource("SoundBat2.png"));

    private static final int WING_DELAY = 15;
    private static final int MOVE_CHANGE_DELAY = 50;
    private static final int WAVE_CREATION_DELAY = 225;

    private boolean wingsDown;
    private int wingCycle;

    private int waveCycle;
    private int moveCycle;
    private int moveDir;
    private boolean isMulti;
    private HashSet<BatWave> waves;
    private final Stack<BatWave> removeWaveStack = new Stack<BatWave>();

    /**
     * Constructs a SoundBat with specified parameters.
     * @param x Initial x-position of the SoundBat
     * @param y Initial y-position of the SoundBat
     * @param inGameWorld The GamePlayComponent to be passed into the super constructor.
     */
    public SoundBat(int x, int y, GamePlayComponent inGameWorld)
    {
        super(x,y,50,22,inGameWorld);
        speed = 2;
        wingsDown = true;
        waves= new HashSet<BatWave>();
        wingCycle = WING_DELAY;

        moveCycle = MOVE_CHANGE_DELAY;
        moveDir = 0;
        isMulti = false;

        waveCycle = WAVE_CREATION_DELAY;
    }

    /**
     * Draws the SoundBat to the provided Graphics context.
     * This also handles animating the SoundBat via a flag to determine whether the wings are up or down.
     * @param window the Graphics context to be drawn to.
     */
    @Override
    public void draw(Graphics window) {
        if(wingsDown) {
            WINGS_DOWN_ICON.paintIcon(gameWorld, window, xPos, yPos);
        }
        else {
            WINGS_UP_ICON.paintIcon(gameWorld, window, xPos, yPos);
        }
        if(wingsDown && wingCycle < 0)
        {
            wingsDown = false;
            wingCycle = WING_DELAY;
        }
        else if (wingCycle < 0) {
            wingsDown = true;
            wingCycle = WING_DELAY;
        }
        wingCycle--;

        //Draws associated waves.
        for(BatWave wave : waves)
            wave.drawObstacle(window);
    }

    /**
     * Returns width of bat image.
     * @return Width of bat image.
     */
    @Override
    public int getImageWidth() {
        return WINGS_DOWN_ICON.getIconWidth();
    }

    /**
     * Returns height of bat image.
     * @return Height of bat image.
     */
    @Override
    public int getImageHeight() {
        return WINGS_DOWN_ICON.getIconHeight();
    }

    /**
     * Handles the logic for which way the enemy object should move.
     * Also handles projectile spawning.
     * @param playerCenterX the x position of the center of the player.
     * @param playerCenterY the y position of the center of the player.
     * @param collideables HashSet of type Collideable used for checking collision on move call.
     * @see #move(String direction, HashSet collideables)
     * @see main.Collision.Collideable
     */
    @Override
    public void pathFinding(int playerCenterX, int playerCenterY, HashSet<main.Collision.Collideable> collideables)
    {
        Random rand = new Random();


        if(moveCycle < 0)
        {
            moveDir = rand.nextInt(4);
            isMulti = rand.nextBoolean();
            moveCycle = MOVE_CHANGE_DELAY;
        }
        else
            moveCycle--;

        if(waveCycle < 0)
        {
            addWave(playerCenterX,playerCenterY);
            waveCycle = WAVE_CREATION_DELAY;
        }
        else
            waveCycle--;

        //Multi-directional movement.
        if(isMulti)
        {
            switch(moveDir)
            {
                case(0):
                    move("N", collideables);
                    move("W",collideables);
                    break;
                case(1):
                    move("N",collideables);
                    move("E",collideables);
                    break;
                case(2):
                    move("S", collideables);
                    move("E",collideables);
                    break;
                case(3):
                    move("S", collideables);
                    move("W",collideables);
                    break;
            }
        }
        //Single-directional movement.
        else {
            switch(moveDir){
                case(0) -> move("N", collideables);
                case(1) -> move("E", collideables);
                case(2) -> move("S", collideables);
                case(3) -> move("W", collideables);
            }

        }
    }

    /**
     * Adds a wave to the Room
     * @param playerCenterX The x-position of the center of the player.
     * @param playerCenterY The y-position of the center of the player.
     */
    public void addWave(int playerCenterX, int playerCenterY)
    {
        gameWorld.getCurrentRoomRef().addProjectile(new BatWave(gameWorld, gameWorld.getCurrentRoomRef(), this, playerCenterX,playerCenterY));
    }

    /**
     * Determines whether this counts as an obstacle for collision-handling purposes.
     * @return Always returns false.
     */
    @Override
    public boolean isObstacle()
    {
        return false;
    }
}
