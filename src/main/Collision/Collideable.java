package main.Collision;
/**
 * Incredibly important interface which tests for collision between two objects that have image representations.
 * This utilizes image width and height to calculate the center of images.
 * This utilizes the actual width and height of objects to calculate the radius of collision.
 * Ensure that images for collision checking are centered within transparent space!
 */
public interface Collideable {

    /**
     * The default value that the interface assumes represents the image diameter of the objects which it is comparing for collision.
     * The value of the constant is {@value #PIXEL_CONSTANT} measured in pixels.
     */
    public final static int PIXEL_CONSTANT = 50;
/*
    /**
     * Tests for collision with a square to square radial algorithm.
     * @param other The other Main.Collision.Collideable object which the object implementing Main.Collision.Collideable is being checked against.
     * @return The other Main.Collision.Collideable if collision is found, or null if there is no collision between the two objects.
     *\/
    public default Collideable collides(Collideable other)
    {
        //The size of the actual image in the image icon.
        //Everything is 50x50 pixels, so  this is a constant.
        int centerX = getX()+ PIXEL_CONSTANT /2;
        int centerY = getY()+ PIXEL_CONSTANT /2;
        int radX = getWidth()/2;
        int radY = getHeight()/2;

        int otherCenterX = other.getX()+ PIXEL_CONSTANT /2;
        int otherCenterY = other.getY()+ PIXEL_CONSTANT /2;
        int otherRadX = other.getWidth()/2;
        int otherRadY = other.getHeight()/2;

        int xDist = Math.abs(otherCenterX-centerX);
        int yDist = Math.abs(otherCenterY-centerY);
        int radiiX = Math.abs(radX) + Math.abs(otherRadX);
        int radiiY = Math.abs(radY) + Math.abs(otherRadY);

        if(!equals(other) &&  xDist < radiiX && yDist < radiiY)
            return other;
        return null;
    }
    */

    /**
     * An overloaded variant of the collides method which checks for collision utilizing different image sizes. Still uses radial square to square collision.
     * @param other The other Main.Collision.Collideable object to be checked against.
     * @return The other Main.Collision.Collideable if collision is found, or null if there is no collision between the two objects.
     */
    public default Collideable collides(Collideable other)
    {
        //The size of the actual image in the image icon.
        //Everything is 50x50 pixels, so  this is a constant.

        int centerX = getImageCenterX();
        int centerY = getImageCenterY();
        int radX = getWidth()/2;
        int radY = getHeight()/2;

        int otherCenterX = other.getImageCenterX();
        int otherCenterY = other.getImageCenterY();
        int otherRadX = other.getWidth()/2;
        int otherRadY = other.getHeight()/2;

        int xDist = Math.abs(otherCenterX-centerX);
        int yDist = Math.abs(otherCenterY-centerY);
        int radiiX = Math.abs(radX) + Math.abs(otherRadX);
        int radiiY = Math.abs(radY) + Math.abs(otherRadY);

        if(!equals(other) &&  xDist < radiiX && yDist < radiiY) {
            return other;
        }
        return null;
    }

    /**
     * Checks if circle and square collides.
     * Still based on rectangular images for collision detection.
     * @param c1 The circle being checked.
     * @param c2 The square being checked
     */
    public static Collideable circleSquareCollides(Collideable c1, Collideable c2 )
    {

        return null;
    }

    /**
     * Gets the X-center of the image representing the Collideable
     * @return An integer representing the X-center of the image representing the Collideable
     */
    public default int getImageCenterX()
    {
        return getX()+ getImageWidth() /2;
    }

    /**
     * Gets the Y-center of the image representing the Collideable
     * @return An integer representing the Y-center of the image representing the Collideable
     */
    public default int getImageCenterY()
    {
        return getY()+ getImageHeight() /2;
    }



    /**
     * Returns a boolean which determines if a Main.Collision.Collideable is an obstacle. Often used in Main.Collision logic outside the interface.
     * @return A boolean which determines if a Main.Collision.Collideable is an obstacle. Often used in Main.Collision logic outside the interface.
     */
    public boolean isObstacle();

    /**
     * Returns the x-position of the implementing object.
     * @return An integer representing the implementing object's x-position.
     */
    public int getX();
    /**
     * Returns the y-position of the implementing object.
     * @return An integer representing the implementing object's y-position.
     */
    public int getY();
    /**
     * Returns the width of the implementing object.
     * @return An integer representing the implementing object's width.
     */
    public int getWidth();
    /**
     * Returns the height of the implementing object.
     * @return An integer representing the implementing object's height.
     */
    public int getHeight();

    /**
     * Returns the width of the image of the implementing object, including any transparent space.
     * This is incredibly important for calculating centers of objects.
     * @return int representing image width.
     */
    public int getImageWidth();
    /**
     * Returns the height of the image of the implementing object, including any transparent space.
     * This is incredibly important for calculating centers of objects.
     * @return int representing image height.
     */
    public int getImageHeight();
}
