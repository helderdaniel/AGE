package core.gui.elements;


public interface IFont {

    enum Type {
        Arial,
        CourierNew,
        TimesNewRoman,
    }

    enum Style {
        Normal,
        Bold,
        Italic,
        BoldItalic,
        Underline,
        StrikeThrough
    }

    /**
     * @return the size of the font
     */
    int size();

    /**
     * @return the type name of the font
     */
    Type type();

    /**
     * @return the style of the font
     */
    Style style();
}





