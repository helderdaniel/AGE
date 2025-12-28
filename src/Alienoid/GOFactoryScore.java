package Alienoid;

import core.eng.*;
import core.gui.IPainter;

import java.awt.*;

/**
 * @author hdaniel@ualg.pt
 * @version 202506181046
 */
public class GOFactoryScore extends GOFactoryText {

    static Font font = new Font("Times New Roman", Font.BOLD, 24);
    static Color color = Color.RED;


    public GOFactoryScore(IPainter painter, String text, IGameEngine engine) {
        super(painter, text, font, color, engine);
        behaviours = new IBehaviour[] { new BehavScore() };
    }

}
