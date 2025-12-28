package Alienoid;

/**
 * @author hdaniel@ualg.pt
 * @version 202501310756
 */

import core.eng.*;
import core.gui.IUserInterface;
import core.gui.awt.GUIAWT;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        //create game engine
        String title = "Alienoid 2D";
        String resPath = "src/Alienoid/resources/";
        boolean showColliders = true;

        IUserInterface gui = new GUIAWT(title, 800, 600, showColliders);
        IGameEngine engine = new GameEngine(new GameWorldLayered(0,2), gui);
        IGame alienoid = new Alienoid(engine, resPath);
        alienoid.init();
        engine.run();
    }
}