package core.gui.elements;

/**
 * @author hdaniel@ualg.pt
 * @version 202506191055
 */
public abstract class Color implements IColor {

    protected Color color = null;

    protected Color(Color color) { this.color = color; }

    @Override
    public Color color() { return color; }

}

