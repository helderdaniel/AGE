package core.gui.awt;

/**
 * @author hdaniel@ualg.pt
 * @version 202506191101
 */
public class FontAWT extends core.gui.elements.Font {

    java.awt.Font font;

    protected static final String[] mapFont = {
            "Arial",
            "Courier New",
            "Times New Roman",
    };

    protected static final int[] mapStyle = {
            java.awt.Font.PLAIN,
            java.awt.Font.BOLD,
            java.awt.Font.ITALIC,
            java.awt.Font.BOLD | java.awt.Font.ITALIC,
            java.awt.Font.PLAIN,    // AWT Fonts do not support UNDERLINE, default to Normal (Plain)
            java.awt.Font.PLAIN,    // AWT Fonts do not support STRIKETHROUGH, default to Normal (Plain)
    };

    FontAWT(Type type, Style style, int size) {
        super(type, style, size);
        font = new java.awt.Font(mapFont[type.ordinal()], mapStyle[style.ordinal()], size);
    }

    public java.awt.Font get() { return font; }
}
