package macbeth;

/**
 * FlyingObject is abstract because it has at least one abstract function in it.
 */
public abstract class FlyingObject {

    /**
     * The advance function is common for all derived classes
     */
    public void advance() {

    }

    /**
     * The draw function must be implemented by all derived classes. 
     */
    public abstract void draw();
}
