package core.eng;

/**
 * Stores user input data from keyboard and mouse
 * @author hdaniel@ualg.pt
 * @version 202502111928
 */
public interface IInputEvent {
    /**
     * @return return the key pressed
     *         or -1 if no key pressed
     */
    int key();

    /**
     * @return return the mouse button pressed
     *         left == 1, right == 3, middle == 2
     *         or -1 if no mouse button pressed
     */
    int button();

    /**
     * @return return the mouse x position
     *         or -1 if no mouse button pressed
     */
    int x();

    /**
     * @return return the mouse y position
     *         or -1 if no mouse button pressed
     */
    int y();
}

