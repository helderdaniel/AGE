package core.gui;

import core.eng.IGameObject;
import core.eng.IInputEvent;

/**
 * User interface that gets user IInputEvents from keyboard and mouse and display a list of IGameObjects
 * @author hdaniel@ualg.pt
 * @version 202502041222
 */
public interface IUserInterface {

    /**
     * @return the number of rows in GUI screen
     */
    int rows();

    /**
     * @return the number of columns in GUI screen
     */
    int cols();

    /**
     * draw all game objects in gol
     * @param gol list of GameObjects
     */
    void update(Iterable<IGameObject> gol);

    /**
     * @return return first input event in queue
     */
    IInputEvent input();
}
