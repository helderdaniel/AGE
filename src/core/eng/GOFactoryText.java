package core.eng;

import core.gui.IPainter;
import core.gui.ShapeText;

import java.awt.*;

/**
 * @author hdaniel@ualg.pt
 * @version 202506181046
 */
public class GOFactoryText extends GOFactory {

    protected String text;
    protected Font font;
    protected Color color;
    protected IPainter painter;

    //todo: same problem with painter here
    public GOFactoryText(IPainter painter, String text, Font font, Color color, IGameEngine engine) {
        super(engine);
        this.text = text;
        this.font = font;
        this.color = color;
        this.painter = painter;
    }

    @Override
    public IGameObject create(String name, ITransform t) {
        shape = new ShapeText(painter, t, text, font, color);
        return new GameObject(engine, name, t, shape, null, behaviours);
    }

}
