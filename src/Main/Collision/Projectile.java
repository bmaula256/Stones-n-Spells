package Main.Collision;

import Main.CharacterResources.Creature;
import Main.GUIDesign.GamePlayComponent;
import Main.Rooms.Obstacle;

import java.util.HashSet;

/**
 * A superclass for projectiles which handles basic projectile movement and collision behavior.
 */
public abstract class Projectile extends Obstacle {

    private static int DIAGONALCONST = 100;

    private int width;
    private int height;
    private int speed;
    private int damage;
    private String[] moveDir;

    /**
     * Constructs a projectile with the specified parameters.
     * @param x The x-position of the projectile.
     * @param y The y-position of the projectile.
     * @param width The physical width of the projectile.
     * @param height The physical height of the projectile.
     * @param speed The speed of the projectile.
     * @param damage The damage dealt by the projectile on impact.
     * @param moveDir The direction of projectile movement.
     * @param imageFileName The file name of the image which represents the projectile.
     * @param inGamePlayComponent The GamePlayComponent that the projectile is to be drawn to.
     */
    public Projectile(int x, int y, int width, int height, int speed, int damage, String[] moveDir, String imageFileName, GamePlayComponent inGamePlayComponent)
    {
        super(inGamePlayComponent, imageFileName, x,y);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;
        this.moveDir = moveDir;
    }

    /**
     * Constructs a projectile with the specified parameters.
     * @param x The x-position of the projectile.
     * @param y The y-position of the projectile.
     * @param width The physical width of the projectile.
     * @param height The physical height of the projectile.
     * @param speed The speed of the projectile.
     * @param damage The damage dealt by the projectile on impact.
     * @param targetCenterX The center x-pos of the target.
     * @param targetCenterY The center y-pos of the target.
     * @param inGamePlayComponent The GamePlayComponent which the projectile is drawn to.
     */
    public Projectile(int x, int y, int width, int height, int speed, int damage, int targetCenterX, int targetCenterY, GamePlayComponent inGamePlayComponent) {
        super(inGamePlayComponent, x, y);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;

        int thisCenterX = getX() + super.getWidth() / 2;
        int thisCenterY = getY() + super.getHeight() / 2;

        int distX = Math.abs(thisCenterX - targetCenterX);
        int distY = Math.abs(thisCenterY - targetCenterY);

        //Multi-Direction
        if (Math.abs(distX - distY) < DIAGONALCONST) {
            if (targetCenterX > thisCenterX && targetCenterY < thisCenterY) {
                moveDir = new String[]{"N", "E"};
            } else if (targetCenterX > thisCenterX && targetCenterY > thisCenterY) {
                moveDir = new String[]{"S", "E"};
            } else if (targetCenterX < thisCenterX && targetCenterY < thisCenterY) {
                moveDir = new String[]{"N", "W"};
            } else {
                moveDir = new String[]{"S", "W"};
            }
        }

        //Single-Direction
        else {
            System.out.println("Else called");
            if (distX > distY) {
                if (targetCenterX > thisCenterX) {
                    moveDir = new String[]{"E"};
                } else {
                    moveDir = new String[]{"W"};
                }
            } else {
                if (targetCenterY < thisCenterY) {
                    moveDir = new String[]{"N"};
                } else {
                    moveDir = new String[]{"S"};
                }
            }
        }
    }

    /**
     * Constructs a projectile with the specified parameters.
     * @param x The x-position of the projectile.
     * @param y The y-position of the projectile.
     * @param width The physical width of the projectile.
     * @param height The physical height of the projectile.
     * @param speed The speed of the projectile.
     * @param damage The damage dealt by the projectile on impact.
     * @param targetCenterX The x-pos center of the target.
     * @param targetCenterY The y-pos center of the target.
     * @param imageFileName The filename of the image which represents the object.
     * @param inGamePlayComponent The GamePlayComponent the projectile is drawn to.
     */
    public Projectile(int x, int y, int width, int height, int speed, int damage, int targetCenterX, int targetCenterY, String imageFileName, GamePlayComponent inGamePlayComponent) {
        super(inGamePlayComponent, imageFileName, x, y);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;

        int thisCenterX = getX() + super.getWidth() / 2;
        int thisCenterY = getY() + super.getHeight() / 2;

        int distX = Math.abs(thisCenterX - targetCenterX);
        int distY = Math.abs(thisCenterY - targetCenterY);

        //Multi-Direction
        if (Math.abs(distX - distY) < DIAGONALCONST) {
            System.out.println("Diagonal called");
            if (targetCenterX > thisCenterX && targetCenterY < thisCenterY) {
                moveDir = new String[]{"N", "E"};
            } else if (targetCenterX > thisCenterX && targetCenterY > thisCenterY) {
                moveDir = new String[]{"S", "E"};
            } else if (targetCenterX < thisCenterX && targetCenterY < thisCenterY) {
                moveDir = new String[]{"N", "W"};
            } else {
                moveDir = new String[]{"S", "W"};
            }
        }

        //Single-Direction
        else {
            System.out.println("Else called");
            if (distX > distY) {
                if (targetCenterX > thisCenterX) {
                    moveDir = new String[]{"E"};
                } else {
                    moveDir = new String[]{"W"};
                }
            } else {
                if (targetCenterY < thisCenterY) {
                    moveDir = new String[]{"N"};
                } else {
                    moveDir = new String[]{"S"};
                }
            }
        }
    }

    /**
     * Returns physical width of the projectile.
     * @return Physical width of the projectile.
     */
    @Override
    public int getWidth() {return width;}

    /**
     * Returns physical height of the projectile.
     * @return Physical height of the projectile.
     */
    @Override
    public int getHeight() {return height;}

    /**
     * Returns a String array of directions which projectile is moving.
     * @return A String array representing directions from the following options: N, W, E, S.
     */
    public String[] getMoveDir() {return moveDir;}

    /**
     * Returns the damage that the projectile deals on impact.
     * @return The damage that the projectile deals on impact.
     */
    public int getAttack() {return damage;}

    /**
     * Moves the projectile and also checks for collision.
     * @param collideables A HashSet full of objects to check collision against.
     */
    public void move(HashSet<Collideable> collideables)
    {
        for(String moveDirection: moveDir)
            move(moveDirection,collideables);
    }

    private void move(String direction, HashSet<Collideable> collideables) {

        switch (direction.toUpperCase()) {
            case "N":
                if(yPos - speed >= 0)
                    yPos-=speed;
                else
                    terminateProjectile();
                break;
            case "E":
                if(xPos + speed + getWidth() <= parent.getWidth())
                    xPos += speed;
                else
                    terminateProjectile();
                break;
            case "S":
                if(yPos + speed + getHeight() <= parent.getHeight())
                    yPos += speed;
                else
                    terminateProjectile();
                break;
            case "W":
                if(xPos - speed >= 0)
                    xPos -= speed;
                else
                    terminateProjectile();
                break;
        }

        for(Collideable other : collideables)
        {
            if(other instanceof Creature && collides(other,super.getWidth(),((Creature) other).getImageWidth()) != null)
                collisionEffect((Creature) other);
        }
    }

    /**
     * This should be implemented so this projectile can be terminated in some way through a direct method call.
     */
    public abstract void terminateProjectile();

    /**
     * This should be implemented to determine the effect this projectile would have on a creature on collision.
     * @param creature The Creature object to apply the effect to.
     */
    public abstract void collisionEffect(Creature creature);
}
