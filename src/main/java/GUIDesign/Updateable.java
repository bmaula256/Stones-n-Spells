package GUIDesign;

/**
 * An interface which classes can implement to in order to have behavior which updates on each tick.
 */
public interface Updateable {
    /**
     * The method which classes implement to actually trigger updates to objects based on a swing Timer or other updating tool.
     * @param updateObject An object with additional properties required in order to process an update() call.
     * @see javax.swing.Timer
     */
    public void update(Object updateObject);
}
