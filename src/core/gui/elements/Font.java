package core.gui.elements;

/**
 * @author hdaniel@ualg.pt
 * @version 202506191055
 */
public abstract class Font implements IFont {

    protected Type  type = null;
    protected Style style  = null;
    protected int   size = 0;

    protected Font(Type type, Style style, int size) {
        this.type = type;
        this.style = style;
        this.size = size;
    }

    @Override
    public int size() { return size; }

    @Override
    public Type type() { return type; }

    @Override
    public Style style() { return style;  }
}

