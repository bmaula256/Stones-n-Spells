package Main;

/**
 * An interface which classes can implement to in order to have behavior which updates on each tick.
 */
public interface Updateable {
    /**
     * The method which classes implement to actually trigger updates to objects based on a swing Timer or other updating tool.
     * @see javax.swing.Timer
     */
    public void update();
}
