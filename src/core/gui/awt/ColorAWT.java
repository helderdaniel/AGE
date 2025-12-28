package core.gui.awt;
import  core.gui.elements.Color;

/**
 * @author hdaniel@ualg.pt
 * @version 202506191101
 */
public class ColorAWT extends Color {

    protected static final java.awt.Color[] mapColor = {
            java.awt.Color.BLACK,
            java.awt.Color.BLUE,
            java.awt.Color.CYAN,
            java.awt.Color.DARK_GRAY,
            java.awt.Color.GRAY,
            java.awt.Color.GREEN,
            java.awt.Color.LIGHT_GRAY,
            java.awt.Color.MAGENTA,
            java.awt.Color.ORANGE,
            java.awt.Color.PINK,
            java.awt.Color.RED,
            java.awt.Color.WHITE,
            java.awt.Color.YELLOW
    };

    public ColorAWT(Color color) { super(color); }

    /**
     * @return the color object for the backend
     */
    public java.awt.Color get() {  return mapColor[color.ordinal()]; }
}
